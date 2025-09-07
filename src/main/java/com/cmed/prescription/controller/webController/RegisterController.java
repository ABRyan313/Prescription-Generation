package com.cmed.prescription.controller.webController;


import com.cmed.prescription.persistence.entity.UserEntity;
import com.cmed.prescription.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userEntity") UserEntity userEntity) {
        authService.register(userEntity); // save with encoded password
        return "redirect:/login?registered";
    }
}

