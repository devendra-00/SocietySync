package com.devendra.SocietySync.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private LocalDate dob;

    @Column(unique = true)
    private String aadhar;

    private String contactno;

    @Column(unique = true)
    private String email;

    private String gender;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "profile_image")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // ⬅️ New field replacing UserRole entity

    public enum Role {
        OWNER,
        FAMILY
    }

    public User() {
    }

    public User(Long userid, String password, String name, LocalDate dob, String aadhar, String contactno,
                String email, String gender, LocalDateTime createdAt, String profileImage, Role role) {
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.aadhar = aadhar;
        this.contactno = contactno;
        this.email = email;
        this.gender = gender;
        this.createdAt = createdAt;
        this.profileImage = profileImage;
        this.role = role;
    }

    // Getters and Setters
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
}
