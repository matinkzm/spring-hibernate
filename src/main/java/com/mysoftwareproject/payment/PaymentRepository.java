package com.mysoftwareproject.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAllByOrphanIdAndPayerId(Integer orphanId, Integer payerId);
    List<Payment> findAllByPayerId(Integer payerId);
    List<Payment> findALlByOrphanId(Integer orphanId);
    List<Payment> findAllByDateBetween(LocalDate from, LocalDate to);
    List<Payment> findAllByDate(LocalDate date);
}
