package com.mysoftwareproject.report;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping // correct
    public Report createReport(@RequestBody ReportDto reportDto) {
        return reportService.createReport(reportDto);
    }

    @GetMapping("/getReportById/{reportId}") // correct
    public Report getReportById(@PathVariable("reportId") Integer reportId) {
        return reportService.getReportById(reportId);
    }

    @GetMapping // correct
    public Object getAllReports(@RequestParam(required = false, defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.getAllReports());
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.getAllReports());
        }
        return reportService.getAllReports();
    }

    @PutMapping("/{reportId}") // correct
    public Report updateReport(@PathVariable("reportId") Integer reportId,@RequestBody ReportDto reportDto) {
        return reportService.updateReport(reportId, reportDto);
    }

//    @DeleteMapping("/{reportId}")
//    public void deleteReportById(@PathVariable("reportId") Integer reportId) {
//        reportService.deleteReportById(reportId);
//    }

    @GetMapping("/getReportsByTypeAndOrphanId/{reportType}/{orphanId}") // correct
    public Object getReportsByTypeAndOrphanId(@PathVariable("reportType") String reportType,@PathVariable("orphanId") Integer orphanId,@RequestParam(required = false, defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.getReportsByTypeAndOrphanId(reportType,orphanId));
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.getReportsByTypeAndOrphanId(reportType,orphanId));
        }
        return reportService.getReportsByTypeAndOrphanId(reportType, orphanId);
    }

    @GetMapping("/getReportsByDate/{date}") // correct // input date: 2000-01-20
    public Object getReportsByDate(@PathVariable("date") LocalDate reportDate,@RequestParam(required = false, defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.getReportByDate(reportDate));
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.getReportByDate(reportDate));
        }
        return reportService.getReportByDate(reportDate);
    }

    @GetMapping("/getReportsByDateBetween/{reportDate1}/{reportDate2}") // correct
    public Object getReportByDateBetween(@PathVariable("reportDate1") LocalDate reportDate1,@PathVariable("reportDate2") LocalDate reportDate2, @RequestParam(required = false, defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.getReportByDateBetween(reportDate1,reportDate2));
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.getReportByDateBetween(reportDate1,reportDate2));
        }
        return reportService.getReportByDateBetween(reportDate1, reportDate2);
    }

    @GetMapping("/getReportsByOrphanId/{orphanId}") // correct
    public Object getReportsByOrphanId(@PathVariable("orphanId") Integer orphanId, @RequestParam(required = false, defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.getReportByOrphanId(orphanId));
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.getReportByOrphanId(orphanId));
        }
        return reportService.getReportByOrphanId(orphanId);
    }

    @GetMapping("/getReportsByReportContent/{reportContent}") // correct
    public Object getReportsByReportContent(@PathVariable("reportContent") String reportContent, @RequestParam(required = false, defaultValue = "false") Boolean csv, @RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE){
            return reportService.exportToCsv(reportService.findByReportContentContains(reportContent));
        }
        if (excel == Boolean.TRUE){
            return reportService.exportToExcel(reportService.findByReportContentContains(reportContent));
        }
        return reportService.findByReportContentContains(reportContent);
    }
}
