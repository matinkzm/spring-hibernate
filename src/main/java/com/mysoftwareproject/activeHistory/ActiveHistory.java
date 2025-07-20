package com.mysoftwareproject.activeHistory;

import com.mysoftwareproject.enums.ActiveType;
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
public class ActiveHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "activeHistoryId")
    @SequenceGenerator(name = "activeHistoryId", sequenceName = "activeHistoryId", allocationSize = 1)
    private Integer id;
    //@NotNull
    private Integer sponsorId;
    //@NotNull
    private LocalDate startDate;
    //@NotNull
    private LocalDate endDate;
    //@NotNull
    private String isActive;

    public ActiveHistory(Integer sponsorId, LocalDate startDate, LocalDate endDate, String isActive) {
        this.sponsorId = sponsorId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public ActiveHistory(Integer sponsorId, LocalDate startDate, String isActive) {
        this.sponsorId = sponsorId;
        this.startDate = startDate;
        this.isActive = isActive;
    }
}
