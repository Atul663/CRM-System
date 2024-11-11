package com.example.CRM.service;

import com.example.CRM.entity.ContactActivityReport;
import com.example.CRM.entity.OldInteraction;
import com.example.CRM.entity.UpcomingInteraction;
import com.example.CRM.entity.Sales;
import com.example.CRM.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class ContactActivityReportService {

    @Autowired
    private UpcomingInteractionRepository upcomingInteractionRepository;

    @Autowired
    private OldInteractionRepository oldInteractionRepository;

    @Autowired
    private SalesRepository saleRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactActivityReportRepository contactActivityReportRepository;

    // Date formatter for 'MMM dd, yyyy' format (e.g., Nov 8, 2024)
    SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

    public List<ContactActivityReport> generateCustomerActivityReport(LocalDate startDate, LocalDate endDate) {
        // Fetch and filter upcoming interactions by date range
        List<UpcomingInteraction> filteredUpcomingInteractions = filterUpcomingInteractionsByDate(
                upcomingInteractionRepository.findAll(), startDate, endDate
        );

        // Fetch and filter old interactions by date range
        List<OldInteraction> filteredOldInteractions = filterOldInteractionsByDate(
                oldInteractionRepository.findAll(), startDate, endDate
        );

        // Fetch and filter sales by date range
        List<Sales> filteredSales = filterSalesByDate(
                saleRepository.findAll(), startDate, endDate
        );

        // Aggregate activities by contact (customer)
        Map<Long, ContactActivityReport> reportMap = new HashMap<>();

        // Process Upcoming Interactions
        processUpcomingInteractions(filteredUpcomingInteractions, reportMap);

        // Process Old Interactions
        processOldInteractions(filteredOldInteractions, reportMap);

        // Process Sales
        processSales(filteredSales, reportMap);

        // Save aggregated reports to the database
        List<ContactActivityReport> reportList = new ArrayList<>(reportMap.values());
        return contactActivityReportRepository.saveAll(reportList);
    }

    // Method to filter UpcomingInteractions by date
    private List<UpcomingInteraction> filterUpcomingInteractionsByDate(List<UpcomingInteraction> interactions, LocalDate startDate, LocalDate endDate) {
        List<UpcomingInteraction> filteredInteractions = new ArrayList<>();
        for (UpcomingInteraction interaction : interactions) {
            Date parsedDate = null;
            try {
                parsedDate = dateFormatter.parse(interaction.getInteractionDate());
            } catch (ParseException e) {
                e.printStackTrace(); // Handle this exception (log it)
            }

            if (parsedDate != null) {
                LocalDate interactionDate = LocalDate.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault());
                if ((interactionDate.isEqual(startDate) || interactionDate.isAfter(startDate)) &&
                        (interactionDate.isEqual(endDate) || interactionDate.isBefore(endDate))) {
                    filteredInteractions.add(interaction);
                }
            }
        }
        return filteredInteractions;
    }

    // Method to filter OldInteractions by date
    private List<OldInteraction> filterOldInteractionsByDate(List<OldInteraction> interactions, LocalDate startDate, LocalDate endDate) {
        List<OldInteraction> filteredInteractions = new ArrayList<>();
        for (OldInteraction interaction : interactions) {
            Date parsedDate = null;
            try {
                parsedDate = dateFormatter.parse(interaction.getInteractionDate());
            } catch (ParseException e) {
                e.printStackTrace(); // Handle this exception (log it)
            }

            if (parsedDate != null) {
                LocalDate interactionDate = LocalDate.ofInstant(parsedDate.toInstant(), ZoneId.systemDefault());
                if ((interactionDate.isEqual(startDate) || interactionDate.isAfter(startDate)) &&
                        (interactionDate.isEqual(endDate) || interactionDate.isBefore(endDate))) {
                    filteredInteractions.add(interaction);
                }
            }
        }
        return filteredInteractions;
    }

    // Method to filter Sales by date
    private List<Sales> filterSalesByDate(List<Sales> sales, LocalDate startDate, LocalDate endDate) {
        List<Sales> filteredSales = new ArrayList<>();
        // Define a formatter to parse the string with time and fractional seconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        for (Sales sale : sales) {
            try {
                // Parse the closing date as LocalDateTime first
                LocalDateTime closingDateTime = LocalDateTime.parse(sale.getClosingDate(), formatter);

                // Extract the LocalDate from the LocalDateTime
                LocalDate closingDate = closingDateTime.toLocalDate();

                // Check if the closing date is within the range
                if (closingDate.isEqual(startDate) || (closingDate.isAfter(startDate) && closingDate.isBefore(endDate))) {
                    filteredSales.add(sale);
                }
            } catch (DateTimeParseException e) {
                // Handle the case where the closing date is not in the correct format
                System.out.println("Invalid date format: " + sale.getClosingDate());
            }
        }
        return filteredSales;
    }

    // Process UpcomingInteractions and aggregate report data
    private void processUpcomingInteractions(List<UpcomingInteraction> interactions, Map<Long, ContactActivityReport> reportMap) {
        for (UpcomingInteraction interaction : interactions) {
            Long contactId = interaction.getContactId();
            ContactActivityReport report = reportMap.getOrDefault(contactId, new ContactActivityReport());

            // Increment interaction count
            report.setInteractionCount(report.getInteractionCount() + 1);

            // Update the total activities (interaction count + sales activity count)
            report.setTotalActivities(report.getInteractionCount() + report.getSalesActivityCount());

            reportMap.put(contactId, report); // Save back to the map
        }
    }

    // Process OldInteractions and aggregate report data
    private void processOldInteractions(List<OldInteraction> interactions, Map<Long, ContactActivityReport> reportMap) {
        for (OldInteraction interaction : interactions) {
            Long contactId = interaction.getContactId();
            ContactActivityReport report = reportMap.getOrDefault(contactId, new ContactActivityReport());

            // Increment interaction count
            report.setInteractionCount(report.getInteractionCount() + 1);

            // Update the total activities (interaction count + sales activity count)
            report.setTotalActivities(report.getInteractionCount() + report.getSalesActivityCount());

            reportMap.put(contactId, report); // Save back to the map
        }
    }

    // Process Sales and aggregate report data
    private void processSales(List<Sales> sales, Map<Long, ContactActivityReport> reportMap) {
        for (Sales sale : sales) {
            Long contactId = sale.getContactId();
            ContactActivityReport report = reportMap.getOrDefault(contactId, new ContactActivityReport());

            // Increment sales activity count
            report.setSalesActivityCount(report.getSalesActivityCount() + 1);

            // Add sale amount to total sales
            report.setTotalSales(report.getTotalSales() + sale.getDealSize());

            // Update total activities: interactions + sales
            report.setTotalActivities(report.getInteractionCount() + report.getSalesActivityCount());

            reportMap.put(contactId, report); // Save back to the map
        }
    }
}