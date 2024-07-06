package com.tasks.cyclops.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tasks.cyclops.Repository.UserRepository;
import com.tasks.cyclops.model.UserEntity;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    private UserRepository userService;
    
    @Autowired
    public CustomUserDetailsService(UserRepository userService){
        this.userService=userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user=userService.findByUserName(username)
        .orElseThrow(()->new UsernameNotFoundException("Username not found"));
        
        return User.withUsername(user.getUserName())
        .password(user.getPassword())
        .authorities("USER")
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
    }
    
}
