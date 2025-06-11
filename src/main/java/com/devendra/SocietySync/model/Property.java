package com.devendra.SocietySync.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propertyId;

    @ManyToOne
    @JoinColumn(name = "owner_userid", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String number;

    private Integer floor;

    private String block;

    private String field;

    private Double area;

    @Column(name = "is_occupied")
    private Boolean isOccupied;

    @Column(name = "ownership_type")
    private String ownershipType;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parking> parkingSlots = new ArrayList<>();

    // Constructors
    public Property(Long propertyId, User owner, String number, Integer floor, String block, String field, Double area,
			Boolean isOccupied, String ownershipType, List<Parking> parkingSlots) {
		super();
		this.propertyId = propertyId;
		this.owner = owner;
		this.number = number;
		this.floor = floor;
		this.block = block;
		this.field = field;
		this.area = area;
		this.isOccupied = isOccupied;
		this.ownershipType = ownershipType;
		this.parkingSlots = parkingSlots;
	}
    

    // Getters and setters

    public Long getPropertyId() { return propertyId; }
	public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public Boolean getIsOccupied() { return isOccupied; }
    public void setIsOccupied(Boolean isOccupied) { this.isOccupied = isOccupied; }

    public String getOwnershipType() { return ownershipType; }
    public void setOwnershipType(String ownershipType) { this.ownershipType = ownershipType; }

    public List<Parking> getParkingSlots() { return parkingSlots; }
    public void setParkingSlots(List<Parking> parkingSlots) { this.parkingSlots = parkingSlots; }
}
