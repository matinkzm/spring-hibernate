package com.mysoftwareproject.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {

    @NotNull
    private Integer payerId;
    @NotNull
    private Integer orphanId;
    @NotNull
    private Double amount;
    private String description;
    private String date;

    // sponsor type = kafil ya moasese
    private String sponsorType;
    private String accountNumber;
    private String sheba;
}
