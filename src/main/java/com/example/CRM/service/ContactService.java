package com.example.CRM.service;

import com.example.CRM.entity.Contact;
import com.example.CRM.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact saveContact(Contact contact) {
        contact.setCreatedDate(new Date());
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact contactDetails) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(contactDetails.getName());
            contact.setEmail(contactDetails.getEmail());
            contact.setPhoneNumber(contactDetails.getPhoneNumber());
            contact.setAddress(contactDetails.getAddress());
            contact.setCompany(contactDetails.getCompany());
            contact.setPosition(contactDetails.getPosition());
            contact.setSource(contactDetails.getSource());
            contact.setStatus(contactDetails.getStatus());
            contact.setNotes(contactDetails.getNotes());
//            contact.setImage(contactDetails.getImage());
            contact.setCreatedDate(contactDetails.getCreatedDate());
            return contactRepository.save(contact);
        } else {
            return null; // Or throw an exception
        }
    }

    public Contact incrementSales(Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        contact.setNoOfSales(contact.getNoOfSales() + 1);
        return contactRepository.save(contact);
    }
}