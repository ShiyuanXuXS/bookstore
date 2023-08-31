package com.shiyuan.bookstore.contorller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public OrderController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/myorders")
    public String getMyOrders(Model model) {
        List<Order> orders = orderService.findMy();
        model.addAttribute("orders", orders);
        model.addAttribute("option", "myorders");
        return "orders-list";
    }

    @GetMapping("/manage")
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        model.addAttribute("option", "manage");
        return "orders-list";
    }

    @GetMapping("/myorders/add")
    public String addOrderForm(Model model, @RequestParam("bookId") int bookId) {
        Order order = new Order();
        Book book = bookService.findById(bookId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        order.setCustomername(authentication.getName());
        order.setBook_id(book.getId());
        order.setBook_title(book.getTitle());
        order.setBook_author(book.getAuthor());
        order.setBook_isbn(book.getIsbn());
        order.setBook_price(book.getPrice());
        order.setNumber(1);

        model.addAttribute("order", order);
        model.addAttribute("operate", "add");
        return "order-form";
    }

    @GetMapping("/myorders/modify")
    public String modifyOrder(Model model, @RequestParam("orderId") int orderId) {
        // customer modify his own order
        Order order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "No such an order!");
            return "show-error";
        }
        if (order.getProcessedby() != null) {
            model.addAttribute("error", "Cannot modify an order in process!");
            return "show-error";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(order.getCustomername())) {
            return "redirect:/access-denied";
        }
        model.addAttribute("operate", "modify");
        model.addAttribute("order", order);
        return "order-form";
    }

    @GetMapping("/manage/process")
    public String processOrder(Model model, @RequestParam("orderId") int orderId) {
        // staff process an order
        Order order = orderService.findById(orderId);
        if (order == null) {
            model.addAttribute("error", "No such an order!");
            return "show-error";
        }
        model.addAttribute("operate", "process");
        model.addAttribute("order", order);
        return "order-form";
    }

    @PostMapping("/myorders/save")
    public String saveMyOrder(Model model, @ModelAttribute("order") Order order,
            @ModelAttribute("operate") String operate) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        switch (operate) {
            case "add":
                // customer add an order
                Book book = bookService.findById(order.getBook_id());
                if (book == null) {
                    model.addAttribute("error", "No such a book!");
                    return "show-error";
                }
                order.setCustomername(authentication.getName());
                order.setBook_title(book.getTitle());
                order.setBook_author(book.getAuthor());
                order.setBook_isbn(book.getIsbn());
                BigDecimal price = book.getPrice();
                order.setBook_price(price);
                order.setTotal(price.multiply(BigDecimal.valueOf(order.getNumber())));
                order.setStatus("ordered");
                order.setCreatedat(LocalDateTime.now());
                order.setUpdatedat(LocalDateTime.now());
                orderService.save(order);
                return "redirect:/orders/myorders";

            case "modify":
                // customer modify an order
                Order originalOrder = orderService.findById(order.getId());
                if (originalOrder == null) {
                    model.addAttribute("error", "No such an order!");
                    return "show-error";
                }
                if (!(originalOrder.getCustomername().equals(authentication.getName()))) {
                    return "redirect:/access-denied";
                }
                if (originalOrder.getProcessedby() != null) {
                    model.addAttribute("error", "Cannot modify an order in process!");
                    return "show-error";
                }
                int number = order.getNumber();
                originalOrder.setNumber(number);
                originalOrder.setTotal(originalOrder.getBook_price().multiply(BigDecimal.valueOf(number)));
                originalOrder.setConsignee(order.getConsignee());
                originalOrder.setAddress(order.getAddress());
                originalOrder.setPhone(order.getPhone());
                originalOrder.setUpdatedat(LocalDateTime.now());
                orderService.save(originalOrder);
                return "redirect:/orders/myorders";

            default:
        }
        model.addAttribute("error", "Unknown error!");
        return "show-error";
    }

    @PostMapping("/manage/save")
    public String saveOrder(Model model, @ModelAttribute("order") Order order,
            @ModelAttribute("operate") String operate) {
        // todo add processedby and updatedat
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        order.setProcessedby(authentication.getName());
        order.setUpdatedat(LocalDateTime.now());

        orderService.save(order);
        return "redirect:/orders/manage";

    }

    @GetMapping("/myorders/delete")
    public String deleteMyOrder(Model model, @RequestParam("orderId") int orderId) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return "redirect:/orders/myorders";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(order.getCustomername().equals(authentication.getName()))) {
            return "redirect:/access-denied";
        }
        if (order.getProcessedby() != null) {
            model.addAttribute("error", "Cannot delete an order in process!");
            return "show-error";
        }
        orderService.deleteById(orderId);
        return "redirect:/orders/myorders";
    }

    @GetMapping("/manage/delete")
    public String deleteOrder(@RequestParam("orderId") int orderId) {
        orderService.deleteById(orderId);
        return "redirect:/orders/manage";
    }
}
