package com.mysoftwareproject.orphan;

import com.mysoftwareproject.sponsor.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrphanRepository extends JpaRepository<Orphan, Integer> {

    Orphan findByNationalCode(String nationalCode);
    List<Orphan> findAllByNameContains(String name);
    List<Orphan> findAllByLastNameContains(String lastName);
    Orphan findByEmail(String email);
    List<Orphan> findAllByDateOfBirthGreaterThanEqual(LocalDate dateOfBirth);
    List<Orphan> findAllByDateOfBirthLessThanEqual(LocalDate dateOfBirth);
    List<Orphan> findAllByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Orphan> findAllBySponsorIdIsNull();
    List<Orphan> findAllBySponsorId(Integer sponsorId);
}
