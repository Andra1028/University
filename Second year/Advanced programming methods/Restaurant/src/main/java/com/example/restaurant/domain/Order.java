package com.example.restaurant.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.example.restaurant.domain.OrderStatus.*;

public class Order {
    private Integer id;
    private Integer table;
    private LocalDateTime date;
    private OrderStatus staus;

    public Order(Integer id, Integer table, LocalDateTime date, OrderStatus staus) {
        this.id = id;
        this.table = table;
        this.date = date;
        this.staus = staus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStaus() {
        return staus;
    }

    public void setStaus(OrderStatus staus) {
        this.staus = staus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(table, order.table) && Objects.equals(date, order.date) && staus == order.staus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, table, date, staus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", table=" + table +
                ", date=" + date +
                ", staus=" + staus +
                '}';
    }

    public static OrderStatus stringToType(String type){
        if(type.equals("PREPARING"))return PREPARING;
        if(type.equals("PLACED"))return PLACED;
        if(type.equals("SERVED"))return SERVED;
        return null;
    }

    public static String typeToString (OrderStatus ord)
    {
        if(ord.equals(PREPARING)) return "PREPARING";
        if(ord.equals(PLACED)) return "PLACED";
        if(ord.equals(SERVED)) return "SERVED";
        return null;
    }
}
