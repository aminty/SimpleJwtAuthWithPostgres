package com.springSecurity.jwt.controller;

import com.springSecurity.jwt.config.JwtUtils;
import com.springSecurity.jwt.domain.SecurityUser;
import com.springSecurity.jwt.dto.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    @Qualifier("userService")
    private final UserDetailsService userDetailsService;
    private  final JwtUtils jwtUtils;



    public AuthenticationController(AuthenticationManager authenticationManager,  UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/authentication")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        final UserDetails user=  userDetailsService.loadUserByUsername(request.getUsername());
        if (user!=null){

            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        System.out.println("-----------> user not found");
        return ResponseEntity.status(400).body("error occurred!!!");


    }


}
