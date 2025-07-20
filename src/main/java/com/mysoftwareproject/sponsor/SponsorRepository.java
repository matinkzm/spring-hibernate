package com.mysoftwareproject.sponsor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

    Sponsor findByNationalCode(String nationalCode);
    List<Sponsor> findAllByNameContains(String name);
    List<Sponsor> findAllByLastNameContains(String lastName);
    List<Sponsor> findAllByDateOfBirthGreaterThanEqual(LocalDate dateOfBirth);
    List<Sponsor> findAllByDateOfBirthLessThanEqual(LocalDate dateOfBirth);
    List<Sponsor> findAllByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Sponsor> findAllByConnectorId(Integer connectorId);

    Optional<Sponsor> findByUsername(String username);
    Optional<Sponsor> findByEmail(String email);
    Optional<Sponsor> findByPhoneNumber(String phoneNumber);
}
