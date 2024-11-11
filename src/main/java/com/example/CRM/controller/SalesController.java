package com.example.CRM.controller;

import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Sales;
import com.example.CRM.service.ContactService;
import com.example.CRM.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    // Services for handling sales and contacts
    private final SalesService salesService;
    private final ContactService contactService;

    // Logger for logging messages to the console
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesController.class);

    // Constructor for injecting dependencies
    @Autowired
    public SalesController(SalesService salesService, ContactService contactService) {
        this.salesService = salesService;
        this.contactService = contactService;
    }

    /**
     * Creates a new Sales record.
     * Sets the sale date to the current date and associates the sale with a contact.
     * @param sales - the Sales object containing sale details
     * @return ResponseEntity with the created Sales object
     */
    @PostMapping("/create")
    public ResponseEntity<Sales> createSales(@RequestBody Sales sales) {
        Contact contact = contactService.getContactById(sales.getContactId());
        sales.setSaleDate(new Date());
        Sales createdSales = salesService.saveSales(sales);
        LOGGER.info("Sales created successfully with ID: {}", createdSales.getId());
        return ResponseEntity.ok(createdSales);
    }

    /**
     * Fetches all Sales records.
     * @return ResponseEntity with the list of all Sales records
     */
    @GetMapping("/all")
    public ResponseEntity<List<Sales>> getAllSales() {
        List<Sales> sales = salesService.getAllSales();
        LOGGER.info("Fetched {} sales records", sales.size());
        return ResponseEntity.ok(sales);
    }

    /**
     * Fetches a Sales record by its ID.
     * @param id - the ID of the Sales record
     * @return ResponseEntity with the Sales object if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sales> getSalesById(@PathVariable Long id) {
        return salesService.getSalesById(id)
                .map(sales -> {
                    LOGGER.info("Sales with ID {} found", id);
                    return ResponseEntity.ok(sales);
                })
                .orElseGet(() -> {
                    LOGGER.warn("Sales with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Deletes a Sales record by its ID.
     * @param id - the ID of the Sales record to delete
     * @return ResponseEntity with no content status if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id) {
        salesService.deleteSales(id);
        LOGGER.info("Sales with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the sales stage of an existing Sales record.
     * @param id - the ID of the Sales record
     * @param newStage - the new stage to set
     * @return ResponseEntity with the updated Sales object if successful, or 404 if not found
     */
    @PutMapping("/{id}/stage")
    public ResponseEntity<Sales> updateSalesStage(@PathVariable Long id, @RequestBody String newStage) {
        Sales updatedSales = salesService.updateSalesStage(id, newStage);
        if (updatedSales != null) {
            LOGGER.info("Sales stage for ID {} updated successfully", id);
            return ResponseEntity.ok(updatedSales);
        } else {
            LOGGER.warn("Sales with ID {} not found for stage update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates an entire Sales record with new details.
     * @param id - the ID of the Sales record
     * @param saleDetails - the updated Sales details
     * @return ResponseEntity with the updated Sales object if successful, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sales> updateSale(@PathVariable Long id, @RequestBody Sales saleDetails) {
        Sales updatedSale = salesService.updateSale(id, saleDetails);
        if (updatedSale != null) {
            LOGGER.info("Sales with ID {} updated successfully", id);
            return ResponseEntity.ok(updatedSale);
        } else {
            LOGGER.warn("Sales with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Fetches all Sales records associated with a specific Contact ID.
     * @param contactId - the ID of the Contact
     * @return ResponseEntity with the list of Sales records if found, or no content status if empty
     */
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<List<Sales>> getSalesForContactId(@PathVariable Long contactId) {
        List<Sales> salesForContact = salesService.getSalesForContactId(contactId);
        if (salesForContact.isEmpty()) {
            LOGGER.warn("No sales found for contact ID {}", contactId);
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Fetched {} sales for contact ID {}", salesForContact.size(), contactId);
        return ResponseEntity.ok(salesForContact);
    }

    /**
     * Fetches all Sales records with a specific sales stage.
     * @param salesStage - the sales stage to filter by
     * @return ResponseEntity with the list of Sales records if found, or no content status if empty
     */
    @GetMapping("/stage/{salesStage}")
    public ResponseEntity<List<Sales>> getSalesBySalesStage(@PathVariable String salesStage) {
        List<Sales> salesByStage = salesService.getSalesBySalesStage(salesStage);
        if (salesByStage.isEmpty()) {
            LOGGER.warn("No sales found for stage {}", salesStage);
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Fetched {} sales for stage {}", salesByStage.size(), salesStage);
        return ResponseEntity.ok(salesByStage);
    }
}