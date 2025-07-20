package com.mysoftwareproject.sponsor;

import com.mysoftwareproject.enums.Access;
import com.mysoftwareproject.enums.ActiveType;
import com.mysoftwareproject.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SponsorDto {

    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String nationalCode;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String dateOfBirth;
    private String address;
    @NotNull
    private Gender gender;
    private Integer connectorId;

    private Access accessLevel;

    @NotNull
    private String username;
    @NotNull
    private String password;
    private String active = "true";
}
