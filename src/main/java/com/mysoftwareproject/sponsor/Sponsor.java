package com.mysoftwareproject.sponsor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysoftwareproject.enums.Access;
import com.mysoftwareproject.enums.ActiveType;
import com.mysoftwareproject.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sponsorId")
    @SequenceGenerator(name = "sponsorId", sequenceName = "sponsorId", allocationSize = 1, initialValue = 100)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    @Column(unique = true)
    private String nationalCode;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    private LocalDate dateOfBirth;
    private String address;
    @NotNull
    private Gender gender;
    private Integer connectorId;
    private Access accessLevel = Access.SPONSOR; // hamoon role hast // my side
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private String active = "true"; // my side
    private Integer activeHistoryId; // my side
    private LocalDate createdAt; // my side

    public Sponsor(String name, String lastName, String nationalCode, String email, String phoneNumber, LocalDate dateOfBirth, String address, Gender gender, Integer connectorId, String active, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
        this.connectorId = connectorId;
        this.active = active;
        this.username = username;
        this.password = password;
    }
}
