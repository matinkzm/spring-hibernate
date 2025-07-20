package com.mysoftwareproject.report;

import com.mysoftwareproject.enums.ReportType;
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
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reportId")
    @SequenceGenerator(name = "reportId", sequenceName = "reportId", allocationSize = 1)
    private Integer id;
    @NotNull
    private Integer orphanId;
    @NotNull
    private String reportType;
    @NotNull
    private LocalDate reportDate;
    @Column(columnDefinition="TEXT")
    @NotNull
    private String reportContent;

    public Report(Integer orphanId, String reportType, LocalDate reportDate, String reportContent) {
        this.orphanId = orphanId;
        this.reportType = reportType;
        this.reportDate = reportDate;
        this.reportContent = reportContent;
    }
}
