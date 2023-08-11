package com.github.adrian83.mordeczki.auth.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/view/auth")
    public String showIndex() {
        return "auth";
    }
    
    
}
