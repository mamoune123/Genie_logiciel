package fr.ul.miage.borne;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

public class Menu {

    private static final ClientBD clientBD = new ClientBD();
    private static final ReservationBD reservationDAO = new ReservationBD();
    private static final ChargeStationBD chargeStationDAO = new ChargeStationBD();

    public static void main(String[] args) {
        DbSetUp.main(new String[0]);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Inscription du client");
            System.out.println("2. Entrer le numéro de plaque d'immatriculation");
            System.out.println("3. Faire une reservation");
            System.out.println("4. Entrer le numéro de réservation");
            System.out.println("5. Afficher tous les clients");
            System.out.println("6. Afficher toutes les réservations");
            System.out.println("7. Quitter");
            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            try {
                switch (choice) {
                    case 1:
                        registerClient(scanner);
                        break;
                    case 2:
                        handleLicensePlate(scanner);
                        break;
                    case 3:
                        makeReservation(scanner);
                        break;
                    case 4:
                        handleReservationNumber(scanner);
                        break;
                    case 5:
                        showAllClients();
                        break;
                    case 6:
                        showAllReservations();
                        break;
                    case 7:
                        System.out.println("Au revoir!");
                        return;
                    default:
                        System.out.println("Option invalide, veuillez réessayer.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    static void makeReservation(Scanner scanner) {
        System.out.print("Entrez votre adresse email: ");
        String email = scanner.nextLine();
        Optional<Client> clientOpt = clientBD.findByEmail(email);

        if (!clientOpt.isPresent()) {
            throw new IllegalArgumentException("Email non reconnu. Veuillez vous inscrire.");
        }

        Client client = clientOpt.get();
        List<ChargeStation> availableStations = chargeStationDAO.findAvailableStations();

        if (availableStations.isEmpty()) {
            throw new IllegalArgumentException("Désolé, il n'y a pas de bornes de recharge disponibles actuellement.");
        }

        System.out.println("Bornes disponibles:");
        for (int i = 0; i < availableStations.size(); i++) {
            System.out.println((i + 1) + ". " + availableStations.get(i).getLocation());
        }

        System.out.print("Choisissez une borne (1-" + availableStations.size() + "): ");
        int stationChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (stationChoice < 1 || stationChoice > availableStations.size()) {
            throw new IllegalArgumentException("Choix invalide.");
        }

        ChargeStation chosenStation = availableStations.get(stationChoice - 1);

        System.out.print("Entrez la durée prévue de recharge en minutes: ");
        int duration = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (duration <= 0) {
            throw new IllegalArgumentException("La durée doit être un nombre positif.");
        }

        System.out.print("Entrez le numéro de plaque d'immatriculation: ");
        String licensePlate = scanner.nextLine();

        if (licensePlate.isEmpty()) {
            throw new IllegalArgumentException("Le numéro de plaque d'immatriculation ne peut pas être vide.");
        }

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusMinutes(duration);

        Reservation reservation = new Reservation(licensePlate, startTime, endTime, client, false);
        reservationDAO.save(reservation);

        ChargeStationBD.updateStationStatus(chosenStation.getId(), "reserved");

        System.out.println("Réservation créée avec succès pour la borne à l'emplacement: " + chosenStation.getLocation());
    }

    static void registerClient(Scanner scanner) {
        System.out.print("Entrez votre prénom: ");
        String firstName = scanner.nextLine();
        System.out.print("Entrez votre nom: ");
        String lastName = scanner.nextLine();
        System.out.print("Entrez votre adresse: ");
        String address = scanner.nextLine();
        System.out.print("Entrez votre numéro de mobile: ");
        String mobileNumber = scanner.nextLine();

        String email;
        while (true) {
            System.out.print("Entrez votre adresse mail: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("L'email doit être sous le format email@mail.com");
            }
        }

        System.out.print("Entrez votre numéro de carte de débit: ");
        String debitCardNumber = scanner.nextLine();
        System.out.print("Entrez votre/vos numéro(s) de plaque d'immatriculation (séparés par des virgules si plusieurs): ");
        String licensePlatesInput = scanner.nextLine();
        List<String> licensePlates = Arrays.asList(licensePlatesInput.split(","));

        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || mobileNumber.isEmpty() || debitCardNumber.isEmpty() || licensePlates.isEmpty()) {
            throw new IllegalArgumentException("Tous les champs doivent être remplis.");
        }

        Client client = new Client(firstName, lastName, address, mobileNumber, email, debitCardNumber, licensePlates);
        clientBD.save(client);

        System.out.println("Inscription réussie!");
    }

    static void handleLicensePlate(Scanner scanner) {
        System.out.print("Entrez le numéro de plaque d'immatriculation: ");
        String licensePlate = scanner.nextLine();
        Optional<Client> clientOpt = clientBD.findByLicensePlate(licensePlate);

        if (!clientOpt.isPresent()) {
            throw new IllegalArgumentException("Numéro de plaque non reconnu.");
        }

        System.out.println("Numéro de plaque reconnu.");
        Optional<Reservation> reservationOpt = reservationDAO.findByLicensePlate(licensePlate);

        if (reservationOpt.isPresent()) {
            System.out.println("Réservation trouvée pour ce véhicule.");
        } else {
            System.out.println("Pas de réservation trouvée pour ce véhicule.");
            System.out.print("Entrez la durée prévue de recharge en minutes: ");
            int duration = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (duration <= 0) {
                throw new IllegalArgumentException("La durée doit être un nombre positif.");
            }

            Client client = clientOpt.get();
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = startTime.plusMinutes(duration);

            Reservation reservation = new Reservation(licensePlate, startTime, endTime, client, false);
            reservationDAO.save(reservation);

            System.out.println("Réservation temporaire créée pour " + duration + " minutes.");
        }
    }

    static void handleReservationNumber(Scanner scanner) {
        System.out.print("Entrez le numéro de réservation: ");
        String reservationNumber = scanner.nextLine();
        Optional<Reservation> reservationOpt = reservationDAO.findByLicensePlate(reservationNumber);

        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Numéro de réservation non trouvé.");
        }

        System.out.println("Réservation trouvée.");
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex) && email.contains(".");
    }

    static void showAllClients() {
        List<Client> clients = clientBD.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé.");
        } else {
            System.out.println("Liste des clients:");
            for (Client client : clients) {
                System.out.println(client.getInfo());
            }
        }
    }

    static void showAllReservations() {
        List<Reservation> reservations = reservationDAO.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée.");
        } else {
            System.out.println("Liste des réservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation.getInfo());
            }
        }
    }
}
