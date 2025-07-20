package com.mysoftwareproject.orphan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysoftwareproject.enums.Access;
import com.mysoftwareproject.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orphan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "orphanId")
    @SequenceGenerator(name = "orphanId", sequenceName = "orphanId", allocationSize = 1)
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
    @NotNull
    private Gender gender;
    private String address;
    private String fathersName;
    private String mothersName;
    private Boolean taken = false; // my side
    private String accountNumber;
    private String sheba;
    private Integer sponsorId;
    private Access accessLevel = Access.ORPHAN; // my side
    private Integer age;
    private Boolean active; // my side
    private LocalDate withoutSponsorStartDate; // my side
    private Integer sponsorOrphanHistoryId; // my side

    public Orphan(String name, String lastName, String nationalCode, String email, String phoneNumber, LocalDate dateOfBirth, Gender gender, String address, String fathersName, String mothersName, Boolean taken, String accountNumber, String sheba, Integer sponsorId) {
        this.name = name;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.fathersName = fathersName;
        this.mothersName = mothersName;
        this.taken = taken;
        this.accountNumber = accountNumber;
        this.sheba = sheba;
        this.sponsorId = sponsorId;
    }
}
