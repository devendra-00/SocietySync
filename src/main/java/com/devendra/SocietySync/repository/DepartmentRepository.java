package com.devendra.SocietySync.repository;

import com.devendra.SocietySync.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Additional query methods if needed
}
