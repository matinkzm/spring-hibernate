package com.mysoftwareproject.orphan;

import com.mysoftwareproject.enums.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class OrphanDto {
    private Integer id;

    private String name;

    private String lastName;

    @Column(unique = true)
    private String nationalCode;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String dateOfBirth;

    private Gender gender;
    private String address;
    private String fathersName;
    private String mothersName;

    private Boolean taken = false;

    private String accountNumber;
    private String sheba;

    private Integer sponsorId;
    private Boolean active;
}
