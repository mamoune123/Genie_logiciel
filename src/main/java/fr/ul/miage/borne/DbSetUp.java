package fr.ul.miage.borne;

import java.io.*;
import java.sql.*;

public class DbSetUp {
	 public static void main(String[] args) {
	        try (Connection conn = Db.getConnection();
	             Statement stmt = conn.createStatement()) {

	            // Lire le script SQL à partir des ressources
	            InputStream inputStream = DbSetUp.class.getClassLoader().getResourceAsStream("schema.sql");
	            if (inputStream == null) {
	                throw new IllegalArgumentException("schema.sql not found in resources");
	            }

	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            StringBuilder sql = new StringBuilder();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                sql.append(line).append("\n");
	            }

	            // Exécuter le script SQL
	            stmt.execute(sql.toString());
	            
	            String insertSql = "MERGE INTO charge_stations (location, status) KEY (location) VALUES" +
	                    "('Station 1', 'available')," +
	                    "('Station 2', 'available')," +
	                    "('Station 3', 'available')," +
	                    "('Station 4', 'available')," +
	                    "('Station 5', 'available');";

	            stmt.execute(insertSql);

	            System.out.println("Tables créées avec succès et données insérées");

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
