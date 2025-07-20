package com.mysoftwareproject.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyHistoryRepository extends JpaRepository<PropertyHistory, Integer> {

    List<PropertyHistory> findAllByConsumerId(Integer consumerId);
    List<PropertyHistory> findAllByStartUsingDateLessThanEqual(LocalDate targetDate);
    List<PropertyHistory> findAllByEndUsingDateGreaterThanEqual(LocalDate targetDate);
    List<PropertyHistory> findAllByPropertyId(Integer propertyId);
    List<PropertyHistory> findAllByPropertyNameContaining(String propertyName);
}
