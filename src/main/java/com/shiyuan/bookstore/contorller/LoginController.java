package com.shiyuan.bookstore.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/showLogin")
    public String showLogin() {
        return "login";
    }
}
