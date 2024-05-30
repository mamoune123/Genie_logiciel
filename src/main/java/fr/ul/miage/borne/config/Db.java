// fr/ul/miage/borne/config/Db.java
package fr.ul.miage.borne.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final String URL = "jdbc:h2:~/chargestation;AUTO_SERVER=TRUE";
    private static final String USER = "Postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}