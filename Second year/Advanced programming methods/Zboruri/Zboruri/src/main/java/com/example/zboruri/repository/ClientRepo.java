package com.example.zboruri.repository;

import com.example.zboruri.domain.Client;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientRepo implements Repo<Client>{
    private String url;
    private String username;

    private String password;

    public ClientRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Client> findAll() {
        Set<Client> clients = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from clients");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String clientId = resultSet.getString("username");
                String clientName = resultSet.getString("client_name");

                Client client = new Client(clientId,clientName);
                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client save (Client entity){return null;}

    @Override
    public Client update (Client entity){return null;}
}
