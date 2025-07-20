package com.mysoftwareproject.property;

import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.manager.Manager;
import com.mysoftwareproject.manager.ManagerService;
import com.mysoftwareproject.notification.NotificationService;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistory;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyHistoryService {

    private final PropertyHistoryRepository propertyHistoryRepository;
    private final ManagerService managerService;
    private final NotificationService notificationService;

    public PropertyHistoryService(PropertyHistoryRepository propertyHistoryRepository, ManagerService managerService, NotificationService notificationService) {
        this.propertyHistoryRepository = propertyHistoryRepository;
        this.managerService = managerService;
        this.notificationService = notificationService;
    }

    public PropertyHistory addPropertyHistory(PropertyHistoryDto propertyHistoryDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(propertyHistoryDto.getStartUsingDate(),formatter);
        LocalDate endDate = LocalDate.parse(propertyHistoryDto.getEndUsingDate(),formatter);

        PropertyHistory propertyHistory = new PropertyHistory(propertyHistoryDto.getPropertyId(), propertyHistoryDto.getConsumerId(), propertyHistoryDto.getPropertyName(), startDate,endDate);
        return propertyHistoryRepository.save(propertyHistory);
    }

    public PropertyHistory updatePropertyHistory(Integer id, PropertyHistoryDto propertyHistoryDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PropertyHistory propertyHistory = propertyHistoryRepository.findById(id).orElse(null);

        if (propertyHistory != null) {
            if (propertyHistoryDto.getPropertyId() != null) {
                propertyHistory.setPropertyId(propertyHistoryDto.getPropertyId());
            }
            if (propertyHistoryDto.getConsumerId() != null) {
                propertyHistory.setConsumerId(propertyHistoryDto.getConsumerId());
            }
            if (propertyHistoryDto.getPropertyName() != null) {
                propertyHistory.setPropertyName(propertyHistoryDto.getPropertyName());
            }
            if (propertyHistoryDto.getStartUsingDate() != null) {
                LocalDate startDate = LocalDate.parse(propertyHistoryDto.getStartUsingDate(),formatter);
                propertyHistory.setStartUsingDate(startDate);
            }
            if (propertyHistoryDto.getEndUsingDate() != null) {
                LocalDate endDate = LocalDate.parse(propertyHistoryDto.getEndUsingDate(),formatter);
                propertyHistory.setEndUsingDate(endDate);
            }
            return propertyHistoryRepository.save(propertyHistory);
        }
        else
            throw new NotFoundException("Property history with id "+id+" not found");
    }

//    public void deletePropertyHistory(Integer id) {
//        propertyHistoryRepository.deleteById(id);
//    }

    public List<PropertyHistory> findPropertyHistoryByDate(LocalDate date) {
        List<PropertyHistory> list1 = propertyHistoryRepository.findAllByStartUsingDateLessThanEqual(date);
        List<PropertyHistory> list2 = propertyHistoryRepository.findAllByEndUsingDateGreaterThanEqual(date);
        List<PropertyHistory> list3 = new ArrayList<PropertyHistory>();
        for (PropertyHistory propertyHistory : list1) {
            if (list2.contains(propertyHistory)) {
                list3.add(propertyHistory);
            }
        }
        return list3;
    }

    public List<PropertyHistory> findPropertyHistoryByConsumerId(Integer consumerId) {
        return propertyHistoryRepository.findAllByConsumerId(consumerId);
    }

    public List<PropertyHistory> findPropertyHistoryByPropertyId(Integer id) {
        return propertyHistoryRepository.findAllByPropertyId(id);
    }

    public List<PropertyHistory> findPropertyHistoryByPropertyName(String propertyName) {
        return propertyHistoryRepository.findAllByPropertyNameContaining(propertyName);
    }

    public List<PropertyHistory> findAllPropertyHistory() {
        return propertyHistoryRepository.findAll();
    }

    public PropertyHistory findPropertyHistoryById(Integer id) {
        return propertyHistoryRepository.findById(id).orElse(null);
    }

    public List<PropertyHistory> findAllPropertyHistoryByStartUsingDateLessThanEqual(LocalDate date) {
        return propertyHistoryRepository.findAllByStartUsingDateLessThanEqual(date);
    }
    public List<PropertyHistory> findAllPropertyHistoryByEndUsingDateGreaterThanEqual(LocalDate date) {
        return propertyHistoryRepository.findAllByEndUsingDateGreaterThanEqual(date);
    }

    @Scheduled(fixedRate = 300000) // Runs every 5 minutes
    public void checkExpirationDateAndSendNotification() {
        LocalDate today = LocalDate.now();

        findAllPropertyHistory().forEach(propertyHistory -> {
            LocalDate targetDate = propertyHistory.getExpirationDate();

            // Calculate the days difference
            long daysDifference = ChronoUnit.DAYS.between(targetDate, today);

            if (daysDifference == 3) {
                List<Manager> myList = managerService.getAllManagers();
                for (Manager manager : myList) {
                    notificationService.createNotification(manager.getId(), "property with id "+ propertyHistory.getPropertyId()+" expiration date is near");
                }
            }

            if (daysDifference == 0) {
                List<Manager> myList = managerService.getAllManagers();
                for (Manager manager : myList) {
                    notificationService.createNotification(manager.getId(), "property with id "+ propertyHistory.getPropertyId()+" expiration date is today");
                }
            }

        });
    }
    private static Integer counter = 0;

    public File exportToCsv(List<PropertyHistory> records) {

        counter++;

        String filePath = "property_history" + counter + ".csv";
        File csvFile = new File(filePath);


        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write the CSV header
            csvWriter.writeNext(new String[] { "id", "propertyId", "consumerId", "propertyName", "startUsingDate","endUsingDate", "expirationDate" });

            // Write data rows
            for (PropertyHistory record : records) {
                csvWriter.writeNext(new String[] {
                        record.getId().toString(),
                        record.getPropertyId().toString(),
                        record.getConsumerId().toString(),
                        record.getPropertyName(),
                        record.getStartUsingDate().toString(),
                        record.getEndUsingDate().toString(),
                        record.getExpirationDate().toString()
                });
            }

            return csvFile;  // Returns the CSV file
        } catch (IOException e) {
            throw new RuntimeException("Failed to write records to CSV file", e);
        }
    }

    public ResponseEntity<Object> exportToExcel(List<PropertyHistory> history) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            counter++;
            Sheet sheet = workbook.createSheet("property history");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("propertyId");
            headerRow.createCell(2).setCellValue("consumerId");
            headerRow.createCell(3).setCellValue("propertyName");
            headerRow.createCell(4).setCellValue("startUsingDate");
            headerRow.createCell(5).setCellValue("endUsingDate");
            headerRow.createCell(6).setCellValue("expirationDate");

            // Fill data rows
            int rowIndex = 1;
            for (PropertyHistory entity : history) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getPropertyId());
                row.createCell(2).setCellValue(entity.getConsumerId());
                row.createCell(3).setCellValue(entity.getPropertyName());
                row.createCell(4).setCellValue(entity.getStartUsingDate());
                row.createCell(5).setCellValue(entity.getEndUsingDate());
                row.createCell(6).setCellValue(entity.getExpirationDate());
            }

            // Write workbook to byte array

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelData =  out.toByteArray();

            try {
                String fileName = "property_history_" + counter + ".xlsx";
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
