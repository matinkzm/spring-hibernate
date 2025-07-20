package com.mysoftwareproject.sponsorOrphanHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SponsorOrphanHistoryRepository extends JpaRepository<SponsorOrphanHistory, Integer> {

    List<SponsorOrphanHistory> findAllBySponsorId(Integer sponsorId);
    List<SponsorOrphanHistory> findAllByOrphanId(Integer orphanId);

    List<SponsorOrphanHistory> findAllByStartDateLessThanEqual(LocalDate targetDate);
    List<SponsorOrphanHistory> findAllByEndDateGreaterThanEqual(LocalDate targetDate);
}
