package com.example.CRM.repository;

import com.example.CRM.entity.ContactActivityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactActivityReportRepository extends JpaRepository<ContactActivityReport, Long> {
    // Custom queries can be added here if necessary
}