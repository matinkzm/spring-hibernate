package com.mysoftwareproject.sponsorOrphanHistory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysoftwareproject.exception.NotFoundException;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SponsorOrphanHistoryService {

    private final SponsorOrphanHistoryRepository sponsorOrphanHistoryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static Integer counter = 0;

    public SponsorOrphanHistoryService(SponsorOrphanHistoryRepository sponsorOrphanHistoryRepository) {
        this.sponsorOrphanHistoryRepository = sponsorOrphanHistoryRepository;
    }

    public List<SponsorOrphanHistory> getAllSponsorOrphanHistory() {
        return sponsorOrphanHistoryRepository.findAll();
    }

    public List<SponsorOrphanHistory> getSponsorOrphanHistoryBySponsorId(Integer id) {
        return sponsorOrphanHistoryRepository.findAllBySponsorId(id);
    }

    public List<SponsorOrphanHistory> getSponsorOrphanHistoryByOrphanId(Integer id) {
        return sponsorOrphanHistoryRepository.findAllByOrphanId(id);
    }

    public SponsorOrphanHistory findSponsorOrphanHistoryById(Integer id) {
        return sponsorOrphanHistoryRepository.findById(id).orElse(null);
    }

    public SponsorOrphanHistory addSponsorOrphanHistory(SponsorOrphanHistoryDto sponsorOrphanHistoryDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(sponsorOrphanHistoryDto.getStartDate(),formatter);
        LocalDate endDate = LocalDate.parse(sponsorOrphanHistoryDto.getEndDate(),formatter);

        SponsorOrphanHistory sponsorOrphanHistory = new SponsorOrphanHistory(sponsorOrphanHistoryDto.getSponsorId(),sponsorOrphanHistoryDto.getOrphanId(),startDate,endDate);

        return sponsorOrphanHistoryRepository.save(sponsorOrphanHistory);
    }

    public SponsorOrphanHistory updateSponsorOrphanHistory(Integer id, SponsorOrphanHistoryDto sponsorOrphanHistoryDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SponsorOrphanHistory sponsorOrphanHistory = sponsorOrphanHistoryRepository.findById(id).orElse(null);
        if (sponsorOrphanHistory != null) {
            if (sponsorOrphanHistoryDto.getOrphanId() != null){
                sponsorOrphanHistory.setOrphanId(sponsorOrphanHistoryDto.getOrphanId());
            }
            if (sponsorOrphanHistoryDto.getStartDate() != null) {
                LocalDate startDate = LocalDate.parse(sponsorOrphanHistoryDto.getStartDate(), formatter);
                sponsorOrphanHistory.setStartDate(startDate);
            }
            if (sponsorOrphanHistoryDto.getEndDate() != null) {
                LocalDate endDate = LocalDate.parse(sponsorOrphanHistoryDto.getEndDate(), formatter);
                sponsorOrphanHistory.setEndDate(endDate);
            }
            if (sponsorOrphanHistoryDto.getSponsorId() != null) {
                sponsorOrphanHistory.setSponsorId(id);
            }
            return sponsorOrphanHistoryRepository.save(sponsorOrphanHistory);
        }
        else
            throw new NotFoundException("SponsorOrphanHistory with id "+id+" not found");
    }

    public SponsorOrphanHistory updateSponsorOrphanHistory(Integer id, SponsorOrphanHistory sponsorOrphanHistory) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SponsorOrphanHistory foundSponsorOrphanHistory = sponsorOrphanHistoryRepository.findById(id).orElse(null);
        if (sponsorOrphanHistory != null) {
            if (sponsorOrphanHistory.getOrphanId() != null){
                foundSponsorOrphanHistory.setOrphanId(sponsorOrphanHistory.getOrphanId());
            }
            if (sponsorOrphanHistory.getStartDate() != null) {
                LocalDate startDate = LocalDate.parse(formatter.format(sponsorOrphanHistory.getStartDate()), formatter);
                foundSponsorOrphanHistory.setStartDate(startDate);
            }
            if (sponsorOrphanHistory.getEndDate() != null) {
                LocalDate endDate = LocalDate.parse(formatter.format(sponsorOrphanHistory.getEndDate()), formatter);
                foundSponsorOrphanHistory.setEndDate(endDate);
            }
            if (sponsorOrphanHistory.getSponsorId() != null) {
                foundSponsorOrphanHistory.setSponsorId(id);
            }
            return sponsorOrphanHistoryRepository.save(sponsorOrphanHistory);
        }
        else
            throw new NotFoundException("SponsorOrphanHistory with id "+id+" not found");
    }


    public void deleteSponsorOrphanHistory(Integer id) {
        sponsorOrphanHistoryRepository.deleteById(id);
    }

    public List<SponsorOrphanHistory> findSponsorOrphanHistoryByDate(LocalDate date) {
        List<SponsorOrphanHistory> list1 = sponsorOrphanHistoryRepository.findAllByStartDateLessThanEqual(date);
        List<SponsorOrphanHistory> list2 = sponsorOrphanHistoryRepository.findAllByEndDateGreaterThanEqual(date);
        List<SponsorOrphanHistory> List3 = new ArrayList<>();

        for (SponsorOrphanHistory sponsorOrphanHistory : list1) {
            if (list2.contains(sponsorOrphanHistory)) {
                List3.add(sponsorOrphanHistory);
            }
        }
        return List3;
    }

    public List<SponsorOrphanHistory> findSponsorOrphanHistoryByStartDateLessThanEqual(LocalDate date) {
        return sponsorOrphanHistoryRepository.findAllByStartDateLessThanEqual(date);
    }

    public List<SponsorOrphanHistory> findSponsorOrphanHistoryByEndDateGreaterThanEqual(LocalDate date) {
        return sponsorOrphanHistoryRepository.findAllByEndDateGreaterThanEqual(date);
    }

    public File exportToCsv(List<SponsorOrphanHistory> records) {

        counter++;

        String filePath = "sponsor_orphan_history" + counter + ".csv";
        File csvFile = new File(filePath);


        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write the CSV header
            csvWriter.writeNext(new String[] { "id", "sponsorId", "orphanId", "startDate", "endDate" });

            // Write data rows
            for (SponsorOrphanHistory record : records) {
                csvWriter.writeNext(new String[] {
                        record.getId().toString(),
                        record.getSponsorId().toString(),
                        record.getOrphanId().toString(),
                        record.getStartDate().toString(),
                        record.getEndDate().toString()
                });
            }

            return csvFile;  // Returns the CSV file
        } catch (IOException e) {
            throw new RuntimeException("Failed to write records to CSV file", e);
        }
    }

    public ResponseEntity<Object> exportToExcel(List<SponsorOrphanHistory> history) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            counter++;
            Sheet sheet = workbook.createSheet("Sponsor orphan history");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("sponsorId");
            headerRow.createCell(2).setCellValue("orphanId");
            headerRow.createCell(3).setCellValue("startDate");
            headerRow.createCell(4).setCellValue("endDate");

            // Fill data rows
            int rowIndex = 1;
            for (SponsorOrphanHistory entity : history) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getSponsorId().toString());
                row.createCell(2).setCellValue(entity.getOrphanId().toString());
                row.createCell(3).setCellValue(entity.getStartDate().toString());
                row.createCell(4).setCellValue(entity.getEndDate().toString());

            }

            // Write workbook to byte array

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelData =  out.toByteArray();

            try {
                String fileName = "sponsor_orphan_history_" + counter + ".xlsx";
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
