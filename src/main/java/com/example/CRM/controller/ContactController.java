package com.example.CRM.controller;

import com.example.CRM.entity.Contact;
import com.example.CRM.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    // Logger for logging messages
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    // Service to manage contact operations
    private final ContactService contactService;

    // Constructor for injecting dependencies
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Creates a new contact.
     * @param contact - the Contact object containing contact details
     * @return ResponseEntity with the created Contact object
     */
    @PostMapping("/create")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        LOGGER.info("Creating a new contact: {}", contact);
        contact.setCreatedDate(new Date());
        Contact createdContact = contactService.saveContact(contact);
        LOGGER.info("Created contact: {}", createdContact);
        return ResponseEntity.ok(createdContact);
    }

    /**
     * Retrieves all contacts.
     * @return ResponseEntity with the list of Contact objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getAllContacts() {
        LOGGER.info("Fetching all contacts");
        List<Contact> contacts = contactService.getAllContacts();
        LOGGER.info("Fetched {} contacts", contacts.size());
        return ResponseEntity.ok(contacts);
    }

    /**
     * Retrieves a contact by its ID.
     * @param id - the ID of the contact to retrieve
     * @return ResponseEntity with the Contact object if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        LOGGER.info("Fetching contact with id: {}", id);
        Contact contact = contactService.getContactById(id);
        if (contact != null) {
            LOGGER.info("Found contact: {}", contact);
            return ResponseEntity.ok(contact);
        } else {
            LOGGER.warn("Contact with id {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a contact by its ID.
     * @param id - the ID of the contact to delete
     * @return ResponseEntity with no content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        LOGGER.info("Deleting contact with id: {}", id);
        contactService.deleteContact(id);
        LOGGER.info("Contact with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates a contact by its ID.
     * @param id - the ID of the contact to update
     * @param contact - the updated Contact object
     * @return ResponseEntity with the updated Contact object if successful, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        LOGGER.info("Updating contact with id: {}", id);
        contact.setCreatedDate(new Date());
        Contact updatedContact = contactService.updateContact(id, contact);
        if (updatedContact != null) {
            LOGGER.info("Updated contact: {}", updatedContact);
            return ResponseEntity.ok(updatedContact);
        } else {
            LOGGER.warn("Contact with id {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Increments the sales count for a specific contact by its ID.
     * @param id - the ID of the contact to update
     * @return ResponseEntity with the updated Contact object
     */
    @PutMapping("/{id}/incrementSales")
    public ResponseEntity<Contact> incrementSales(@PathVariable Long id) {
        LOGGER.info("Incrementing sales for contact with id: {}", id);
        Contact updatedContact = contactService.incrementSales(id);
        LOGGER.info("Sales incremented for contact: {}", updatedContact);
        return ResponseEntity.ok(updatedContact);
    }
}