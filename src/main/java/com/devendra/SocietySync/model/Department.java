package com.devendra.SocietySync.model;

import jakarta.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    public enum DepartmentName {
        MAINTENANCE,
        FINANCE,
        ADMINISTRATION,
        SECURITY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DepartmentName name;

    @Column
    private String description;

    // Getters and Setters

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public DepartmentName getName() {
        return name;
    }

    public void setName(DepartmentName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
