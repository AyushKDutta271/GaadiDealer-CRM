package com.MarketConnect.GaadiDealer.controllers;


import com.MarketConnect.GaadiDealer.dtos.LoginUserDto;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import com.MarketConnect.GaadiDealer.services.AuthService;
import com.MarketConnect.GaadiDealer.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUserDto user)
    {
        String token=authService.verify(user);

        ResponseCookie cookie=ResponseCookie.from("accesstoken",token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24*60*60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message","Logged in"));

    }


    @GetMapping("/me")
    public Object me(Authentication authentication)
    {
        System.out.println(authentication.getName());
        return authentication.getName();
    }

    @PostMapping("/userlogout") //spring sevurity has default logout api /logout hence keeping different name
    public ResponseEntity<?> logout(HttpServletResponse httpServletResponse)
    {
        ResponseCookie cookie=ResponseCookie.from("accesstoken","")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        return ResponseEntity.ok("Logged out!");
    }
}


