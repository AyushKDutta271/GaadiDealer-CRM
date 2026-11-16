package com.MarketConnect.GaadiDealer.configs;

import com.MarketConnect.GaadiDealer.services.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        http    .cors(Customizer.withDefaults()) //what does this do?
                .csrf(csrf->csrf.disable())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                                    auth.requestMatchers("/users/register")
                                            .permitAll()
                                            .requestMatchers("/login")
                                            .permitAll()
                                            .requestMatchers(HttpMethod.OPTIONS,"/**")
                                            .permitAll()
                                            .requestMatchers("/userlogout")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/cars/addVehicle")
                                            .hasAnyRole("USER","ADMIN")
                                            .requestMatchers("/cars/viewVehicles")
                                            .hasAnyRole("USER","ADMIN")
                                            .requestMatchers("/cars/sell")
                                            .hasRole("USER")
                                            .requestMatchers("/cars/viewVehicleById/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers(("/cars/updateVehicle/**"))
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/testdrives/updateStatus/**")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/testdrives/add")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/testdrives/get")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/testdrives/updateTestDate/**")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/emails/send")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/testdrives/updateStatus/**")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/leads/customerVerificationDoc/download/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/cars/getAllSoldCars")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/cars/getTotalUnSoldCars")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/cars/getCountOfAllCars")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/cars/getTotalMoneyEarned")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/cars/getCarsByUserId/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/login")
                                            .permitAll()
                                            .requestMatchers("/leads/save")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/leads/getLeads")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/leads/getLeadById/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/leads/getTotalLeads")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/leads/updateStatus/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/leads/updateByStatusOfALead/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/leads/getLeadByTestDriveId/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/cars/viewVehicleById/**")
                                            .hasAnyRole("ADMIN","USER")

                                            .requestMatchers("/contacts/save")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/contacts/get")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/contacts/getAll")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/adminVerificationDoc/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/testdrives/getTestDriveById/**")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/testdrives/getTotalTestDrives")
                                            .hasAnyRole("ADMIN")
                                            .requestMatchers("/testdrives/getAllDoneTestDrives")
                                            .hasRole("ADMIN")
                                            .requestMatchers("/testdrives/getTestDrivesByUserId/**")
                                            .permitAll()
                                            .requestMatchers("/users/getUserByEmailId")
                                            .hasAnyRole("ADMIN","USER")
                                            .requestMatchers("/*.jpg","/*.jpeg","/*.png","/*.gif","/*.webp","/**/*.pdf")
                                            .permitAll()

                                            .anyRequest()
                                            .authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
    {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
