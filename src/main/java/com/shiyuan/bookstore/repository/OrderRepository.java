package com.shiyuan.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiyuan.bookstore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findAllByOrderByUpdatedatDesc();

    public List<Order> findByCustomernameOrderByUpdatedatDesc(String customerName);
}
