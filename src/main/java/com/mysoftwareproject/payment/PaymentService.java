package com.mysoftwareproject.payment;

import com.mysoftwareproject.enums.ActiveType;
import com.mysoftwareproject.exception.InvalidInput;
import com.mysoftwareproject.exception.NotActiveException;
import com.mysoftwareproject.exception.NotFoundException;
import com.mysoftwareproject.notification.NotificationService;
import com.mysoftwareproject.sponsor.Sponsor;
import com.mysoftwareproject.sponsor.SponsorService;
import com.mysoftwareproject.sponsorOrphanHistory.SponsorOrphanHistory;
import com.opencsv.CSVWriter;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Lazy;
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
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SponsorService sponsorService;
    private final NotificationService notificationService;

    public PaymentService(PaymentRepository paymentRepository, @Lazy SponsorService sponsorService, NotificationService notificationService) {
        this.paymentRepository = paymentRepository;
        this.sponsorService = sponsorService;
        this.notificationService = notificationService;
    }

    public Payment createPayment(PaymentDto paymentDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(paymentDto.getDate(), formatter);
        Sponsor sponsor = sponsorService.findSponsorById(paymentDto.getPayerId());

        Payment payment = new Payment(paymentDto.getPayerId(), paymentDto.getOrphanId(), paymentDto.getAmount(), paymentDto.getDescription(), date, paymentDto.getSponsorType(), paymentDto.getAccountNumber(), paymentDto.getSheba());
        notificationService.createNotification(paymentDto.getPayerId(),"A payment with value " + paymentDto.getAmount() + " has been sent to orphan " + paymentDto.getOrphanId());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Integer id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment with id: "+ id +" not found"));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePayment(Integer id ,PaymentDto paymentDto) {
        Payment foundPayment = paymentRepository.findById(id).orElse(null);
        if(foundPayment != null) {
            if (paymentDto.getPayerId() != null){
                foundPayment.setPayerId(paymentDto.getPayerId());
            }
            if (paymentDto.getAmount() != null){
                foundPayment.setAmount(paymentDto.getAmount());
            }
            if (paymentDto.getDescription() != null){
                foundPayment.setDescription(paymentDto.getDescription());
            }
            if (paymentDto.getOrphanId() != null){
                foundPayment.setOrphanId(paymentDto.getOrphanId());
            }
            if (paymentDto.getDate() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(paymentDto.getDate(), formatter);
                foundPayment.setDate(date);
            }
            if (paymentDto.getSponsorType() != null){
                foundPayment.setSponsorType(paymentDto.getSponsorType());
            }
            return paymentRepository.save(foundPayment);
        }
        else
            throw new NotFoundException("Payment with id: "+ id +" not found");
    }

    public Double sponsorToOrphanAllPaysSum(Integer orphanId,Integer sponsorId){
        List<Payment> payments = paymentRepository.findAllByOrphanIdAndPayerId(orphanId,sponsorId);
        Double price = 0.0;
        for (Payment payment : payments) {
            price += payment.getAmount();
        }
        return price;
    }

    public List<Payment> getAllPaymentsByPayerId(Integer payerId) {
        return paymentRepository.findAllByPayerId(payerId);
    }

    public List<Payment> getAllPaymentsByOrphanId(Integer orphanId) {
        return paymentRepository.findALlByOrphanId(orphanId);
    }

    public List<Payment> getAllPaymentsByDateBetween(LocalDate from, LocalDate to) {
        return paymentRepository.findAllByDateBetween(from, to);
    }

    public List<Payment> getAllPaymentsByDate(LocalDate date) {
        return paymentRepository.findAllByDate(date);
    }
    private static Integer counter = 0;

    public File exportToCsv(List<Payment> records) {

        counter++;

        String filePath = "payments" + counter + ".csv";
        File csvFile = new File(filePath);


        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            // Write the CSV header
            csvWriter.writeNext(new String[] { "id", "payerId", "orphanId", "amount", "description", "date" });

            // Write data rows
            for (Payment record : records) {
                csvWriter.writeNext(new String[] {
                        record.getId().toString(),
                        record.getPayerId().toString(),
                        record.getOrphanId().toString(),
                        record.getAmount().toString(),
                        record.getDescription(),
                        record.getDate().toString()
                });
            }

            return csvFile;  // Returns the CSV file
        } catch (IOException e) {
            throw new RuntimeException("Failed to write records to CSV file", e);
        }
    }

    public ResponseEntity<Object> exportToExcel(List<Payment> history) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            counter++;
            Sheet sheet = workbook.createSheet("payment");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Payer ID");
            headerRow.createCell(2).setCellValue("Orphan ID");
            headerRow.createCell(3).setCellValue("Amount");
            headerRow.createCell(4).setCellValue("Description");
            headerRow.createCell(5).setCellValue("Date");

            // Fill data rows
            int rowIndex = 1;
            for (Payment entity : history) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(entity.getId());
                row.createCell(1).setCellValue(entity.getPayerId());
                row.createCell(2).setCellValue(entity.getOrphanId());
                row.createCell(3).setCellValue(entity.getAmount());
                row.createCell(4).setCellValue(entity.getDescription());
                row.createCell(5).setCellValue(entity.getDate());
            }

            // Write workbook to byte array

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] excelData =  out.toByteArray();

            try {
                String fileName = "payment_" + counter + ".xlsx";
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
