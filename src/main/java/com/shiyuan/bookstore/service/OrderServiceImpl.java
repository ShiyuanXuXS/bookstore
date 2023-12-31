package com.shiyuan.bookstore.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiyuan.bookstore.entity.Order;
import com.shiyuan.bookstore.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    // @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAllByOrderByUpdatedatDesc();
    }

    @Override
    public List<Order> findMy() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String customerName = authentication.getName();
        return orderRepository.findByCustomernameOrderByUpdatedatDesc(customerName);
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }

}
