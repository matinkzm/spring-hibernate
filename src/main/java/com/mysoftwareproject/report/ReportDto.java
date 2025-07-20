package com.mysoftwareproject.report;

import com.mysoftwareproject.enums.ReportType;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ReportDto {

    private Integer orphanId;
    private String reportType;
    private String reportDate;
    @Column(columnDefinition="TEXT")
    private String reportContent;
}
