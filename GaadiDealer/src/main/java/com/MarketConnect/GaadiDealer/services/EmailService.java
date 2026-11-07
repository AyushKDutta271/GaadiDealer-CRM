package com.MarketConnect.GaadiDealer.services;

import com.MarketConnect.GaadiDealer.dtos.EmailDto;
import com.MarketConnect.GaadiDealer.entities.User;
import com.MarketConnect.GaadiDealer.entities.Vehicle;
import com.MarketConnect.GaadiDealer.repositories.UserRepository;
import com.MarketConnect.GaadiDealer.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public void sendSimpleMail(String to, String subject, String body)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);

        System.out.println("Mail sent successfully!");
    }

    public  String buildMail(EmailDto emailDto)
    {
        Optional<User> user=userRepository.findByEmailId(emailDto.getEmailId());
        Optional<Vehicle> vehicle=vehicleRepository.findById(emailDto.getCarId());
        if(user.isPresent() && vehicle.isPresent())
        {
            String to=emailDto.getEmailId();
            String body="Greetings!"+user.get().getFullName()+ " ,"+" \n This email is to inform you that, your testdrive request for:\n"+vehicle.get().getCarBrand()+" ,\n"+"Date of testdrive: "+emailDto.getDateAndTimeOfTestDrive().getDayOfMonth()+"/"+emailDto.getDateAndTimeOfTestDrive().getMonth()+"/"+emailDto.getDateAndTimeOfTestDrive().getYear()+" \n City: "+emailDto.getCity()+"\n has been **CONFIRMED**!\n"+"Please note down the testdriveId: "+emailDto.getTestdriveId()+" for offline verification reference!\n Thankyou!\nRegards,GaadiDealer!";
            String subject="From CarDealer, UPDATE! regarding requested testdrive schedule!";

            sendSimpleMail(to,subject,body);
            return "Email Sent successfully!";
        }
        else
        {
            throw new NoSuchElementException("No user with this userID OR No car with this ID exists!");
        }
    }


}
