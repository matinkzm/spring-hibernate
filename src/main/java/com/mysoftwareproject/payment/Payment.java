package com.mysoftwareproject.payment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ,generator = "paymentId")
    @SequenceGenerator(name = "paymentId", sequenceName = "paymentId", allocationSize = 1)
    private Integer id;
    @NotNull
    private Integer payerId;
    @NotNull
    private Integer orphanId;
    @NotNull
    private Double amount;
    private String description;
    private LocalDate date;

    // sponsor type = kafil ya moasese
    private String sponsorType;
    private String accountNumber;
    private String sheba;

    public Payment(Integer payerId, Integer orphanId, Double amount, String description, LocalDate date, String sponsorType) {
        this.payerId = payerId;
        this.orphanId = orphanId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.sponsorType = sponsorType;
    }

    public Payment(Integer payerId, Integer orphanId, Double amount, String description, LocalDate date, String sponsorType, String accountNumber, String sheba) {
        this.payerId = payerId;
        this.orphanId = orphanId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.sponsorType = sponsorType;
        this.accountNumber = accountNumber;
        this.sheba = sheba;
    }
}
