package fr.ul.miage.borne;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MenuTest {
	 @Mock
	    private ClientBD clientBD;

	    @Mock
	    private ReservationBD reservationDAO;

	    @Mock
	    private ChargeStationBD chargeStationDAO;

	    @InjectMocks
	    private Menu menu;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testRegisterClient() {
	        String input = "John\nDoe\n123 Main St\n555-5555\njohn.doe@example.com\n1234567890123456\nABC123,DEF456\n";
	        InputStream in = new ByteArrayInputStream(input.getBytes());
	        System.setIn(in);

	        Scanner scanner = new Scanner(System.in);
	        menu.registerClient(scanner);

	        verify(clientBD, times(1)).save(any(Client.class));
	    }

	    @Test
	    public void testMakeReservation() {
	        String email = "john.doe@example.com";
	        Client client = new Client("John", "Doe", "123 Main St", "555-5555", email, "1234567890123456", Arrays.asList("ABC123"));

	        when(clientBD.findByEmail(email)).thenReturn(Optional.of(client));
	        when(chargeStationDAO.findAvailableStations()).thenReturn(Arrays.asList(
	                new ChargeStation(1L, "Location1", "available"),
	                new ChargeStation(2L, "Location2", "available")
	        ));

	        String input = email + "\n1\n60\nABC123\n";
	        InputStream in = new ByteArrayInputStream(input.getBytes());
	        System.setIn(in);

	        Scanner scanner = new Scanner(System.in);
	        menu.makeReservation(scanner);

	        verify(reservationDAO, times(1)).save(any(Reservation.class));
	        verify(chargeStationDAO, times(1)).updateStationStatus(eq(1L), eq("reserved"));
	    }

	    @Test
	    public void testHandleLicensePlate() {
	        String licensePlate = "ABC123";
	        Client client = new Client("John", "Doe", "123 Main St", "555-5555", "john.doe@example.com", "1234567890123456", Arrays.asList(licensePlate));
	        Reservation reservation = new Reservation(licensePlate, LocalDateTime.now(), LocalDateTime.now().plusHours(1), client, false);

	        when(clientBD.findByLicensePlate(licensePlate)).thenReturn(Optional.of(client));
	        when(reservationDAO.findByLicensePlate(licensePlate)).thenReturn(Optional.of(reservation));

	        String input = licensePlate + "\n";
	        InputStream in = new ByteArrayInputStream(input.getBytes());
	        System.setIn(in);

	        Scanner scanner = new Scanner(System.in);
	        menu.handleLicensePlate(scanner);

	        verify(reservationDAO, times(1)).findByLicensePlate(eq(licensePlate));
	    }

	    @Test
	    public void testHandleReservationNumber() {
	        String reservationNumber = "R123";
	        Reservation reservation = new Reservation("ABC123", LocalDateTime.now(), LocalDateTime.now().plusHours(1), new Client(), false);

	        when(reservationDAO.findByLicensePlate(reservationNumber)).thenReturn(Optional.of(reservation));

	        String input = reservationNumber + "\n";
	        InputStream in = new ByteArrayInputStream(input.getBytes());
	        System.setIn(in);

	        Scanner scanner = new Scanner(System.in);
	        menu.handleReservationNumber(scanner);

	        verify(reservationDAO, times(1)).findByLicensePlate(eq(reservationNumber));
	    }

	    @Test
	    public void testShowAllClients() {
	        when(clientBD.getAllClients()).thenReturn(Arrays.asList(
	                new Client("John", "Doe", "123 Main St", "555-5555", "john.doe@example.com", "1234567890123456", Arrays.asList("ABC123")),
	                new Client("Jane", "Smith", "456 Elm St", "555-5556", "jane.smith@example.com", "9876543210987654", Arrays.asList("XYZ789"))
	        ));

	        menu.showAllClients();

	        verify(clientBD, times(1)).getAllClients();
	    }

	    @Test
	    public void testShowAllReservations() {
	        when(reservationDAO.getAllReservations()).thenReturn(Arrays.asList(
	                new Reservation("ABC123", LocalDateTime.now(), LocalDateTime.now().plusHours(1), new Client(), false),
	                new Reservation("XYZ789", LocalDateTime.now(), LocalDateTime.now().plusHours(2), new Client(), true)
	        ));

	        menu.showAllReservations();

	        verify(reservationDAO, times(1)).getAllReservations();
	    }

}
