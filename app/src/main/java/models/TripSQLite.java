/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import entities.Trip;
import helpers.KoneksiDB;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author tegar
 */
public class TripSQLite implements TripDAO {

    @Override
    public void addTrip(Trip trip) throws SQLException {
        trip.setStatus(determineStatus(trip.getTicketAmount()));

        String sql = "INSERT INTO trips (id, route, schedule, status, ticket_amount, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trip.getId());
            pstmt.setString(2, trip.getRoute());
            pstmt.setString(3, trip.getSchedule());
            pstmt.setString(4, trip.getStatus());
            pstmt.setInt(5, trip.getTicketAmount());
            pstmt.setDouble(6, trip.getPrice());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Trip getTripById(String id) throws SQLException {
        String sql = "SELECT * FROM trips WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Trip(
                        rs.getString("id"),
                        rs.getString("route"),
                        rs.getString("schedule"),
                        rs.getString("status"),
                        rs.getInt("ticket_amount"),
                        rs.getDouble("price")
                );
            }
        }
        return null;
    }

    @Override
    public List<Trip> getAllTrips() throws SQLException {
        List<Trip> trips = new ArrayList<>();
        String sql = "SELECT * FROM trips";
        try (Connection conn = KoneksiDB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                trips.add(new Trip(
                        rs.getString("id"),
                        rs.getString("route"),
                        rs.getString("schedule"),
                        rs.getString("status"),
                        rs.getInt("ticket_amount"),
                        rs.getDouble("price")
                ));
            }
        }
        return trips;
    }

    @Override
    public void updateTrip(Trip trip) throws SQLException {
        trip.setStatus(determineStatus(trip.getTicketAmount()));

        String sql = "UPDATE trips SET route = ?, schedule = ?, status = ?, ticket_amount = ?, price = ? WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, trip.getRoute());
            pstmt.setString(2, trip.getSchedule());
            pstmt.setString(3, trip.getStatus());
            pstmt.setInt(4, trip.getTicketAmount());
            pstmt.setDouble(5, trip.getPrice());
            pstmt.setString(6, trip.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteTrip(String id) throws SQLException {
        String sql = "DELETE FROM trips WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        }
    }

    private String determineStatus(int ticketAmount) {
        if (ticketAmount == 0) {
            return "Kosong";
        } else if (ticketAmount <= 20) {
            return "Hampir Habis";
        } else {
            return "Tersedia";
        }
    }

}