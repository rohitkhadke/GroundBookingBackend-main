package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
	
}