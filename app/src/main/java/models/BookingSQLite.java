/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import entities.Booking;
import entities.Trip;
import helpers.KoneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tegar
 */
public class BookingSQLite implements BookingDAO {
     @Override
    public void addBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO bookings (trip_id, customer_name, customer_contact, booking_date, buy_amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getTripId());
            pstmt.setString(2, booking.getCustomerName());
            pstmt.setString(3, booking.getCustomerContact());
            pstmt.setString(4, booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setInt(5, booking.getBuyAmount());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Booking getBookingById(int id) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking(
                        rs.getString("trip_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_contact"),
                        LocalDateTime.parse(rs.getString("booking_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getInt("buy_amount")
                );
                booking.setId(rs.getInt("id"));
                return booking;
            }
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = KoneksiDB.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getString("trip_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_contact"),
                        LocalDateTime.parse(rs.getString("booking_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getInt("buy_amount")
                );
                booking.setId(rs.getInt("id"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public List<Booking> getBookingsByTripId(String tripId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE trip_id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tripId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getString("trip_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_contact"),
                        LocalDateTime.parse(rs.getString("booking_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        rs.getInt("buy_amount")
                );
                booking.setId(rs.getInt("id"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public void updateBooking(Booking booking) throws SQLException {
        String sql = "UPDATE bookings SET trip_id = ?, customer_name = ?, customer_contact = ?, booking_date = ?, buy_amount = ? WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getTripId());
            pstmt.setString(2, booking.getCustomerName());
            pstmt.setString(3, booking.getCustomerContact());
            pstmt.setString(4, booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.setInt(5, booking.getBuyAmount());
            pstmt.setInt(6, booking.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteBooking(int id) throws SQLException {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = KoneksiDB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
