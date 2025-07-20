package com.mysoftwareproject.property;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findAllByName(String name);
    List<Property> findAllByAvailability(String availability);

}
