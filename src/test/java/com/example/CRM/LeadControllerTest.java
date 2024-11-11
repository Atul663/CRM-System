package com.example.CRM;

import com.example.CRM.controller.LeadController;
import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Lead;
import com.example.CRM.service.ContactService;
import com.example.CRM.service.LeadService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class LeadControllerTest {

    @Mock
    private LeadService leadService;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private LeadController leadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLead() {
        Lead lead = new Lead();
        lead.setId(1L);
        lead.setCreatedDate(new Date());

        when(leadService.saveLead(lead)).thenReturn(lead);

        ResponseEntity<Lead> response = leadController.createLead(lead);

        assertEquals(lead, response.getBody());
        verify(leadService, times(1)).saveLead(lead);
    }

    @Test
    void testGetAllLeads() {
        List<Lead> leadList = new ArrayList<>();
        leadList.add(new Lead());
        leadList.add(new Lead());

        when(leadService.getAllLeads()).thenReturn(leadList);

        ResponseEntity<List<Lead>> response = leadController.getAllLeads();

        assertEquals(2, response.getBody().size());
        verify(leadService, times(1)).getAllLeads();
    }

    @Test
    void testGetLeadById_Found() {
        Lead lead = new Lead();
        lead.setId(1L);

        when(leadService.getLeadById(1L)).thenReturn(lead);

        ResponseEntity<Lead> response = leadController.getLeadById(1L);

        assertEquals(lead, response.getBody());
        verify(leadService, times(1)).getLeadById(1L);
    }

    @Test
    void testGetLeadById_NotFound() {
        when(leadService.getLeadById(1L)).thenReturn(null);

        ResponseEntity<Lead> response = leadController.getLeadById(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(leadService, times(1)).getLeadById(1L);
    }

    @Test
    void testDeleteLead() {
        doNothing().when(leadService).deleteLead(1L);

        ResponseEntity<Void> response = leadController.deleteLead(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(leadService, times(1)).deleteLead(1L);
    }

    @Test
    void testUpdateLead_Found() {
        Lead lead = new Lead();
        lead.setId(1L);
        Lead updatedLead = new Lead();
        updatedLead.setId(1L);

        when(leadService.updateLead(1L, lead)).thenReturn(updatedLead);

        ResponseEntity<Lead> response = leadController.updateLead(1L, lead);

        assertEquals(updatedLead, response.getBody());
        verify(leadService, times(1)).updateLead(1L, lead);
    }

    @Test
    void testUpdateLead_NotFound() {
        Lead lead = new Lead();
        lead.setId(1L);

        when(leadService.updateLead(1L, lead)).thenReturn(null);

        ResponseEntity<Lead> response = leadController.updateLead(1L, lead);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(leadService, times(1)).updateLead(1L, lead);
    }

    @Test
    void testConvertLeadToContact_Found() {
        Contact contact = new Contact();
        contact.setId(1L);

        when(leadService.convertLeadToContact(1L)).thenReturn(contact);

        ResponseEntity<Contact> response = leadController.convertLeadToContact(1L);

        assertEquals(contact, response.getBody());
        verify(leadService, times(1)).convertLeadToContact(1L);
    }

    @Test
    void testConvertLeadToContact_NotFound() {
        when(leadService.convertLeadToContact(1L)).thenReturn(null);

        ResponseEntity<Contact> response = leadController.convertLeadToContact(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(leadService, times(1)).convertLeadToContact(1L);
    }
}