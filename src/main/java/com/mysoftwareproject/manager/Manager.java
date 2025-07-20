package com.mysoftwareproject.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysoftwareproject.enums.*;
import com.mysoftwareproject.enums.Access;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "orphanId")
    @SequenceGenerator(name = "orphanId", sequenceName = "orphanId" , allocationSize = 1, initialValue = 1)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    @Size(min = 10, max = 10)
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
    @NotNull
    private Gender gender;

    private Access accessLevel = Access.MANAGER; // my side

    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;

    private Integer age;

    private String active = "true"; // my side

    public Manager(String name, String lastName, String nationalCode, String email, String phoneNumber, LocalDate dateOfBirth, Gender gender, String username, String password, Integer age) {
        this.name = name;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
