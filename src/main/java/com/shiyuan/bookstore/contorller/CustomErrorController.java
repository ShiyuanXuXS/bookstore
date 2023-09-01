package com.shiyuan.bookstore.contorller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {

        Object errorAttribute = request.getAttribute("javax.servlet.error.exception");

        if (errorAttribute instanceof Exception) {
            Exception exception = (Exception) errorAttribute;
            String errorMessage = exception.getMessage();
            model.addAttribute("errorText", errorMessage);
        } else {
            model.addAttribute("errorText", "An error occurred.");
        }

        return "show-error";
    }

}
