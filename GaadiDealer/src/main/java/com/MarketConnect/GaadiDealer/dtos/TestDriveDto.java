package com.MarketConnect.GaadiDealer.dtos;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TestDriveDto {

    private Long id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]+@gmail.[a-z]+$")
    private String emailId;

    @NotNull
    private LocalDateTime dateAndTimeOfTestDrive;

    @NotNull(message = "car id must not be null")
    private Long carId;

    @NotNull
    private String city;

    @NotNull
    private Boolean isDateConfirmed;

    @NotNull
    private Boolean isDone;
}
