package com.example.CRM.controller;

import com.example.CRM.entity.ContactActivityReport;
import com.example.CRM.service.ContactActivityReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    // Logger for logging activity
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    // Service to handle business logic for activity reports
    @Autowired
    private ContactActivityReportService activityService;

    /**
     * Endpoint to generate a customer activity report for a specified date range.
     *
     * @param startDate - Start date of the report range (inclusive).
     * @param endDate - End date of the report range (inclusive).
     * @return ResponseEntity containing a list of ContactActivityReport entries within the date range.
     */
    @GetMapping("/report")
    public ResponseEntity<List<ContactActivityReport>> generateActivityReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        LOGGER.info("Request received for activity report generation from {} to {}", startDate, endDate);

        // Fetch the activity report for the given date range
        List<ContactActivityReport> report = activityService.generateCustomerActivityReport(startDate, endDate);

        // Log warning if no report entries were found in the specified range
        if (report.isEmpty()) {
            LOGGER.warn("No activity report found for the provided date range: {} to {}", startDate, endDate);
        } else {
            LOGGER.info("Generated activity report with {} entries", report.size());
        }

        // Return the report as a ResponseEntity
        return ResponseEntity.ok(report);
    }
}