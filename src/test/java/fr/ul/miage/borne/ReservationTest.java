/*package fr.ul.miage.borne;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.*;


public class ReservationTest {

    private Reservation reservation;
    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client("Jane","Doe","13 rue Michel Ney","987654321","jane.doe@example.com","4445555326662",Arrays.asList("B4V3H3B"));
        reservation = new Reservation("AB123CD", LocalDateTime.of(2024, 6, 1, 10, 0), LocalDateTime.of(2024, 6, 1, 12, 0), client, true);
    }

    @Test
    public void testReservationCreation() {
        assertEquals("AB123CD", reservation.getLicensePlate());
        assertEquals(LocalDateTime.of(2024, 6, 1, 10, 0), reservation.getStartTime());
        assertEquals(LocalDateTime.of(2024, 6, 1, 12, 0), reservation.getEndTime());
        assertEquals(client, reservation.getClient());
        assertTrue(reservation.isGuaranteed());
    }

    @Test
    public void testSetId() {
        reservation.setId(1L);
        assertEquals(1L, reservation.getId());
    }

    @Test
    public void testSetLicensePlate() {
        reservation.setLicensePlate("XY987ZT");
        assertEquals("XY987ZT", reservation.getLicensePlate());
    }

    @Test
    public void testSetStartTime() {
        LocalDateTime newStartTime = LocalDateTime.of(2024, 6, 1, 14, 0);
        reservation.setStartTime(newStartTime);
        assertEquals(newStartTime, reservation.getStartTime());
    }

    @Test
    public void testSetEndTime() {
        LocalDateTime newEndTime = LocalDateTime.of(2024, 6, 1, 16, 0);
        reservation.setEndTime(newEndTime);
        assertEquals(newEndTime, reservation.getEndTime());
    }

    @Test
    public void testSetClient() {
        Client newClient = new Client("Jane","Doe","13 rue Michel Ney","987654321","jane.doe@example.com","4445555326662",Arrays.asList("B4V3H3B"));
        reservation.setClient(newClient);
        assertEquals(newClient, reservation.getClient());
    }

    @Test
    public void testSetGuaranteed() {
        reservation.setGuaranteed(false);
        assertFalse(reservation.isGuaranteed());
    }

    @Test
    public void testGetInfo() {
        reservation.setId(1L);
        String expectedInfo = "ID: 1, Matricule: AB123CD, Crenaux = [2024-06-01T10:00|2024-06-01T12:00]";
        assertEquals(expectedInfo, reservation.getInfo());
    }
}*/