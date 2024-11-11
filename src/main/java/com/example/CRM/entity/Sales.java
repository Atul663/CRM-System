package com.example.CRM.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contactId;
    private String contactName; // Contact's name
    private String salesStage;
    private double dealSize;
    private double probabilityOfClosing;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy", timezone = "Asia/Kolkata")
    private String  closingDate;
    private String ownerName;
    private Date saleDate;
//    private List<String> salesStages;
//    private String ownerName;
//    private Long contactId;


    // List of possible sales stages
    @ElementCollection
    private List<String> salesStages;

    public Sales() {}

    // Getters and Setters...

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    //
//    public Contact getContact() {
//        return contact;
//    }
//
//    public void setContact(Contact contact) {
//        this.contact = contact;
//    }

//    public Long getContactId() {
//        return contactId;
//    }
//
//    public void setContactId(Long contactId) {
//        this.contactId = contactId;
//    }

    public String getSalesStage() {
        return salesStage;
    }

    public void setSalesStage(String salesStage) {
        this.salesStage = salesStage;
    }

    public double getDealSize() {
        return dealSize;
    }

    public void setDealSize(double dealSize) {
        this.dealSize = dealSize;
    }

    public double getProbabilityOfClosing() {
        return probabilityOfClosing;
    }

    public void setProbabilityOfClosing(double probabilityOfClosing) {
        this.probabilityOfClosing = probabilityOfClosing;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public List<String> getSalesStages() {
        return salesStages;
    }

    public void setSalesStages(List<String> salesStages) {
        this.salesStages = salesStages;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}