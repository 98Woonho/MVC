package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
        log.info("GET /");
        return "index";
    }

    @GetMapping("/login")
    public void login(){
        log.info("GET /login...");
    }

    @GetMapping("/template")
    public void template() {
        log.info("GET /template...");
    }
}
