package fr.ul.miage.borne;

import java.util.*;
import java.io.*;
import java.sql.*;


public class Menu {

    private static final ClientBD clientBD = new ClientBD();
    private static final ReservationBD reservationDAO = new ReservationBD();

    public static void main(String[] args) {
    	DbSetUp.main(new String[0]);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Inscription du client");
            System.out.println("2. Entrer le numéro de plaque d'immatriculation");
            System.out.println("3. Entrer le numéro de réservation");
            System.out.println("4. Afficher tous les clients");
            System.out.println("5. Afficher toutes les réservations");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    registerClient(scanner);
                    break;
                case 2:
                    handleLicensePlate(scanner);
                    break;
                case 3:
                    handleReservationNumber(scanner);
                    break;
                case 4:
                	showAllClients();
                    break;
                case 5:
                    showAllReservations();
                    break;
                case 6:
                    System.out.println("Au revoir!");
                    return;
                    
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        }
    }

    private static void registerClient(Scanner scanner) {
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

        Client client = new Client(firstName, lastName, address, mobileNumber, email, debitCardNumber, licensePlates);
        clientBD.save(client);

        System.out.println("Inscription réussie!");
    }

    private static void handleLicensePlate(Scanner scanner) {
        System.out.print("Entrez le numéro de plaque d'immatriculation: ");
        String licensePlate = scanner.nextLine();
        Optional<Client> clientOpt = clientBD.findByLicensePlate(licensePlate);

        if (clientOpt.isPresent()) {
            System.out.println("Numéro de plaque reconnu.");
            Optional<Reservation> reservationOpt = reservationDAO.findByLicensePlate(licensePlate);

            if (reservationOpt.isPresent()) {
                System.out.println("Réservation trouvée pour ce véhicule.");
            } else {
                System.out.println("Pas de réservation trouvée pour ce véhicule.");
                System.out.print("Entrez la durée prévue de recharge en minutes: ");
                int duration = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                Reservation reservation = new Reservation(licensePlate, duration);
                reservationDAO.save(reservation);

                System.out.println("Réservation temporaire créée pour " + duration + " minutes.");
            }
        } else {
            System.out.println("Numéro de plaque non reconnu.");
            System.out.print("Entrez votre numéro de mobile: ");
            String mobileNumber = scanner.nextLine();
            System.out.print("Entrez la durée prévue de recharge en minutes: ");
            int duration = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("Veuillez vous inscrire.");
        }
    }

    private static void handleReservationNumber(Scanner scanner) {
        System.out.print("Entrez le numéro de réservation: ");
        String reservationNumber = scanner.nextLine();
        Optional<Reservation> reservationOpt = reservationDAO.findByLicensePlate(reservationNumber);

        if (reservationOpt.isPresent()) {
            System.out.println("Réservation trouvée.");
        } else {
            System.out.println("Numéro de réservation non trouvé.");
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex) && email.contains(".");
    }
    
    private static void showAllClients() {
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
    private static void showAllReservations() {
        List<Reservation> reservations = reservationDAO.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation trouvée.");
        } else {
            System.out.println("Liste des réservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

}