package com.MarketConnect.GaadiDealer.services;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    ApplicationContext context;

    @Autowired
    private JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;

        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            username=jwtService.extractUserName(token);
        }
        else
        {
            //what's this doing and why it's needed other than above if?
            if(request.getCookies()!=null)
            {
                for(jakarta.servlet.http.Cookie cookie:request.getCookies())
                {
                    if("accesstoken".equals(cookie.getName()))
                    {
                        try {
                            token = cookie.getValue();
                            username = jwtService.extractUserName(token);
                        }
                        catch (Exception ex)
                        {
                            System.out.println("Cookie JWT error: "+ex.getMessage());
                        }
                        break;
                    }
                }
            }
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
//            UserDetails userDetails=context.getBean(MyUserDetailsService.class)
//                    .loadUserByUsername(username);   --->this does one select operation in db for every request
           String role= jwtService.getClaim(token);
            if(jwtService.validateToken(token))
            {
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username, null,new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_"+role.toString().toUpperCase()))));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
