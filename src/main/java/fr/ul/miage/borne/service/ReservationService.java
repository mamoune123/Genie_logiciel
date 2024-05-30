// fr/ul/miage/borne/service/ReservationService.java
package fr.ul.miage.borne.service;

import fr.ul.miage.borne.dao.ReservationBD;
import fr.ul.miage.borne.model.Reservation;

import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final ReservationBD reservationBD = new ReservationBD();

    public void createReservation(Reservation reservation) {
        reservationBD.save(reservation);
    }

    public Optional<Reservation> findReservationByLicensePlate(String licensePlate) {
        return reservationBD.findByLicensePlate(licensePlate);
    }

    public List<Reservation> getAllReservations() {
        return reservationBD.getAllReservations();
    }
}