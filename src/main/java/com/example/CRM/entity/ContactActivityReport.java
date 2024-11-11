package com.example.CRM.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "contact_activity_report") // Table name in the database
public class ContactActivityReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate unique ID
    private Long id;  // Primary key for the entity
//
//    @Column(name = "customer_name")  // Column to store customer name
//    private String customerName;
//
//    @Column(name = "interaction_count")  // Column to store interaction count
//    private int interactionCount;
//
//    @Column(name = "sales_activity_count")  // Column to store sales activity count
//    private int salesActivityCount;
//
//    @Column(name = "total_activities")  // Column to store total activities count
//    private int totalActivities;



//        @Id
        private Long contactId =100L;  // Unique ID of the contact

        private int interactionCount;  // Number of interactions (old + upcoming)
        private int salesActivityCount;  // Number of sales activities
        private double totalSales;  // Total value of sales
        private int totalActivities;  // Sum of interaction count and sales activities

        // Getters and Setters for each field...

    // Getters and Setters

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName) {
//        this.customerName = customerName;
//    }
//
//    public Long getContactId() {
//        return contactId;
//    }
//
//    public void setContactId(Long contactId) {
//        this.contactId = contactId;
//    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public int getInteractionCount() {
        return interactionCount;
    }

    public void setInteractionCount(int interactionCount) {
        this.interactionCount = interactionCount;
    }

    public int getSalesActivityCount() {
        return salesActivityCount;
    }

    public void setSalesActivityCount(int salesActivityCount) {
        this.salesActivityCount = salesActivityCount;
    }

    public int getTotalActivities() {
        return totalActivities;
    }

    public void setTotalActivities(int totalActivities) {
        this.totalActivities = totalActivities;
    }

//     Optional: toString() for debugging and logging
    @Override
    public String toString() {
        return "ContactActivityReport{" +
                "id=" + id +
//                ", customerName='" + customerName + '\'' +
                ", interactionCount=" + interactionCount +
                ", salesActivityCount=" + salesActivityCount +
                ", totalActivities=" + totalActivities +
                '}';
    }
}