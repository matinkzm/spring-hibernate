package com.mysoftwareproject.sponsorOrphanHistory;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SponsorOrphanHistoryDto {
    @NotNull
    private Integer sponsorId;
    @NotNull
    private Integer orphanId;
    private String startDate;
    private String endDate;
}
