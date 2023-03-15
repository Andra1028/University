package com.example.restaurant.repository;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Table;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class MenuItemRepo implements Repo<MenuItem>{
    private String url;
    private String username;

    private String password;

    public MenuItemRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<MenuItem> findAll() {
        Set<MenuItem> menus = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from menu_items");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("menu_id");
               String category = resultSet.getString("category");
                String item = resultSet.getString("item");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");

                MenuItem menuItem = new MenuItem(id,category,item,price,currency);
               menus.add(menuItem);
            }
            return menus;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menus;
    }

    @Override
    public MenuItem save (MenuItem entity){return null;}



}
