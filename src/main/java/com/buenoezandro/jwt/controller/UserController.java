package com.buenoezandro.jwt.controller;

import com.buenoezandro.jwt.model.UserModel;
import com.buenoezandro.jwt.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;

    @GetMapping(path = "all-users")
    public List<UserModel> listAllUsers() {
        return this.userDetailsService.listUsers();
    }
}
