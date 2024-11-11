package com.example.CRM.repository;

import com.example.CRM.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
//    List<Sales> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
    List<Sales> findByContactId(Long contactId);
    List<Sales> findBySalesStage(String salesStage);


}