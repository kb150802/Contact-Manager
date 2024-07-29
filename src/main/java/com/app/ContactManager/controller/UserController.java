package com.app.ContactManager.controller;

import com.app.ContactManager.model.User;
import com.app.ContactManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserInfo(userDetails.getUsername());
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User newUserDetails) {
        return userService.updateUserInfo(userDetails.getUsername(), newUserDetails);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.delete(userDetails.getUsername());
    }
}
