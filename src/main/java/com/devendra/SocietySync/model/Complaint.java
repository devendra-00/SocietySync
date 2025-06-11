package com.devendra.SocietySync.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "complaints")
public class Complaint {

    public enum Status {
        CREATED,
        ATTENDED,
        RESOLVED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // complaint creator

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff attendedBy;  // staff attending complaint (nullable)

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // department responsible

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date attendedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        if (this.status == null) {
            this.status = Status.CREATED;
        }
    }

    // Getters and Setters

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Staff getAttendedBy() {
        return attendedBy;
    }

    public void setAttendedBy(Staff attendedBy) {
        this.attendedBy = attendedBy;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getAttendedAt() {
        return attendedAt;
    }

    public void setAttendedAt(Date attendedAt) {
        this.attendedAt = attendedAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Date resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}
