package com.example.CRM.controller;

import com.example.CRM.entity.OldInteraction;
import com.example.CRM.entity.UpcomingInteraction;
import com.example.CRM.service.InteractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    // Service to manage interactions
    private final InteractionService interactionService;

    // Logger for logging messages
    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionController.class);

    // Constructor for injecting dependencies
    @Autowired
    public InteractionController(InteractionService interactionService) {
        this.interactionService = interactionService;
    }

    /**
     * Adds a new upcoming interaction.
     * @param interactionLog - the UpcomingInteraction object containing interaction details
     * @return ResponseEntity with the added UpcomingInteraction object
     */
    @PostMapping("/add")
    public ResponseEntity<UpcomingInteraction> addInteraction(@RequestBody UpcomingInteraction interactionLog) {
        LOGGER.info("Received interaction request: {}", interactionLog);
        UpcomingInteraction interaction = interactionService.addInteraction(interactionLog);
        LOGGER.info("Successfully added interaction with ID: {}", interaction.getId());
        return ResponseEntity.ok(interaction);
    }

    /**
     * Retrieves all upcoming interactions, ordered by date.
     * @return ResponseEntity with the list of UpcomingInteraction objects
     */
    @GetMapping("/all/upcoming")
    public ResponseEntity<List<UpcomingInteraction>> getAllUpcomingInteractions() {
        List<UpcomingInteraction> interactions = interactionService.getAllInteractionsOrdered();
        LOGGER.info("Fetched {} upcoming interactions", interactions.size());
        return ResponseEntity.ok(interactions);
    }

    /**
     * Retrieves all old interactions, ordered by date.
     * @return ResponseEntity with the list of OldInteraction objects in reverse order
     */
    @GetMapping("/all/old")
    public ResponseEntity<List<OldInteraction>> getAllOldInteractions() {
        List<OldInteraction> oldInteractions = interactionService.getAllOldInteractionsOrdered();
        Collections.reverse(oldInteractions);
        LOGGER.info("Fetched {} old interactions", oldInteractions.size());
        return ResponseEntity.ok(oldInteractions);
    }

    /**
     * Retrieves upcoming interactions by contact ID.
     * @param contactId - the contact ID for which to fetch interactions
     * @return ResponseEntity with the list of UpcomingInteraction objects for the contact, or 204 if none are found
     */
    @GetMapping("/upcoming_interactions/{contactId}")
    public ResponseEntity<List<UpcomingInteraction>> getUpcomingInteractionsByContactId(@PathVariable Long contactId) {
        List<UpcomingInteraction> interactions = interactionService.getUpcomingInteractionsByContactId(contactId);
        if (interactions.isEmpty()) {
            LOGGER.warn("No upcoming interactions found for contact ID: {}", contactId);
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Fetched {} upcoming interactions for contact ID: {}", interactions.size(), contactId);
        return ResponseEntity.ok(interactions);
    }

    /**
     * Retrieves old interactions by contact ID.
     * @param contactId - the contact ID for which to fetch old interactions
     * @return ResponseEntity with the list of OldInteraction objects for the contact, or 204 if none are found
     */
    @GetMapping("/old_interactions/{contactId}")
    public ResponseEntity<List<OldInteraction>> getInteractionsByContactId(@PathVariable Long contactId) {
        List<OldInteraction> interactions = interactionService.getOldInteractionsByContactId(contactId);
        if (interactions.isEmpty()) {
            LOGGER.warn("No old interactions found for contact ID: {}", contactId);
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Fetched {} old interactions for contact ID: {}", interactions.size(), contactId);
        return ResponseEntity.ok(interactions);
    }

    /**
     * Deletes an old interaction by its ID.
     * @param interactionId - the ID of the old interaction to delete
     * @return ResponseEntity with a success message if deleted, or 404 if not found
     */
    @DeleteMapping("/old/{interactionId}")
    public ResponseEntity<String> deleteOldInteraction(@PathVariable Long interactionId) {
        boolean isDeleted = interactionService.deleteOldInteraction(interactionId);
        if (isDeleted) {
            LOGGER.info("Successfully deleted old interaction with ID {}", interactionId);
            return ResponseEntity.ok("Interaction deleted successfully.");
        } else {
            LOGGER.warn("Old interaction with ID {} not found", interactionId);
            return ResponseEntity.status(404).body("Interaction not found.");
        }
    }

    /**
     * Deletes an upcoming interaction by its ID.
     * @param interactionId - the ID of the upcoming interaction to delete
     * @return ResponseEntity with a success message if deleted, or 404 if not found
     */
    @DeleteMapping("/upcoming/{interactionId}")
    public ResponseEntity<String> deleteUpcomingInteraction(@PathVariable Long interactionId) {
        boolean isDeleted = interactionService.deleteUpcomingInteraction(interactionId);
        if (isDeleted) {
            LOGGER.info("Successfully deleted upcoming interaction with ID {}", interactionId);
            return ResponseEntity.ok("Upcoming interaction deleted successfully.");
        } else {
            LOGGER.warn("Upcoming interaction with ID {} not found", interactionId);
            return ResponseEntity.status(404).body("Upcoming interaction not found.");
        }
    }

    /**
     * Updates an upcoming interaction by its ID.
     * @param interactionId - the ID of the upcoming interaction to update
     * @param updatedInteraction - the updated interaction details
     * @return ResponseEntity with the updated UpcomingInteraction object if successful, or 404 if not found
     */
    @PutMapping("/upcoming/{interactionId}")
    public ResponseEntity<UpcomingInteraction> updateUpcomingInteraction(
            @PathVariable Long interactionId,
            @RequestBody UpcomingInteraction updatedInteraction) {
        UpcomingInteraction updated = interactionService.updateUpcomingInteraction(interactionId, updatedInteraction);
        if (updated != null) {
            LOGGER.info("Successfully updated upcoming interaction with ID {}", interactionId);
            return ResponseEntity.ok(updated);
        } else {
            LOGGER.warn("Upcoming interaction with ID {} not found for update", interactionId);
            return ResponseEntity.status(404).body(null);
        }
    }
}