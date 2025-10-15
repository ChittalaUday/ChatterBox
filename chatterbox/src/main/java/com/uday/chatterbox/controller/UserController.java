package com.uday.chatterbox.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UserController {

    @GetMapping("/users")
    public String getMethodName() {
        return new String("Hello Users");
    }

}
