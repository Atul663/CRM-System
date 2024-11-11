package com.example.CRM.service;

import com.example.CRM.entity.Contact;
import com.example.CRM.entity.Sales;
import com.example.CRM.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalesService {

    private final SalesRepository salesRepository;
    private final ContactService contactService;

    @Autowired
    public SalesService(SalesRepository salesRepository, ContactService contactService) {
        this.salesRepository = salesRepository;
        this.contactService = contactService;
    }

    public Sales saveSales(Sales sales) {

        Sales saveSales = salesRepository.save(sales);
        Contact contact = contactService.getContactById(sales.getContactId());
        if (contact != null) {
            // Increment the number of sales for the contact
            contact.setNoOfSales(contact.getNoOfSales() + 1);
            contactService.saveContact(contact); // Save the updated contact back to the database
        }

        return saveSales;
    }

    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }

    public List<Sales> getSalesForContactId(Long contactId) {
        return salesRepository.findByContactId(contactId);  // Assuming you have this method in your repository
    }
    public Optional<Sales> getSalesById(Long id) {
        return salesRepository.findById(id);
    }

    public void deleteSales(Long id) {
        salesRepository.deleteById(id);
    }

    public Sales updateSalesStage(Long id, String newStage) {
        Optional<Sales> salesOpt = salesRepository.findById(id);
        if (salesOpt.isPresent()) {
            Sales sales = salesOpt.get();
            sales.setSalesStage(newStage); // Update sales stage
            return salesRepository.save(sales);
        } else {
            return null; // Or throw an exception
        }
    }

    public Sales updateSale(Long id, Sales saleDetails) {
        Optional<Sales> optionalSale = salesRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sales existingSale = optionalSale.get();
            // Update only the fields that can be modified
            existingSale.setSalesStage(saleDetails.getSalesStage());
            existingSale.setDealSize(saleDetails.getDealSize());
            // If there are other fields to retain, you can keep them the same.
            return salesRepository.save(existingSale); // Save the updated sale
        } else {
            throw new RuntimeException("Sale not found for id: " + id);
        }
    }

    public List<Sales> getSalesBySalesStage(String salesStage) {
        return salesRepository.findBySalesStage(salesStage); // Assuming you have this method in your repository
    }
}