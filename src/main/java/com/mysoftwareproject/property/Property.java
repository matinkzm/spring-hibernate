package com.mysoftwareproject.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "propertyId")
    @SequenceGenerator(name = "propertyId", sequenceName = "propertyId", allocationSize = 1)
    private Integer id;
    @NotNull
    private String name;
    private String availability;
    private Integer propertyCurrentUser;
    private Integer propertyHistoryId; // my side

}
