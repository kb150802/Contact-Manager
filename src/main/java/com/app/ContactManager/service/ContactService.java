package com.app.ContactManager.service;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.model.User;
import com.app.ContactManager.repository.ContactRepository;
import com.app.ContactManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileStorageService fileStorageService;

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
            contactRepository.delete(contact);
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

    public ResponseEntity<?> addImage(String username,Integer cId, MultipartFile file) {
        try{
            User user = userRepository.findUserByUsername(username);
            if(user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            Contact contact = contactRepository.getContactById(cId);
            if(contact == null) {
                return new ResponseEntity<>("Contact not found", HttpStatus.BAD_REQUEST);
            }

            if(!contact.getUser().getEmail().equals(username)) {
                return new ResponseEntity<>("Can not upload image", HttpStatus.FORBIDDEN);
            }
            String imageUrl = fileStorageService.storeFile(file);
            contact.setImage(file.getOriginalFilename());
            contactRepository.save(contact);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to upload Image", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<?> getImage(String username, Integer cId) {
        try{
            Contact contact = contactRepository.getContactById(cId);
            if(contact == null) {
                return new ResponseEntity<>("Contact not found",HttpStatus.BAD_REQUEST);
            }
            if(!contact.getUser().getEmail().equals(username)) {
                return new ResponseEntity<>("Can not get image", HttpStatus.FORBIDDEN);
            }

            Resource resource = fileStorageService.getFile(contact.getImage());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource.getContentAsByteArray());
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to fetch image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
