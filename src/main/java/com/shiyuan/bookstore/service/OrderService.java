package com.shiyuan.bookstore.service;

import java.util.List;

import com.shiyuan.bookstore.entity.Order;

public interface OrderService {
    List<Order> findAll();

    List<Order> findMy();

    Order findById(int id);

    void save(Order order);

    void deleteById(int id);
}
