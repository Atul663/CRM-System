package com.example.CRM;

import com.example.CRM.controller.ActivityController;
import com.example.CRM.entity.ContactActivityReport;
import com.example.CRM.service.ContactActivityReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ActivityControllerTest {

    @Mock
    private ContactActivityReportService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateActivityReport_WithResults() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        List<ContactActivityReport> reportList = new ArrayList<>();
        reportList.add(new ContactActivityReport());
        reportList.add(new ContactActivityReport());

        when(activityService.generateCustomerActivityReport(startDate, endDate)).thenReturn(reportList);

        ResponseEntity<List<ContactActivityReport>> response = activityController.generateActivityReport(startDate, endDate);

        assertEquals(2, response.getBody().size());
        verify(activityService, times(1)).generateCustomerActivityReport(startDate, endDate);
    }

    @Test
    void testGenerateActivityReport_NoResults() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(activityService.generateCustomerActivityReport(startDate, endDate)).thenReturn(new ArrayList<>());

        ResponseEntity<List<ContactActivityReport>> response = activityController.generateActivityReport(startDate, endDate);

        assertEquals(0, response.getBody().size());
        verify(activityService, times(1)).generateCustomerActivityReport(startDate, endDate);
    }
}