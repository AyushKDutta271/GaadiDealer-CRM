package com.MarketConnect.GaadiDealer.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timeStampDataTime;

    public ErrorResponse(int status, String message, LocalDateTime timeStampDataTime) {

        this.status = status;
        this.message = message;
        this.timeStampDataTime = timeStampDataTime;
    }
}
