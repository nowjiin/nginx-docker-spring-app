package com.nowgenie.dockerapp2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Controller
public class App2Controller {

    private final Random random = new Random();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Spring Boot App2");
        model.addAttribute("port", "8081");
        model.addAttribute("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return "index";
    }

    @PostMapping("/api/greeting")
    @ResponseBody
    public String apiGreeting(@RequestParam(defaultValue = "사용자") String name) {
        String[] greetings = {"안녕하세요", "반갑습니다", "환영합니다", "좋은 하루", "화이팅"};
        String greeting = greetings[random.nextInt(greetings.length)];
        return String.format("%s %s! App2에서 인사드립니다. 시간: %s",
                greeting,
                name,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    @GetMapping("/api/info")
    @ResponseBody
    public String getInfo() {
        return String.format("App2 정보 - 포트: 8081, 실행시간: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @PostMapping("/api/calculate")
    @ResponseBody
    public String calculate(@RequestParam(defaultValue = "10") int number) {
        int result = number * number;
        return String.format("입력: %d, 제곱: %d, 계산시간: %s",
                number,
                result,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK - App2";
    }
}