package com.devendra.SocietySync.model;

import jakarta.persistence.*;

@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @Column(name = "userid")
    private Long userId;

    @Column(name = "owner_userid", nullable = false)
    private Long ownerUserId;

    @Column(name = "relationship", nullable = false)
    private String relationship;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
