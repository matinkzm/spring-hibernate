package com.mysoftwareproject.payment;

import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping // correct
    public Payment createPayment(@RequestBody PaymentDto paymentDto) {
        return paymentService.createPayment(paymentDto);
    }

    @GetMapping("/getPaymentWithPaymentId/{paymentId}") // correct
    public Payment getPaymentById(@PathVariable("paymentId") Integer paymentId) {
        return paymentService.getPaymentById(paymentId);
    }

    @GetMapping // correct
    public Object getAllPayments(@RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return paymentService.exportToCsv(paymentService.getAllPayments());
        }
        if (excel == Boolean.TRUE) {
            return paymentService.exportToExcel(paymentService.getAllPayments());
        }
        return paymentService.getAllPayments();
    }

    @PutMapping("/{paymentId}") // correct
    public Payment updatePayment(@PathVariable("paymentId") Integer paymentId ,@RequestBody PaymentDto paymentDto) {
        return paymentService.updatePayment(paymentId, paymentDto);
    }

    @GetMapping("/getPaymentsByPayerId/{payerId}") // correct
    public Object getPaymentsByPayerId(@PathVariable("payerId") Integer payerId, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return paymentService.exportToCsv(paymentService.getAllPaymentsByPayerId(payerId));
        }
        if (excel == Boolean.TRUE) {
            return paymentService.exportToExcel(paymentService.getAllPaymentsByPayerId(payerId));
        }
        return paymentService.getAllPaymentsByPayerId(payerId);
    }

    @GetMapping("/getPaymentsByOrphanId/{orphanId}") // correct
    public Object getPaymentsByOrphanId(@PathVariable("orphanId") Integer orphanId, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return paymentService.exportToCsv(paymentService.getAllPaymentsByOrphanId(orphanId));
        }
        if (excel == Boolean.TRUE) {
            return paymentService.exportToExcel(paymentService.getAllPaymentsByOrphanId(orphanId));
        }
        return paymentService.getAllPaymentsByOrphanId(orphanId);
    }

    @GetMapping("/sponsorToOrphanAllPaysSum/{sponsorId}/{orphanId}") // correct
    public Double sponsorToOrphanAllPaysSum(@PathVariable Integer sponsorId, @PathVariable Integer orphanId) {
        return paymentService.sponsorToOrphanAllPaysSum(sponsorId, orphanId);
    }

    @GetMapping("/getPaymentsByDateBetween/{from}/{to}") // correct // input date = 2000-01-01
    public Object getPaymentsByDateBetween(@PathVariable LocalDate from, @PathVariable LocalDate to, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return paymentService.exportToCsv(paymentService.getAllPaymentsByDateBetween(from, to));
        }
        if (excel == Boolean.TRUE) {
            return paymentService.exportToExcel(paymentService.getAllPaymentsByDateBetween(from, to));
        }
        return paymentService.getAllPaymentsByDateBetween(from, to);
    }

    @GetMapping("/getPaymentsByDate/{date}") // correct // input date = 2000-01-01
    public Object getPaymentsByDate(@PathVariable LocalDate date, @RequestParam(required = false,defaultValue = "false") Boolean csv,@RequestParam(required = false,defaultValue = "false") Boolean excel) throws IOException {
        if (csv == Boolean.TRUE) {
            return paymentService.exportToCsv(paymentService.getAllPaymentsByDate(date));
        }
        if (excel == Boolean.TRUE) {
            return paymentService.exportToExcel(paymentService.getAllPaymentsByDate(date));
        }
        return paymentService.getAllPaymentsByDate(date);
    }
}
