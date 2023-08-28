package com.shiyuan.bookstore.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shiyuan.bookstore.entity.Order;
import com.shiyuan.bookstore.service.OrderService;

@Controller
@RequestMapping("orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders-list";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order-form";
    }

    @GetMapping("/update")
    public String updateOrder(Model model, @RequestParam("orderId") int orderId) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order-form";
    }

    @PostMapping("/save")
    public String addOrder(@ModelAttribute("order") Order order) {
        orderService.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam("orderId") int orderId) {
        orderService.deleteById(orderId);
        return "redirect:/orders";
    }

}
