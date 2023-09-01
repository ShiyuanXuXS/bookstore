package com.shiyuan.bookstore.contorller;

import java.util.HashMap;
import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shiyuan.bookstore.entity.User;
import com.shiyuan.bookstore.service.UserService;

@Controller
@RequestMapping("users")
public class UserController {

    private UserService userService;

    // @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("operate", "register");
        return "user-form";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        model.addAttribute("user", user);
        model.addAttribute("operate", "profile");
        return "user-form";
    }

    @PostMapping("/register/save")
    public String saveRegister(Model model, User user, @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {
        Map<String, Object> response = new HashMap<>();
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        if (newPassword == null || newPassword == "") {
            response.put("success", false);
            response.put("message", "Failed to register user: password empty.");
        } else if (!newPassword.equals(confirmPassword)) {
            response.put("success", false);
            response.put("message", "Failed to register user: Passwords do not match.");
        } else {
            String password = "{bcrypt}" + bc.encode(confirmPassword);
            user.setPassword(password);
            user.setEnabled(true);
            response = userService.registerCustomer(user);
        }
        model.addAttribute("operate", "register");
        model.addAttribute("response", response);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/profile/save")
    public String saveProfile(Model model, User user,
            @RequestParam(name = "passwordChanged", defaultValue = "false") String passwordChanged,
            @RequestParam(name = "originalPassword", defaultValue = "") String originalPassword,
            @RequestParam(name = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(name = "confirmPassword", defaultValue = "") String confirmPassword) {
        // save my profile
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        // not authenticated user
        if (!user.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return "redirect:/access-denied";
        }

        Map<String, Object> response = new HashMap<>();
        // change password
        boolean isPasswordChanged = passwordChanged.equals("true") ? true : false;
        if (isPasswordChanged) {
            if (!bc.matches(originalPassword, user.getPassword().replace("{bcrypt}", ""))) {
                response.put("success", false);
                response.put("message", "Failed to save profile: Original password does not match.");
            } else if (newPassword.equals("")) {
                response.put("success", false);
                response.put("message", "Failed to save profile: New passwords is empty.");
            } else if (!newPassword.equals(confirmPassword)) {
                response.put("success", false);
                response.put("message", "Failed to save profile: New passwords and Confirm password do not match.");
            } else {
                newPassword = "{bcrypt}" + bc.encode(confirmPassword);
                user.setPassword(newPassword);
                userService.saveMyUser(user);
                response.put("success", true);
                response.put("message", "Save profile successfully.");
            }
        } else {
            response.put("success", true);
            response.put("message", "Save profile successfully.");
            userService.saveMyUser(user);
        }

        model.addAttribute("operate", "profile");
        model.addAttribute("response", response);
        model.addAttribute("user", user);
        return "user-form";
    }
}
