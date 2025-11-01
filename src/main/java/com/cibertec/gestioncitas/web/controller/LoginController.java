package com.cibertec.gestioncitas.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String handleLoginPage(@RequestParam(value = "logout", required = false) String logout) {
        if (logout != null) {
            return "redirect:/?logout=success";
        }
        return "redirect:/";
    }
}
