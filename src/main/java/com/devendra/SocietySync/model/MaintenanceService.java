package com.devendra.SocietySync.model;

import jakarta.persistence.*;

@Entity
@Table(name = "maintenance_services")
public class MaintenanceService {

    public enum MaintenanceServiceType {
        CIVIL,
        PLUMBING,
        GARDENING,
        CLEANING,
        CARPENTRY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MaintenanceServiceType name;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department; // Should refer to the Maintenance department

    // Getters and Setters

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public MaintenanceServiceType getName() {
        return name;
    }

    public void setName(MaintenanceServiceType name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
