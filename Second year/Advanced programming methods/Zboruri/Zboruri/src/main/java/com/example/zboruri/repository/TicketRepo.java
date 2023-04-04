package com.example.zboruri.repository;

import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Ticket;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TicketRepo implements Repo<Ticket>{
    private String url;
    private String username;

    private String password;

    public TicketRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Ticket> findAll() {
        Set<Ticket> tickets = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from tickets");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                Long flight = resultSet.getLong("flight");
                LocalDateTime purchase=resultSet.getTimestamp("purchase_time").toLocalDateTime();
                Ticket ticket = new Ticket(username,flight,purchase);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Ticket save (Ticket entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");

        String sql = "insert into tickets (username, flight, purchase_time) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,entity.getUsername());
            ps.setLong(2, entity.getFlightId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getPurchaseTime()));


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Ticket update (Ticket entity){return null;}
}
