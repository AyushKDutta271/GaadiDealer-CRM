package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.dtos.LoginUserDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String verify(LoginUserDto user)
    {
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmailId(),user.getPassword())
        );

        if(auth.isAuthenticated())
        {
            return jwtService.generateToken(user.getEmailId(),user.getUserType());
        }
        else
        {
            return "failed to authenticataed";
        }
    }


}
