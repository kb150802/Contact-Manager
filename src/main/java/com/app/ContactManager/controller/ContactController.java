package com.app.ContactManager.controller;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;
    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<?> deleteContact(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer cId) {
        return contactService.deleteContact(userDetails.getUsername(), cId);
    }
}
