package com.mysoftwareproject.activeHistory;

import com.mysoftwareproject.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActiveHistoryRepository extends JpaRepository<ActiveHistory, Integer> {

    List<ActiveHistory> findAllByStartDateLessThanEqual(LocalDate targetDate);
    List<ActiveHistory> findAllByEndDateGreaterThanEqual(LocalDate targetDate);
    List<ActiveHistory> findAllBySponsorId(Integer sponsorId);

}
