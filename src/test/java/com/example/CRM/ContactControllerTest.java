package com.example.CRM;

import com.example.CRM.controller.ContactController;
import com.example.CRM.entity.Contact;
import com.example.CRM.service.ContactService;
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

class ContactControllerTest {

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateContact() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setCreatedDate(new Date());

        when(contactService.saveContact(contact)).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.createContact(contact);

        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).saveContact(contact);
    }

    @Test
    void testGetAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        contacts.add(new Contact());

        when(contactService.getAllContacts()).thenReturn(contacts);

        ResponseEntity<List<Contact>> response = contactController.getAllContacts();

        assertEquals(2, response.getBody().size());
        verify(contactService, times(1)).getAllContacts();
    }

    @Test
    void testGetContactById_Found() {
        Contact contact = new Contact();
        contact.setId(1L);

        when(contactService.getContactById(1L)).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.getContactById(1L);

        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).getContactById(1L);
    }

    @Test
    void testGetContactById_NotFound() {
        when(contactService.getContactById(1L)).thenReturn(null);

        ResponseEntity<Contact> response = contactController.getContactById(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(contactService, times(1)).getContactById(1L);
    }

    @Test
    void testDeleteContact() {
        doNothing().when(contactService).deleteContact(1L);

        ResponseEntity<Void> response = contactController.deleteContact(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(contactService, times(1)).deleteContact(1L);
    }

    @Test
    void testUpdateContact_Found() {
        Contact contact = new Contact();
        contact.setId(1L);
        Contact updatedContact = new Contact();
        updatedContact.setId(1L);

        when(contactService.updateContact(1L, contact)).thenReturn(updatedContact);

        ResponseEntity<Contact> response = contactController.updateContact(1L, contact);

        assertEquals(updatedContact, response.getBody());
        verify(contactService, times(1)).updateContact(1L, contact);
    }

    @Test
    void testUpdateContact_NotFound() {
        Contact contact = new Contact();
        contact.setId(1L);

        when(contactService.updateContact(1L, contact)).thenReturn(null);

        ResponseEntity<Contact> response = contactController.updateContact(1L, contact);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(contactService, times(1)).updateContact(1L, contact);
    }

    @Test
    void testIncrementSales() {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setNoOfSales(5); // Assuming Contact has a salesCount field

        when(contactService.incrementSales(1L)).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.incrementSales(1L);

        assertEquals(contact, response.getBody());
        verify(contactService, times(1)).incrementSales(1L);
    }
}