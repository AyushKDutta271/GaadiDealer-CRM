package com.MarketConnect.GaadiDealer.controllers;

import com.MarketConnect.GaadiDealer.GaadiDealerApplication;
import com.MarketConnect.GaadiDealer.dtos.EmailDto;
import com.MarketConnect.GaadiDealer.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private GaadiDealerApplication gaadiDealerApplication;
    @PostMapping("/send")
     public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto)
     {
         return ResponseEntity.status(HttpStatus.CREATED).body(emailService.buildMail(emailDto));
     }
}
