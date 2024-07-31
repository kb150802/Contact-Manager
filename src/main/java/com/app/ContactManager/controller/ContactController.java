package com.app.ContactManager.controller;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.service.ContactService;
import com.app.ContactManager.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;
    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<?> deleteContact(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer cId) {
        return contactService.deleteContact(userDetails.getUsername(), cId);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateContact(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Contact contact) {
        return contactService.updateContact(userDetails.getUsername(), contact);
    }
    @PostMapping("/image/{cId}")
    public ResponseEntity<?> addImage(@AuthenticationPrincipal UserDetails userDetails, @RequestPart(value = "file") MultipartFile file, @PathVariable Integer cId) {
        return contactService.addImage(userDetails.getUsername(), cId, file);
    }
    @GetMapping("/image/{cId}")
    public ResponseEntity<?> getImage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer cId) {
        return contactService.getImage(userDetails.getUsername(), cId);
    }

}
