package com.nowgenie.dockerapp1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class App1Controller {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Spring Boot App1");
        model.addAttribute("port", "8080");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return "index";
    }

    @PostMapping("/api/hello")
    @ResponseBody
    public String apiHello(@RequestParam(defaultValue = "World") String name) {
        return String.format("Hello %s from App1! Time: %s",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    @GetMapping("/api/status")
    @ResponseBody
    public String getStatus() {
        return "App1 is running on port 8080";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK - App1";
    }
}