// fr/ul/miage/borne/model/ChargeStation.java
package fr.ul.miage.borne.model;

public class ChargeStation {
    private Long id;
    private String location;
    private String status;  // Values: 'available', 'occupied', 'reserved', 'unavailable'

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}