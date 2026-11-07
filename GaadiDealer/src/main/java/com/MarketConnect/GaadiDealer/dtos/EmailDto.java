package com.MarketConnect.GaadiDealer.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmailDto {


    private Long testdriveId;
    private String emailId;
    private LocalDateTime dateAndTimeOfTestDrive;
    private Long carId;
    private String city;
    private Boolean isDateConfirmed;

}
