package com.example.CRM.repository;

import com.example.CRM.entity.UpcomingInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpcomingInteractionRepository extends JpaRepository<UpcomingInteraction, Long> {
    // Additional custom queries can be added here
//    List<UpcomingInteraction> findByInteractionDateBetween(LocalDate startDate, LocalDate endDate);
    List<UpcomingInteraction> findByContactId(Long contactId);

}