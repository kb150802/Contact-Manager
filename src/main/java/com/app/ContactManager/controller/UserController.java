package com.app.ContactManager.controller;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.model.User;
import com.app.ContactManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserInfo(userDetails.getUsername());
    }
    @GetMapping("/contacts")
    public ResponseEntity<?> getUserContacts(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserContacts(userDetails.getUsername());
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody User newUserDetails) {
        return userService.updateUserInfo(userDetails.getUsername(), newUserDetails);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.delete(userDetails.getUsername());
    }
    @PostMapping("/addContact")
    public ResponseEntity<?> addContact(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Contact contact) {
        return userService.addContact(userDetails.getUsername(), contact);
    }
    @PostMapping("/image")
    public ResponseEntity<?> addImage(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(value = "file")MultipartFile file) {
        return userService.addImage(userDetails.getUsername(), file);
    }
    @GetMapping("/image")
    public ResponseEntity<?> getImage(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getImage(userDetails.getUsername());
    }
}
