package com.tasks.cyclops.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tasks.cyclops.Service.UserServiceImpl;
import com.tasks.cyclops.dto.UserDto;
import com.tasks.cyclops.security.JwtResponse;

@Controller
public class AuthController {

    private UserServiceImpl userService;
    private JwtResponse jwtResponse;

    public AuthController(UserServiceImpl userService, JwtResponse jwtResponse) {
        this.userService = userService;
        this.jwtResponse=jwtResponse;
    }

    @GetMapping(path="/auth/home1")
    public String getHome(){
        return "Hello";
    }
    
    @PostMapping(path="/auth/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDto){
        
        String res=userService.isAuthentic(userDto.getUserName(), userDto.getPassword());
        if(res.equals("true")){
            String token=jwtResponse.getJwtToken(userDto.getUserName());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }else{
            if(userService.existingUser(userDto.getUserName())){
                return new ResponseEntity<>("Incorrect password",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Invalid User Name",HttpStatus.BAD_REQUEST);
        }
        
    }

    @PostMapping(path="/auth/register")
    public ResponseEntity<String> addNewuser(@RequestBody UserDto userDto){
        System.out.println(userDto.getUserName()+" ---- "+userDto.getPassword());
        if(userService.existingUser(userDto.getUserName())){
            return new ResponseEntity<>("User already exists, Please login",HttpStatus.BAD_REQUEST);
        }
        String response=userService.createUser(userDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
