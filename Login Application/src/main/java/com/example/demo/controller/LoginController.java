package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("User Role in login method: " + user.getRole());
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "welcome";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/welcome")
    public String showWelcomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "welcome";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/restricted")
    public String showRestrictedPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && "manager".equals(user.getRole())) {
            return "restricted";
        } else {
            model.addAttribute("error", "Access denied");
            return "welcome";
        }
    }

    @GetMapping(value = "/logout", produces = "text/html")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
