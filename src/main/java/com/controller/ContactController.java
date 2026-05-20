package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Contact;
import com.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    @Autowired
    private ContactService service;

    @PostMapping
    public ResponseEntity<?> saveContact(@Valid @RequestBody Contact contact) {
        Contact saved = service.saveMessage(contact);
        return ResponseEntity.ok(saved);
    }
    
    @GetMapping
    public List<Contact> getAllContacts() {
        return service.getAllMessages();
    }

    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Integer id) {
        service.deleteMessage(id);
    }

    @PutMapping("/{id}/read")
    public Contact markAsRead(@PathVariable Integer id) {
        return service.markAsRead(id);
    }
}