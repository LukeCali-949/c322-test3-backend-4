package edu.iu.c322.test3.controllers;

import edu.iu.c322.test3.model.Customer;
import edu.iu.c322.test3.service.IAuthenticationService;
import edu.iu.c322.test3.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final IAuthenticationService authenticationService;

    private TokenService tokenService;


    public AuthenticationController(AuthenticationManager authenticationManager,
                                    IAuthenticationService authenticationService,
                                    TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }




    @PostMapping("/signup")
    public boolean register(@RequestBody Customer customer) {
        System.out.println(customer);
        try {
            authenticationService.register(customer);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signin")
    public String login(@RequestBody Customer customer) {
        System.out.println(customer.getUsername() + customer.getPassword());
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                customer.getUsername()
                                ,customer.getPassword()));
        System.out.println(authentication);
        //return "string";
//        return "string";


        return tokenService.generateToken(authentication);
    }


}