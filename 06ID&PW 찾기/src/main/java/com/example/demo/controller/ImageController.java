package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/image")
public class ImageController {
    @GetMapping("/post")
    public void imagePost() {
        log.info("GET /image/post");
    }
}
