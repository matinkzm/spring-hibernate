package com.mysoftwareproject.manager;

import com.mysoftwareproject.sponsor.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByNationalCode(String nationalCode);
    Optional<Manager> findByUsername(String username);
    Optional<Manager> findByEmail(String email);
    Optional<Manager> findByPhoneNumber(String phoneNumber);
}
