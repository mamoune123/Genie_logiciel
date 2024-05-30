// fr/ul/miage/borne/service/ClientService.java
package fr.ul.miage.borne.service;

import fr.ul.miage.borne.dao.ClientBD;
import fr.ul.miage.borne.model.Client;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientBD clientBD = new ClientBD();

    public void registerClient(Client client) {
        clientBD.save(client);
    }

    public Optional<Client> findClientByEmail(String email) {
        return clientBD.findByEmail(email);
    }

    public Optional<Client> findClientByLicensePlate(String licensePlate) {
        return clientBD.findByLicensePlate(licensePlate);
    }

    public List<Client> getAllClients() {
        return clientBD.getAllClients();
    }
}