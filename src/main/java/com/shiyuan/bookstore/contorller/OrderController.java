package com.shiyuan.bookstore.contorller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shiyuan.bookstore.entity.Book;
import com.shiyuan.bookstore.entity.Order;
import com.shiyuan.bookstore.service.BookService;
import com.shiyuan.bookstore.service.OrderService;

@Controller
@RequestMapping("orders")
public class OrderController {
    private OrderService orderService;
    private BookService bookService;

    // @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/myorders")
    public String getMyOrders(Model model) {
        List<Order> orders = orderService.findMy();
        model.addAttribute("orders", orders);
        return "orders-list";
    }

    @GetMapping("/manage")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders-list";
    }

    @GetMapping("/add")
    public String addOrderForm(Model model, @RequestParam("bookId") int bookId) {
        Order order = new Order();
        Book book = bookService.findById(bookId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        order.setCustomername(authentication.getName());
        order.setBook_id(book.getId());
        order.setBook_title(book.getTitle());
        order.setBook_author(book.getAuthor());
        order.setBook_isbn(book.getIsbn());
        order.setNumber(1);

        model.addAttribute("order", order);
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
        // todo: auth

        orderService.save(order);
        return "redirect:/orders";
    }

    @GetMapping("/delete")
    public String deleteOrder(@RequestParam("orderId") int orderId) {
        // todo: auth

        orderService.deleteById(orderId);
        return "redirect:/orders";
    }

}
