package com.mysoftwareproject.sponsorOrphanHistory;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SponsorOrphanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SponsorOrphanHistoryId")
    @SequenceGenerator(name = "SponsorOrphanHistoryId", sequenceName = "SponsorOrphanHistoryId", allocationSize = 1)
    private Integer id;
    @NotNull
    private Integer sponsorId;
    @NotNull
    private Integer orphanId;
    private LocalDate startDate;
    private LocalDate endDate;

    public SponsorOrphanHistory(Integer sponsorId, Integer orphanId, LocalDate startDate, LocalDate endDate) {
        this.sponsorId = sponsorId;
        this.orphanId = orphanId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SponsorOrphanHistory(Integer sponsorId, Integer orphanId, LocalDate startDate) {
        this.sponsorId = sponsorId;
        this.orphanId = orphanId;
        this.startDate = startDate;
    }
}
