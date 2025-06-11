package com.devendra.SocietySync.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "staff")
public class Staff {

    // Enum inside Staff.java
    public enum RoleName {
        MANAGER,
        PERMANENT_STAFF,
        TEMPORARY_STAFF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(length = 12, unique = true)
    private String aadhar;

    private String contactNo;

    private String email;

    private String gender;

    private Boolean isPermanent;

    @Temporal(TemporalType.DATE)
    private Date joiningDate;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleName role;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @PrePersist
    @PreUpdate
    public void updateIsPermanent() {
        if (this.role == RoleName.MANAGER || this.role == RoleName.PERMANENT_STAFF) {
            this.isPermanent = true;
        } else {
            this.isPermanent = false;
        }
    }
    
    

    // Getters and Setters
    

    public Long getStaffId() {
        return staffId;
    }

    public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public void setIsPermanent(Boolean isPermanent) {
		this.isPermanent = isPermanent;
	}


	public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getIsPermanent() {
        return isPermanent;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
