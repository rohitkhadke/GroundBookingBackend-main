package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.entity.Contact;
import com.repository.ContactRepo;

@Service
public class ContactService {

    @Autowired
    private ContactRepo repo;

    public Contact saveMessage(Contact contact) {
        return repo.save(contact);
    }
    
    public List<Contact> getAllMessages() {
        return repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public void deleteMessage(Integer id) {
        repo.deleteById(id);
    }

    public Contact markAsRead(Integer id) {
        Contact c = repo.findById(id).orElseThrow();
        c.setStatus("READ");
        return repo.save(c);
    }
}