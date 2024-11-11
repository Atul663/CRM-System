package com.example.CRM.controller;

import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Lead;
import com.example.CRM.service.ContactService;
import com.example.CRM.service.LeadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    // Services for handling leads and contacts
    private final LeadService leadService;
    private final ContactService contactService;

    // Logger for logging messages to the console
    private static final Logger LOGGER = LoggerFactory.getLogger(LeadController.class);

    // Constructor for injecting dependencies
    @Autowired
    public LeadController(LeadService leadService, ContactService contactService) {
        this.leadService = leadService;
        this.contactService = contactService;
    }

    /**
     * Creates a new Lead record.
     * Sets the created date to the current date.
     * @param lead - the Lead object containing lead details
     * @return ResponseEntity with the created Lead object
     */
    @PostMapping("/create")
    public ResponseEntity<Lead> createLead(@RequestBody Lead lead) {
        lead.setCreatedDate(new Date());
        Lead savedLead = leadService.saveLead(lead);
        LOGGER.info("Lead created successfully with ID: {}", savedLead.getId());
        return ResponseEntity.ok(savedLead);
    }

    /**
     * Fetches all Lead records.
     * @return ResponseEntity with the list of all Lead records
     */
    @GetMapping("/all")
    public ResponseEntity<List<Lead>> getAllLeads() {
        List<Lead> leads = leadService.getAllLeads();
        LOGGER.info("Fetched {} leads", leads.size());
        return ResponseEntity.ok(leads);
    }

    /**
     * Fetches a Lead record by its ID.
     * @param id - the ID of the Lead record
     * @return ResponseEntity with the Lead object if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Lead> getLeadById(@PathVariable Long id) {
        Lead lead = leadService.getLeadById(id);
        if (lead != null) {
            LOGGER.info("Fetched lead with ID: {}", id);
            return ResponseEntity.ok(lead);
        } else {
            LOGGER.warn("Lead with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a Lead record by its ID.
     * @param id - the ID of the Lead record to delete
     * @return ResponseEntity with no content status if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        LOGGER.info("Lead with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates an entire Lead record with new details.
     * @param id - the ID of the Lead record
     * @param lead - the updated Lead details
     * @return ResponseEntity with the updated Lead object if successful, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Lead> updateLead(@PathVariable Long id, @RequestBody Lead lead) {
        Lead updatedLead = leadService.updateLead(id, lead);
        if (updatedLead != null) {
            LOGGER.info("Lead with ID: {} updated successfully", id);
            return ResponseEntity.ok(updatedLead);
        } else {
            LOGGER.warn("Lead with ID: {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Converts a Lead into a Contact.
     * @param id - the ID of the Lead to be converted
     * @return ResponseEntity with the created Contact object if successful, or 404 if not found
     */
    @PostMapping("/{id}/convertToContact")
    public ResponseEntity<Contact> convertLeadToContact(@PathVariable Long id) {
        Contact contact = leadService.convertLeadToContact(id);
        if (contact != null) {
            LOGGER.info("Lead with ID: {} converted to contact successfully", id);
            return ResponseEntity.ok(contact);
        } else {
            LOGGER.warn("Lead with ID: {} not found for conversion", id);
            return ResponseEntity.notFound().build();
        }
    }
}