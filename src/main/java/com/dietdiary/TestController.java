package com.dietdiary;

// 参考：https://qiita.com/t-yama-3/items/a538d47b8f0a27639d23

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
    @GetMapping
    public String index () {
        return "index";
    }

    @GetMapping("/login")
    public String login () {
        return "login";
    }
}