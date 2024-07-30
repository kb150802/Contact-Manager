package com.app.ContactManager.service;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.model.User;
import com.app.ContactManager.repository.ContactRepository;
import com.app.ContactManager.repository.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserRepository userRepository;
    public ResponseEntity<?> deleteContact(String username, Integer cId) {
        Contact contact = contactRepository.getContactById(cId);
        User user = userRepository.findUserByUsername(username);
        List<Contact> contacts = user.getContacts();
        contacts.remove(contact);
        userRepository.save(user);
        contactRepository.save(contact);
        return new ResponseEntity<>("In delete", HttpStatus.OK);
    }
}
