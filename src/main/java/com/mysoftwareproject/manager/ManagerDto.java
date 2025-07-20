package com.mysoftwareproject.manager;

import com.mysoftwareproject.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ManagerDto {

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
    private String dateOfBirth;
    @NotNull
    private Gender gender;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;
    private String active;
}
