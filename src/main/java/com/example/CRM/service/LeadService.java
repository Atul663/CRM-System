package com.example.CRM.service;

import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Lead;
import com.example.CRM.repository.ContactRepository;
import com.example.CRM.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeadService {

    private final LeadRepository leadRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public LeadService(LeadRepository leadRepository, ContactRepository contactRepository) {
        this.leadRepository = leadRepository;
        this.contactRepository = contactRepository;
    }

    public Lead saveLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public Lead getLeadById(Long id) {
        return leadRepository.findById(id).orElse(null);
    }

    public void deleteLead(Long id) {
        leadRepository.deleteById(id);
    }

    public Lead updateLead(Long id, Lead leadDetails) {
        Optional<Lead> existingLead = leadRepository.findById(id);
        if (existingLead.isPresent()) {
            Lead lead = existingLead.get();
            lead.setName(leadDetails.getName());
            lead.setEmail(leadDetails.getEmail());
            lead.setPhoneNumber(leadDetails.getPhoneNumber());
            lead.setAddress(leadDetails.getAddress());
            lead.setCompany(leadDetails.getCompany());
            lead.setPosition(leadDetails.getPosition());
            lead.setSource(leadDetails.getSource());
            lead.setStatus(leadDetails.getStatus());
            lead.setNotes(leadDetails.getNotes());
//            lead.setImage(leadDetails.getImage());
            lead.setCreatedDate(leadDetails.getCreatedDate());
            return leadRepository.save(lead);
        } else {
            return null; // Or throw an exception
        }
    }

    public Contact convertLeadToContact(Long leadId) {
        Lead lead = getLeadById(leadId);
        if (lead == null) {
            return null;  // Or throw an exception if preferred
        }

        // Convert Lead to Contact
        Contact contact = new Contact();
        contact.setName(lead.getName());
        contact.setEmail(lead.getEmail());
        contact.setPhoneNumber(lead.getPhoneNumber());
        contact.setAddress(lead.getAddress());
        contact.setCompany(lead.getCompany());
        contact.setSource(lead.getSource());
        contact.setStatus(lead.getStatus());
        contact.setPosition(lead.getPosition());
        contact.setNotes(lead.getNotes());
        contact.setOwnerName(lead.getOwnerName());
        contact.setCreatedDate(new Date());

        // Save the Contact and delete the Lead
        Contact savedContact = contactRepository.save(contact);
        leadRepository.deleteById(leadId); // Delete the lead after conversion

        return savedContact;
    }
}