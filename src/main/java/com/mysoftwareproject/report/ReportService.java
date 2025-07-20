package com.mysoftwareproject.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.notification.NotificationService;
import com.mysoftwareproject.orphan.Orphan;
import com.mysoftwareproject.orphan.OrphanService;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final NotificationService notificationService;
    private final OrphanService orphanService;
    @Autowired
    private ObjectMapper objectMapper;

    public ReportService(ReportRepository reportRepository, NotificationService notificationService, OrphanService orphanService) {
        this.reportRepository = reportRepository;
        this.notificationService = notificationService;
        this.orphanService = orphanService;
    }

    public Report createReport(ReportDto reportDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(reportDto.getReportDate(),formatter);
        Orphan orphan = orphanService.getOrphanById(reportDto.getOrphanId());
        if(orphan.getSponsorId() != null){
            notificationService.createNotification(orphan.getSponsorId(), "A new report with type " + reportDto.getReportType() + " for orphan "+orphan.getId()+" has been created");
        }
        Report report = new Report(reportDto.getOrphanId(),reportDto.getReportType(),date,reportDto.getReportContent());
        return reportRepository.save(report);
    }

    public Report getReportById(Integer id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report with id "+id+" not found"));
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report updateReport(Integer id,ReportDto reportDto) {
        Report foundReport = reportRepository.findById(id).orElse(null);
        if(foundReport != null) {
            if(reportDto.getOrphanId() != null){
                foundReport.setOrphanId(reportDto.getOrphanId());
            }
            if(reportDto.getReportDate() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(reportDto.getReportDate(),formatter);
                foundReport.setReportDate(date);
            }
            if(reportDto.getReportType() != null){
                foundReport.setReportType(reportDto.getReportType());
            }
            if(reportDto.getReportContent() != null){
                foundReport.setReportContent(reportDto.getReportContent());
            }
            return reportRepository.save(foundReport);
        }
        else
            throw new NotFoundException("Report with id "+id+" not found");
    }

    public void deleteReportById(Integer id) {
        reportRepository.deleteById(id);
    }

    public List<Report> getReportsByTypeAndOrphanId(String reportType, Integer orphanId) {
        return reportRepository.findAllByReportTypeAndOrphanId(reportType,orphanId);
    }

    public List<Report> getReportByDate(LocalDate reportDate) {
        return reportRepository.findAllByReportDate(reportDate);
    }

    public List<Report> getReportByDateBetween(LocalDate reportDate1, LocalDate reportDate2) {
        return reportRepository.findAllByReportDateBetween(reportDate1, reportDate2);
    }

    public List<Report> getReportByOrphanId(Integer orphanId) {
        return reportRepository.findAllByOrphanId(orphanId);
    }

    private static Integer counter = 0;

    public File exportToCsv(List<Report> records) {

        counter++;
        String filePath = "report" + counter + ".csv";
        File csvFile = new File(filePath);


        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write the CSV header
            csvWriter.writeNext(new String[] { "id", "orphanId", "reportType", "reportDate", "reportContent" });

            // Write data rows
            for (Report record : records) {
                csvWriter.writeNext(new String[] {
                        record.getId().toString(),
                        record.getOrphanId().toString(),
                        record.getReportType(),
                        record.getReportDate().toString(),
                        record.getReportContent()
                });
            }

            return csvFile;  // Returns the CSV file
        } catch (IOException e) {
            throw new RuntimeException("Failed to write records to CSV file", e);
        }

    }

    public ResponseEntity<Object> exportToExcel(List<Report> history) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            counter++;
            Sheet sheet = workbook.createSheet("Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("orphanId");
            headerRow.createCell(2).setCellValue("reportType");
            headerRow.createCell(3).setCellValue("reportDate");
            headerRow.createCell(4).setCellValue("reportContext");

            // Fill data rows
            int rowIndex = 1;
            for (Report entity : history) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getOrphanId().toString());
                row.createCell(2).setCellValue(entity.getReportType());
                row.createCell(3).setCellValue(entity.getReportDate().toString());
                row.createCell(4).setCellValue(entity.getReportContent());

            }

            // Write workbook to byte array

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelData =  out.toByteArray();

            try {
                String fileName = "report" + counter + ".xlsx";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);

                return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<Report> findByReportContentContains(String reportContent) {
        return reportRepository.findAllByReportContentContains(reportContent);
    }

}
