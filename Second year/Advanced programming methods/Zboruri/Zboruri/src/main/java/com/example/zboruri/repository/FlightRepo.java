package com.example.zboruri.repository;

import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Flight;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FlightRepo implements Repo<Flight>{
    private String url;
    private String username;

    private String password;

    public FlightRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Flight> findAll() {
        Set<Flight> flights = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from filghts");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long flightId = resultSet.getLong("fight_id");
                String from = resultSet.getString("flight_from");
                String to = resultSet.getString("flight_to");
                LocalDateTime departure = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime landing = resultSet.getTimestamp("landing_time").toLocalDateTime();
                Integer noSits =resultSet.getInt("no_sits");
                Flight flight= new Flight(flightId,from,to,departure,landing,noSits);
                flights.add(flight);
            }
            return flights;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public Flight save (Flight entity){return null;}

    @Override
    public Flight update (Flight entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");
        String sql = "update filghts set  no_sits=? where fight_id = ?";
        int row_count = 0;

        try(Connection connection = DriverManager.getConnection(url,username,password)) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,entity.getNoSits());
            ps.setLong(2,entity.getFightId());

            row_count = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(row_count > 0)
            return null;
        return entity;
    }
}
