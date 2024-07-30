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
        try {
            Contact contact = contactRepository.getContactById(cId);
            User user = userRepository.findUserByUsername(username);
            if(!contact.getUser().getEmail().equals(username)) {
                return new ResponseEntity<>("Can not delete contact", HttpStatus.FORBIDDEN);
            }
            List<Contact> contacts = user.getContacts();
            contacts.remove(contact);
            userRepository.save(user);
            contactRepository.save(contact);
            return new ResponseEntity<>("Contact deleted successfully", HttpStatus.OK);
        }catch(Exception exception) {
            return new ResponseEntity<>("Failed to delete contact", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> updateContact(String username, Contact contact) {
        try{

            Contact dbContact = contactRepository.getContactById(contact.getcId());
            if(!dbContact.getUser().getEmail().equals(username)) {
                return new ResponseEntity<>("Can not update contact", HttpStatus.FORBIDDEN);
            }
            dbContact.setName(contact.getName());
            dbContact.setSecondName(contact.getSecondName());
            dbContact.setDescription(contact.getDescription());
            dbContact.setEmail(contact.getEmail());
            dbContact.setWork(contact.getWork());
            dbContact.setPhone(contact.getPhone());
            dbContact.setImage(contact.getImage());
            contactRepository.save(dbContact);

            return new ResponseEntity<>("Contact updated successfully", HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to update contact", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
