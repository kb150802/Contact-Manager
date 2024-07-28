package com.app.ContactManager.controller;

import com.app.ContactManager.model.User;
import com.app.ContactManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/user/add")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "Saved";
    }
}
