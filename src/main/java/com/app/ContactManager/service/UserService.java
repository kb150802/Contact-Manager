package com.app.ContactManager.service;

import com.app.ContactManager.model.User;
import com.app.ContactManager.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.logging.Logger;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<String> addUser(User user) {
        user.setRole("USER");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Exception exceptionOccured ;
        try {
            userRepository.save(user);
            return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
        }catch(DataIntegrityViolationException exception) {
            return new ResponseEntity<>("Data Integrity Violation : " + exception.getRootCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception exception) {
            return  new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
