/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package models;

import entities.Trip;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tegar
 */
public interface TripDAO {
    void addTrip(Trip trip) throws SQLException;
    Trip getTripById(String id) throws SQLException;
    List<Trip> getAllTrips() throws SQLException;
    void updateTrip(Trip trip) throws SQLException;
    void deleteTrip(String id) throws SQLException;
}