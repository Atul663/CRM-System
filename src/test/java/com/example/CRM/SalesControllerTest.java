package com.example.CRM;

import com.example.CRM.controller.SalesController;
import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Sales;
import com.example.CRM.service.ContactService;
import com.example.CRM.service.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SalesControllerTest {

    @Mock
    private SalesService salesService;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private SalesController salesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSales() {
        Sales sales = new Sales();
        sales.setContactId(1L);
        sales.setSaleDate(new Date());

        Contact contact = new Contact();
        contact.setId(1L);

        when(contactService.getContactById(1L)).thenReturn(contact);
        when(salesService.saveSales(sales)).thenReturn(sales);

        ResponseEntity<Sales> response = salesController.createSales(sales);

        assertEquals(sales, response.getBody());
        verify(contactService, times(1)).getContactById(1L);
        verify(salesService, times(1)).saveSales(sales);
    }

    @Test
    void testGetAllSales() {
        List<Sales> salesList = new ArrayList<>();
        Sales sale1 = new Sales();
        Sales sale2 = new Sales();
        salesList.add(sale1);
        salesList.add(sale2);

        when(salesService.getAllSales()).thenReturn(salesList);

        ResponseEntity<List<Sales>> response = salesController.getAllSales();

        assertEquals(2, response.getBody().size());
        verify(salesService, times(1)).getAllSales();
    }

    @Test
    void testGetSalesById_Found() {
        Sales sales = new Sales();
        sales.setId(1L);

        when(salesService.getSalesById(1L)).thenReturn(Optional.of(sales));

        ResponseEntity<Sales> response = salesController.getSalesById(1L);

        assertEquals(sales, response.getBody());
        verify(salesService, times(1)).getSalesById(1L);
    }

    @Test
    void testGetSalesById_NotFound() {
        when(salesService.getSalesById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Sales> response = salesController.getSalesById(1L);

        assertEquals(ResponseEntity.notFound().build(), response);
        verify(salesService, times(1)).getSalesById(1L);
    }

    @Test
    void testDeleteSales() {
        doNothing().when(salesService).deleteSales(1L);

        ResponseEntity<Void> response = salesController.deleteSales(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(salesService, times(1)).deleteSales(1L);
    }

    @Test
    void testUpdateSalesStage() {
        Sales sales = new Sales();
        sales.setId(1L);
        sales.setSalesStage("New Stage");

        when(salesService.updateSalesStage(1L, "New Stage")).thenReturn(sales);

        ResponseEntity<Sales> response = salesController.updateSalesStage(1L, "New Stage");

        assertEquals(sales, response.getBody());
        verify(salesService, times(1)).updateSalesStage(1L, "New Stage");
    }

    @Test
    void testGetSalesForContactId() {
        List<Sales> salesList = new ArrayList<>();
        Sales sale = new Sales();
        salesList.add(sale);

        when(salesService.getSalesForContactId(1L)).thenReturn(salesList);

        ResponseEntity<List<Sales>> response = salesController.getSalesForContactId(1L);

        assertEquals(salesList, response.getBody());
        verify(salesService, times(1)).getSalesForContactId(1L);
    }
}