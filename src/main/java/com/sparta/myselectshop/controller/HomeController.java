package com.sparta.myselectshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")            //슬래쉬로 요청 들어가면 인덱스.html 파일 반환함
    public String home() {
        return "index";
    }
}

