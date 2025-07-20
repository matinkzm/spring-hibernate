package com.mysoftwareproject.activeHistory;

import com.mysoftwareproject.enums.ActiveType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActiveHistoryDto {

    @NotNull
    private Integer sponsorId;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private ActiveType isActive;
}
