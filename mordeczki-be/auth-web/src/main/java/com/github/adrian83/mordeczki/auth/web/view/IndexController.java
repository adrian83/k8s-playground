package com.github.adrian83.mordeczki.auth.web.view;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("view")
public class IndexController {

    
    @GetMapping("/index")
    public String showUserList() {
        return "index";
    }
    
}
