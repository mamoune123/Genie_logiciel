// fr/ul/miage/borne/controller/ClientController.java
package fr.ul.miage.borne.controller;

import fr.ul.miage.borne.model.Client;
import fr.ul.miage.borne.service.ClientService;

import java.util.List;
import java.util.Optional;

public class ClientController {
    private final ClientService clientService = new ClientService();

    public String registerClient(Client client) {
        Optional<Client> existingClient = clientService.findClientByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            return "Un client avec cet email existe déjà.";
        }
        clientService.registerClient(client);
        return "Inscription réussie!";
    }

    public Optional<Client> findClientByEmail(String email) {
        return clientService.findClientByEmail(email);
    }

    public Optional<Client> findClientByLicensePlate(String licensePlate) {
        return clientService.findClientByLicensePlate(licensePlate);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}