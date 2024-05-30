// fr/ul/miage/borne/controller/ReservationController.java
package fr.ul.miage.borne.controller;

import fr.ul.miage.borne.model.Reservation;
import fr.ul.miage.borne.service.ReservationService;

import java.util.List;
import java.util.Optional;

public class ReservationController {
    private final ReservationService reservationService = new ReservationService();

    public void createReservation(Reservation reservation) {
        reservationService.createReservation(reservation);
    }

    public Optional<Reservation> findReservationByLicensePlate(String licensePlate) {
        return reservationService.findReservationByLicensePlate(licensePlate);
    }

    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}