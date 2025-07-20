package com.mysoftwareproject.report;

import com.mysoftwareproject.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    List<Report> findAllByOrphanId(Integer id);
    List<Report> findAllByReportTypeAndOrphanId(String reportType, Integer id);
    List<Report> findAllByReportDateBetween(LocalDate start, LocalDate end);
    List<Report> findAllByReportDate(LocalDate date);
    List<Report> findAllByReportContentContains(String content);

}
