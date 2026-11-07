package com.MarketConnect.GaadiDealer;


import com.MarketConnect.GaadiDealer.dtos.EmailDto;
import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.entities.Vehicle;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import com.MarketConnect.GaadiDealer.repositories.VehicleRepository;
import com.MarketConnect.GaadiDealer.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({FileStorageProperties.class})

public class GaadiDealerApplication {





	public static void main(String[] args) {
		SpringApplication.run(GaadiDealerApplication.class, args);
	}



}
