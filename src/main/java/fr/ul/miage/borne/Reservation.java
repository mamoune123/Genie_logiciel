package fr.ul.miage.borne;

import java.time.LocalDateTime;

public class Reservation {
	private Long id;
    private String licensePlate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Client client;
    private boolean isGuaranteed;

    // Getters and Setters

    public Reservation(String licensePlate, int duration) {
        this.licensePlate = licensePlate;
        this.startTime = LocalDateTime.now();
        this.endTime = this.startTime.plusMinutes(duration);
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isGuaranteed() {
        return isGuaranteed;
    }

    public void setGuaranteed(boolean guaranteed) {
        isGuaranteed = guaranteed;
    }
}
