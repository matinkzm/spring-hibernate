package com.mysoftwareproject.activeHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.report.Report;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistory;
import com.opencsv.CSVWriter;
import lombok.AllArgsConstructor;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveHistoryService {

    private final ActiveHistoryRepository activeHistoryRepository;
    private static Integer counter = 0;
    @Autowired
    private ObjectMapper objectMapper;

    public ActiveHistoryService(ActiveHistoryRepository activeHistoryRepository) {
        this.activeHistoryRepository = activeHistoryRepository;
    }

    public List<ActiveHistory> getAllActiveHistory() {
        return activeHistoryRepository.findAll();
    }

    public ActiveHistory getActiveHistoryById(Integer id) {
        return activeHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Active History with id " + id + " Not Found"));
    }

    public ActiveHistory updateActiveHistory(Integer id ,ActiveHistoryDto activeHistoryDto) {
        ActiveHistory foundActiveHistory = activeHistoryRepository.findById(id).orElse(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (foundActiveHistory != null) {
            if (activeHistoryDto.getSponsorId() != null){
                foundActiveHistory.setSponsorId(activeHistoryDto.getSponsorId());
            }
            if (activeHistoryDto.getStartDate() != null){
                LocalDate date = LocalDate.parse(activeHistoryDto.getStartDate(), formatter);
                foundActiveHistory.setStartDate(date);
            }
            if (activeHistoryDto.getEndDate() != null){
                LocalDate date = LocalDate.parse(activeHistoryDto.getEndDate(), formatter);
                foundActiveHistory.setEndDate(date);
            }
            if (activeHistoryDto.getIsActive() != null){
                foundActiveHistory.setIsActive(String.valueOf(activeHistoryDto.getIsActive()));
            }
            return activeHistoryRepository.save(foundActiveHistory);
        }
        else
            throw new NotFoundException("Active History with id " + id + " Not Found");
    }

//    public void deleteActiveHistoryById(Integer id) {
//        activeHistoryRepository.deleteById(id);
//    }

    public ActiveHistory addActiveHistory(ActiveHistoryDto activeHistoryDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(activeHistoryDto.getStartDate(),formatter);
        LocalDate endDate = LocalDate.parse(activeHistoryDto.getEndDate(),formatter);

        ActiveHistory activeHistory = new ActiveHistory(activeHistoryDto.getSponsorId(),startDate,endDate,String.valueOf(activeHistoryDto.getIsActive()));
        return activeHistoryRepository.save(activeHistory);
    }

    public List<ActiveHistory> getActiveHistoryBySponsorId(Integer sponsorId) {
        return activeHistoryRepository.findAllBySponsorId(sponsorId);
    }

    public List<ActiveHistory> getActiveHistoryByStartDateLessThan(LocalDate startDate) {
        return activeHistoryRepository.findAllByStartDateLessThanEqual(startDate);
    }

    public List<ActiveHistory> getActiveHistoryByEndDateGreaterThan(LocalDate endDate) {
        return activeHistoryRepository.findAllByEndDateGreaterThanEqual(endDate);
    }

    public List<ActiveHistory> getActiveHistoryByDateBetween(LocalDate date) {
        List<ActiveHistory> list1 = activeHistoryRepository.findAllByStartDateLessThanEqual(date);
        List<ActiveHistory> list2 = activeHistoryRepository.findAllByEndDateGreaterThanEqual(date);
        List<ActiveHistory> list3 = new ArrayList<>();

        for (ActiveHistory activeHistory : list1) {
            if (list2.contains(activeHistory)) {
                list3.add(activeHistory);
            }
        }
        return list3;
    }

    public File exportToCsv(List<ActiveHistory> records) {

        counter++;
        String filePath = "active_history" + counter + ".csv";
        File csvFile = new File(filePath);


        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write the CSV header
            csvWriter.writeNext(new String[] { "id","sponsorId", "startDate", "endDate", "isActive" });

            // Write data rows
            for (ActiveHistory record : records) {
                csvWriter.writeNext(new String[] {
                        record.getId().toString(),
                        record.getSponsorId().toString(),
                        record.getStartDate().toString(),
                        record.getEndDate().toString(),
                        record.getIsActive().toString()
                });
            }

            return csvFile;  // Returns the CSV file
        } catch (IOException e) {
            throw new RuntimeException("Failed to write records to CSV file", e);
        }
    }
    public ResponseEntity<Object> exportToExcel(List<ActiveHistory> history) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            counter++;
            Sheet sheet = workbook.createSheet("Active history");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("sponsorId");
            headerRow.createCell(2).setCellValue("startDate");
            headerRow.createCell(3).setCellValue("endDate");
            headerRow.createCell(4).setCellValue("isActive");

            // Fill data rows
            int rowIndex = 1;
            for (ActiveHistory entity : history) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getSponsorId().toString());
                row.createCell(2).setCellValue(entity.getStartDate().toString());
                row.createCell(3).setCellValue(entity.getEndDate().toString());
                row.createCell(4).setCellValue(entity.getIsActive().toString());

            }

            // Write workbook to byte array

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelData =  out.toByteArray();

            try {
                String fileName = "active_history"+ counter +".xlsx";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fileName);

                return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}
