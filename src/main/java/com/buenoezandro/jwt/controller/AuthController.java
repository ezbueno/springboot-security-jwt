package com.buenoezandro.jwt.controller;

import com.buenoezandro.jwt.encrypt.PasswordEncoderEncrypt;
import com.buenoezandro.jwt.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/")
public class AuthController {
    private final PasswordEncoderEncrypt passwordEncoderEncrypt;

    @GetMapping(path = "login")
    public void login(@RequestBody UserModel user) {
        user.setPassword(this.passwordEncoderEncrypt.bCryptPasswordEncoder().encode(user.getPassword()));
    }
}
