package com.example.r2dbc.controller;

import com.example.r2dbc.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import java.time.Duration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserViewController {
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        IReactiveDataDriverContextVariable userDataDrivenMode = new ReactiveDataDriverContextVariable(userService.findAll(), 1);

        model.addAttribute("users", userDataDrivenMode);

        return "index";
    }

    @GetMapping("/index2")
    public String index2(Model model) {
        //IReactiveDataDriverContextVariable userDataDrivenMode = new ReactiveDataDriverContextVariable(userService.findAll().delayElements(Duration.ofSeconds(1)), 1);

        model.addAttribute("users", userService.findAll());

        return "index2";
    }

}
