package com.mysoftwareproject.property;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PropertyHistoryDto {
    @NotNull
    private Integer propertyId;
    @NotNull
    private Integer consumerId;
    @NotNull
    private String propertyName;
    @NotNull
    private String startUsingDate;
    private String endUsingDate;
    @NotNull
    private LocalDate expirationDate;
}
