package com.app.ContactManager.repository;

import com.app.ContactManager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    @Query("select c from Contact c where c.cId = :cId")
    public Contact getContactById(Integer cId);
}
