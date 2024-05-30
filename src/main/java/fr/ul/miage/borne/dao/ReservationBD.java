// fr/ul/miage/borne/dao/ReservationBD.java
package fr.ul.miage.borne.dao;

import fr.ul.miage.borne.model.Reservation;
import fr.ul.miage.borne.config.Db;

import java.sql.*;
import java.util.*;

public class ReservationBD {

    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservations (license_plate, start_time, end_time, client_id, is_guaranteed) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reservation.getLicensePlate());
            pstmt.setTimestamp(2, Timestamp.valueOf(reservation.getStartTime()));
            pstmt.setTimestamp(3, Timestamp.valueOf(reservation.getEndTime()));
            pstmt.setLong(4, reservation.getClient().getId());
            pstmt.setBoolean(5, reservation.isGuaranteed());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservation.setId(generatedKeys.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<Reservation> findByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM reservations WHERE license_plate = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getLong("id"));
                reservation.setLicensePlate(rs.getString("license_plate"));
                reservation.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                reservation.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                reservation.setGuaranteed(rs.getBoolean("is_guaranteed"));

                ClientBD clientbd = new ClientBD();
                clientbd.findByEmail(rs.getString("client_id")).ifPresent(reservation::setClient);

                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";

        try (Connection conn = Db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getLong("id"));
                reservation.setLicensePlate(rs.getString("license_plate"));
                reservation.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
                reservation.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
                reservation.setGuaranteed(rs.getBoolean("is_guaranteed"));

                ClientBD clientDAO = new ClientBD();
                clientDAO.findByEmail(rs.getString("client_id")).ifPresent(reservation::setClient);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reservations;
    }
}