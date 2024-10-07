    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tegar
 */
public class SetupDB {
    
    public static void migrate() {
        
        // Create table for trip data
        String createTripTable = "CREATE TABLE IF NOT EXISTS trips ("
            + "id TEXT NOT NULL, "
            + "route TEXT NOT NULL, "
            + "schedule TEXT NOT NULL, "
            + "status TEXT NOT NULL, "
            + "ticket_amount INTEGER NOT NULL DEFAULT 0, "
            + "price REAL NOT NULL)";
            
            // Create table for ticket bookings
        String createBookingTable = "CREATE TABLE IF NOT EXISTS bookings ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "trip_id TEXT NOT NULL, "
            + "customer_name TEXT NOT NULL, "
            + "customer_contact TEXT NOT NULL, "
            + "booking_date TEXT NOT NULL, "
            + "buy_amount INTEGER NOT NULL DEFAULT 0, "
            + "FOREIGN KEY (trip_id) REFERENCES trips(id))";
        
        
        try (Connection conn = KoneksiDB.connect();
             Statement stmt = conn.createStatement()) {
            
            // Execute each CREATE TABLE statement
            stmt.executeUpdate(createTripTable);
            stmt.executeUpdate(createBookingTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }       
}
