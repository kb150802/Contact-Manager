package com.app.ContactManager.service;

import com.app.ContactManager.model.Contact;
import com.app.ContactManager.model.User;
import com.app.ContactManager.model.UserDTO;
import com.app.ContactManager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> addUser(User user) {
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        }catch(DataIntegrityViolationException dataIntegrityViolationException) {
            return new ResponseEntity<>("Email already registered", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception exception) {

            return new ResponseEntity<>("Failed to Create User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getUserInfo(String username) {
        try{
            User user = userRepository.findUserByUsername(username);
            assert (user != null);
            return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
        }catch(Exception exception){
            return new ResponseEntity<>("Failed to fetch user info", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Transactional
    public ResponseEntity<?> updateUserInfo(String username, User newUserDetails) {
        try {
            User existingUser = userRepository.findUserByUsername(username);
            existingUser.setImageUrl(newUserDetails.getImageUrl());
            existingUser.setAbout(newUserDetails.getAbout());
            existingUser.setName(newUserDetails.getName());
            userRepository.save(existingUser);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        }catch(Exception exception) {
            return  new ResponseEntity<>("Error while updating" + exception.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> delete(String username) {
        try {
            User user = userRepository.findUserByUsername(username);
            userRepository.delete(user);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to Delete", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addContact(String username, Contact contact) {
        try {
            User user = userRepository.findUserByUsername(username);
            List<Contact> contacts = user.getContacts();
            contact.setUser(user);
            contacts.add(contact);
            userRepository.save(user);
            return new ResponseEntity<>("Contact added successfully", HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to add contact" + exception.getLocalizedMessage()
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getUserContacts(String username) {
        try{
            User user = userRepository.findUserByUsername(username);
            return new ResponseEntity<>(user.getContacts(), HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to fetch contacts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> addImage(String username, MultipartFile file) {
        try{
            User user = userRepository.findUserByUsername(username);
            if(user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            String imageUrl = fileStorageService.storeFile(file);
            user.setImageUrl(file.getOriginalFilename());
            userRepository.save(user);
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to upload Image", HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }

    public ResponseEntity<?> getImage(String username) {
        try{
            User user = userRepository.findUserByUsername(username);
            if(user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
            }
            Resource resource = fileStorageService.getFile(user.getImageUrl());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource.getContentAsByteArray());
        }catch (Exception exception) {
            return new ResponseEntity<>("Failed to fetch image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
