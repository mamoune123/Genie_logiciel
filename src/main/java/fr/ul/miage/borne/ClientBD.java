package fr.ul.miage.borne;

import java.sql.*;
import java.util.*;
public class ClientBD {
	  public void save(Client client) {
	        String sql = "INSERT INTO clients (first_name, last_name, address, mobile_number, email, debit_card_number) VALUES (?, ?, ?, ?, ?, ?)";

	        try (Connection conn = Db.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	            pstmt.setString(1, client.getFirstName());
	            pstmt.setString(2, client.getLastName());
	            pstmt.setString(3, client.getAddress());
	            pstmt.setString(4, client.getMobileNumber());
	            pstmt.setString(5, client.getEmail());
	            pstmt.setString(6, client.getDebitCardNumber());

	            int affectedRows = pstmt.executeUpdate();

	            if (affectedRows > 0) {
	                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        client.setId(generatedKeys.getLong(1));
	                    }
	                }
	            }

	            saveLicensePlates(client);

	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	    }

	    private void saveLicensePlates(Client client) throws SQLException {
	        String sql = "INSERT INTO license_plates (client_id, license_plate) VALUES (?, ?)";

	        try (Connection conn = Db.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            for (String plate : client.getLicensePlates()) {
	                pstmt.setLong(1, client.getId());
	                pstmt.setString(2, plate);
	                pstmt.addBatch();
	            }

	            pstmt.executeBatch();
	        }
	    }

	    public Optional<Client> findByEmail(String email) {
	        String sql = "SELECT * FROM clients WHERE email = ?";

	        try (Connection conn = Db.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, email);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                Client client = new Client();
	                client.setId(rs.getLong("id"));
	                client.setFirstName(rs.getString("first_name"));
	                client.setLastName(rs.getString("last_name"));
	                client.setAddress(rs.getString("address"));
	                client.setMobileNumber(rs.getString("mobile_number"));
	                client.setEmail(rs.getString("email"));
	                client.setDebitCardNumber(rs.getString("debit_card_number"));
	                client.setLicensePlates(findLicensePlates(client.getId()));

	                return Optional.of(client);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return Optional.empty();
	    }

	    private List<String> findLicensePlates(Long clientId) {
	        String sql = "SELECT license_plate FROM license_plates WHERE client_id = ?";
	        List<String> plates = new ArrayList<>();

	        try (Connection conn = Db.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setLong(1, clientId);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                plates.add(rs.getString("license_plate"));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }

	        return plates;
	    }

	    public Optional<Client> findByLicensePlate(String licensePlate) {
	        String sql = "SELECT c.* FROM clients c JOIN license_plates lp ON c.id = lp.client_id WHERE lp.license_plate = ?";

	        try (Connection conn = Db.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, licensePlate);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                Client client = new Client();
	                client.setId(rs.getLong("id"));
	                client.setFirstName(rs.getString("first_name"));
	                client.setLastName(rs.getString("last_name"));
	                client.setAddress(rs.getString("address"));
	                client.setMobileNumber(rs.getString("mobile_number"));
	                client.setEmail(rs.getString("email"));
	                client.setDebitCardNumber(rs.getString("debit_card_number"));
	                client.setLicensePlates(findLicensePlates(client.getId()));

	                return Optional.of(client);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return Optional.empty();
	    }
	    public List<Client> getAllClients() {
	        List<Client> clients = new ArrayList<>();
	        String sql = "SELECT * FROM clients";

	        try (Connection conn = Db.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                Client client = new Client();
	                client.setId(rs.getLong("id"));
	                client.setFirstName(rs.getString("first_name"));
	                client.setLastName(rs.getString("last_name"));
	                client.setAddress(rs.getString("address"));
	                client.setMobileNumber(rs.getString("mobile_number"));
	                client.setEmail(rs.getString("email"));
	                client.setDebitCardNumber(rs.getString("debit_card_number"));
	                client.setLicensePlates(findLicensePlates(client.getId()));
	                clients.add(client);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return clients;
	    }
}
