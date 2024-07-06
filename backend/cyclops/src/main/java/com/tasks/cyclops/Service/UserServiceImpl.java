package com.tasks.cyclops.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tasks.cyclops.Repository.UserRepository;
import com.tasks.cyclops.dto.UserDto;
import com.tasks.cyclops.model.UserEntity;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String createUser(UserDto userDto) {
        UserEntity user=new UserEntity();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);
        return "User Created";
    }

    @Override
    public boolean existingUser(String name) {
        return userRepo.existsByUserName(name);
    }

    @Override
    public Optional<UserEntity> getUser(String name) {
        return userRepo.findByUserName(name);
    }

    @Override
    public String isAuthentic(String name, String password) {
        return checkCredentials(name,password)? "true":"false";
    }

    private boolean checkCredentials(String name, String password){
        UserEntity user= userRepo.findByUserName(name).get();
        boolean isValidUser=user.getUserName().equalsIgnoreCase(name);
        boolean isValidPassword=passwordEncoder.matches(password, user.getPassword());
        return isValidUser&&isValidPassword;
    }
    
}
