package com.example.restaurant.domain;

import java.util.Objects;

public class OrderItem {
    private Integer order;
    private Integer item;

    public OrderItem(Integer order, Integer item) {
        this.order = order;
        this.item = item;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(order, orderItem.order) && Objects.equals(item, orderItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, item);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", item=" + item +
                '}';
    }


}
