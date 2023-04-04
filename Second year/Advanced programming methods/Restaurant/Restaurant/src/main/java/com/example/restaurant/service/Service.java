package com.example.restaurant.service;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderItem;
import com.example.restaurant.domain.Table;
import com.example.restaurant.repository.Repo;
import com.example.restaurant.utils.Observable;
import com.example.restaurant.utils.ObservableImplementat;
import com.example.restaurant.utils.Observer;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Service extends ObservableImplementat {

    private Repo<Table> tableRepo;
    private Repo<MenuItem> menuRepo;
    private Repo<Order> orderRepo;
    private Repo<OrderItem> orderItemRepo;


    public Service(Repo<Table> tableRepo,Repo<MenuItem> menuRepo,Repo<Order> orderRepo,Repo<OrderItem> orderItemRepo) {
        this.tableRepo = tableRepo;
        this.menuRepo=menuRepo;
        this.orderRepo=orderRepo;
        this.orderItemRepo=orderItemRepo;
    }

    public Iterable<Table> getTables()
    {
        return tableRepo.findAll();
    }

    public Iterable<MenuItem> getMenuItems(){return menuRepo.findAll();}

    public Iterable<MenuItem> getItemsByCategory(String category){
        Set<MenuItem> menu = new HashSet<>();
        for(MenuItem m: menuRepo.findAll())
        {
            if(Objects.equals(m.getCategory(), category))
                menu.add(m);
        }
        return menu;
    }

    public int calutateOrderId()
    {
        int id=0;
        for(Order o: orderRepo.findAll())
        {
            if(o.getId()>id)
                id=o.getId();
        }
        id++;
        return id;
    }

    public Iterable<Order> getOrders()
    {
        return orderRepo.findAll();
    }
    public Iterable<OrderItem> getOrderItems()
    {
        return orderItemRepo.findAll();
    }

    public void saveOrder(Order order)
    {
        orderRepo.save(order);
    }
    public void saveOrderItem(OrderItem orderItem)
    {
        orderItemRepo.save(orderItem);
    }

    public Iterable<OrderItem> findByOrderId(Integer id)
    {
        Set<OrderItem> items= new HashSet<>();
        for(OrderItem o: getOrderItems())
        {
            if(Objects.equals(o.getOrder(), id))
                items.add(o);
        }
        return items;
    }

    public MenuItem findById(Integer id)
    {
        for(MenuItem o: getMenuItems())
        {
            if(Objects.equals(o.getId(), id))
               return o;
        }
        return null;
    }


}
