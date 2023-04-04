package com.example.restaurant.repository;

import com.example.restaurant.domain.Table;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TableRepo implements Repo<Table>{
    private String url;
    private String username;

    private String password;

    public TableRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Table> findAll() {
        Set<Table> tables = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from tables");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("table_id");

                Table table = new Table(id);
                tables.add(table);
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public Table save (Table entity){return null;}


}
