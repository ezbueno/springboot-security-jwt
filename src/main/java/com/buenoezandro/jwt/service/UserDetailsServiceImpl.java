package com.buenoezandro.jwt.service;

import com.buenoezandro.jwt.encrypt.PasswordEncoderEncrypt;
import com.buenoezandro.jwt.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoderEncrypt passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.findUser(username);
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

    private UserModel findUser(String username) {
        var user = new UserModel();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.bCryptPasswordEncoder().encode("nimda"));
        return user;
    }

    public List<UserModel> listUsers() {
        ArrayList<UserModel> list = new ArrayList<>();
        list.add((this.findUser("admin")));
        return list;
    }
}
