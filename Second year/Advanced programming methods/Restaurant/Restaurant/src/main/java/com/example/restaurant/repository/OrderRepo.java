package com.example.restaurant.repository;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderStatus;
import com.example.restaurant.domain.Table;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderRepo implements Repo<Order>{
    private String url;
    private String username;

    private String password;

    public OrderRepo(String url, String username,String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Iterable<Order> findAll() {
        Set<Order> orders = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from orders");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("order_id");
                Integer table = resultSet.getInt("table_id");
                LocalDateTime date = resultSet.getTimestamp("date_order").toLocalDateTime();
                OrderStatus status = Order.stringToType(resultSet.getString("status"));

                Order o = new Order(id,table,date,status);
                orders.add(o);
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order save (Order entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity must not be null");

        String sql = "insert into orders (order_id, table_id, date_order, status) values (?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,entity.getId());
            ps.setInt(2, entity.getTable());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getDate()));
            ps.setString(4, Order.typeToString(entity.getStaus()));


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
