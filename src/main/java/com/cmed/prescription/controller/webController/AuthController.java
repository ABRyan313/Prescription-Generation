package com.cmed.prescription.controller.webController;

import com.cmed.prescription.model.dto.auth.AuthRequest;
import com.cmed.prescription.security.AuthUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthUserDetailsService userService; // your service to handle registration

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("loginError", "Invalid username or password. Please try again.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been successfully logged out.");
        }
        return "auth/login"; // login.html template
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new AuthRequest(" "," ")); // DTO for registration
        return "auth/register"; // register.html template
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") AuthRequest request,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        if (!userService.registerUser(request)) {
            model.addAttribute("registrationError", "Username already exists.");
            return "auth/register";
        }

        return "redirect:/auth/login?registered";
    }
}