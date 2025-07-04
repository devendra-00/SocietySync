package com.devendra.SocietySync.repository;

import com.devendra.SocietySync.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByAadhar(String aadhar);
}
