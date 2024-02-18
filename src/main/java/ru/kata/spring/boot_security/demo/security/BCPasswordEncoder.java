package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCPasswordEncoder {


    public PasswordEncoder PaspasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
