/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models;

import entities.Booking;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tegar
 */
import java.util.List;

public interface BookingDAO {
    void addBooking(Booking booking) throws SQLException;
    Booking getBookingById(int id) throws SQLException;
    List<Booking> getAllBookings() throws SQLException;
    List<Booking> getBookingsByTripId(String tripId) throws SQLException;
    void updateBooking(Booking booking) throws SQLException;
    void deleteBooking(int id) throws SQLException;
}
