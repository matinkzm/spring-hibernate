package com.mysoftwareproject.property;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PropertyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "propertyHistoryId")
    @SequenceGenerator(name = "propertyHistoryId", sequenceName = "propertyHistoryId",allocationSize = 1)
    private Integer id;

    private Integer propertyId;

    private Integer consumerId;

    private String propertyName;

    private LocalDate startUsingDate;
    private LocalDate endUsingDate;
    private LocalDate expirationDate;

    public PropertyHistory(Integer propertyId, Integer consumerId, String propertyName, LocalDate startUsingDate, LocalDate endUsingDate) {
        this.propertyId = propertyId;
        this.consumerId = consumerId;
        this.propertyName = propertyName;
        this.startUsingDate = startUsingDate;
        this.endUsingDate = endUsingDate;
    }

    public PropertyHistory(LocalDate startUsingDate, String propertyName, Integer consumerId, Integer propertyId) {
        this.startUsingDate = startUsingDate;
        this.propertyName = propertyName;
        this.consumerId = consumerId;
        this.propertyId = propertyId;
    }
}
