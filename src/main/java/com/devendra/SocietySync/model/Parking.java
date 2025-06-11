package com.devendra.SocietySync.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Parking")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingId;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    public enum SlotType {
        Car, Bike
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotType slotType;  // e.g., "4-vehicle", "2-vehicle"

    

    public Parking(Long parkingId, Property property, SlotType slotType) {
		super();
		this.parkingId = parkingId;
		this.property = property;
		this.slotType = slotType;
	}
    
	// Getters and setters
    public Long getParkingId() { return parkingId; }
    public void setParkingId(Long parkingId) { this.parkingId = parkingId; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public SlotType getSlotType() { return slotType; }
    public void setSlotType(SlotType slotType) { this.slotType = slotType; }
}
