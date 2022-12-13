package com.springSecurity.jwt.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class HomeController {



    @GetMapping("/greeting")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayHi(Principal principal){
        return "hi dear  :"+ principal.getName();
    }
}
