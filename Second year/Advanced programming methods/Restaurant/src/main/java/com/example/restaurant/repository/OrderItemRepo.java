package com.example.restaurant.repository;

import com.example.restaurant.domain.*;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderItemRepo implements Repo<OrderItem>{
    private String url;
    private String username;

    private String password;

    public OrderItemRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<OrderItem> findAll() {
        Set<OrderItem> orders = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from order_items");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer order = resultSet.getInt("order_id");
                Integer item = resultSet.getInt("item_id");

                OrderItem o = new OrderItem(order,item);
                orders.add(o);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public OrderItem save (OrderItem entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");

        String sql = "insert into order_items (order_id, item_id) values (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,entity.getOrder());
            ps.setInt(2, entity.getItem());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
