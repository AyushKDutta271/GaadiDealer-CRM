package com.MarketConnect.GaadiDealer.dtos;

import com.MarketConnect.GaadiDealer.entities.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeadsDto {

    private Long id;

    @NotNull
    private Long testDriveId;
    @NotNull
    private String emailId;

    @NotNull
    private Long carId;

    @NotNull
    private Boolean buy;


    private String uploadedDoc;

    @NotNull
    private Boolean isVerified;

    @NotNull
    private Boolean isPaymentDone;
}
