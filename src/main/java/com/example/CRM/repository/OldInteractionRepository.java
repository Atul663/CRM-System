package com.example.CRM.repository;

import com.example.CRM.entity.OldInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface OldInteractionRepository extends JpaRepository<OldInteraction, Long> {
    // You can add custom queries if necessary

//    List<OldInteraction> findByInteractionDateBetween(LocalDate startDate, LocalDate endDate);
List<OldInteraction> findByContactId(Long contactId);


}