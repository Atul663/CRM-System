package com.example.CRM.service;

import com.example.CRM.entity.UpcomingInteraction;
import com.example.CRM.entity.OldInteraction;
import com.example.CRM.repository.UpcomingInteractionRepository;
import com.example.CRM.repository.OldInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InteractionService {

    private final UpcomingInteractionRepository upcomingInteractionRepository;
    private final OldInteractionRepository oldInteractionRepository;  // Inject OldInteractionRepository
//    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);  // Correct 24-hour format

    @Autowired
    public InteractionService(UpcomingInteractionRepository UpcominginteractionRepository, OldInteractionRepository oldInteractionRepository) {
        this.upcomingInteractionRepository = UpcominginteractionRepository;
        this.oldInteractionRepository = oldInteractionRepository;  // Initialize OldInteractionRepository
    }

    public UpcomingInteraction addInteraction(UpcomingInteraction interaction) {
        // Uncomment if you want to set the current date and time automatically
        // interaction.setInteractionDate(new Date());  // Current date and time
        return upcomingInteractionRepository.save(interaction);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds

    public void checkAndMoveOldInteractions() {
        // Get all interactions
        List<UpcomingInteraction> interactions = upcomingInteractionRepository.findAll();

        // Loop through each interaction and check if it is in the past
        for (UpcomingInteraction interaction : interactions) {
            // Parse the interaction date and time to a Date object
            Date interactionDate = parseDate(interaction.getInteractionDate(), interaction.getTime());

            // If the interaction date is in the past, move it to the old interactions table
            if (interactionDate != null && interactionDate.before(new Date())) {
                // Create a new OldInteraction object
                OldInteraction oldInteraction = new OldInteraction();
                oldInteraction.setSaleId(interaction.getSaleId());
                oldInteraction.setInteractionType(interaction.getInteractionType());
                oldInteraction.setInteractionDate(interaction.getInteractionDate());
                oldInteraction.setTime(interaction.getTime());
                oldInteraction.setNotes(interaction.getNotes());
                oldInteraction.setContactId(interaction.getContactId());
                // Add other fields if necessary

                // Save to the OldInteraction table
                oldInteractionRepository.save(oldInteraction);

                // Delete from the Interaction table
                upcomingInteractionRepository.delete(interaction);
            }
        }
    }

    private Date combineDateAndTime(Date date, Date time) {
        // Set time from the provided Date object (time) into the date object
        date.setHours(time.getHours());
        date.setMinutes(time.getMinutes());
        date.setSeconds(time.getSeconds());
        return date;
    }

    // Helper method to parse date and time strings into Date objects
    private Date parseDate(String dateStr, String timeStr) {
        try {
            // Handle null or empty date and time
            if (dateStr == null || timeStr == null || dateStr.trim().isEmpty() || timeStr.trim().isEmpty()) {
                return null; // Return null if date or time is invalid
            }

            // Debug: Log the date and time before parsing
            System.out.println(dateStr + "********" + timeStr);

            // Parse date and time separately
            Date date = dateFormat.parse(dateStr);
            Date time = timeFormat.parse(timeStr);  // Use 24-hour format for time parsing

            // Combine the date and time into a single Date object
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(time);

            // Set the hour and minute from the time and return the full Date object
            calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));

            return calendar.getTime();
        } catch (Exception e) {
            // Handle exception if parsing fails (e.g., incorrect format)
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
    public List<UpcomingInteraction> getAllInteractionsOrdered() {
        List<UpcomingInteraction> interactions = upcomingInteractionRepository.findAll();

        // Log the original order of interactions
        System.out.println("Original Order: ");
        interactions.forEach(i -> {
            System.out.println(i.getInteractionDate() + " " + i.getTime());
        });

        // Sort interactions by date and time in ascending order using Date objects
        interactions.sort((i1, i2) -> {
            Date date1 = parseDate(i1.getInteractionDate(), i1.getTime());
            Date date2 = parseDate(i2.getInteractionDate(), i2.getTime());

            // Compare the two dates
            if (date1 == null && date2 == null) {
                return 0; // Both are null, considered equal
            } else if (date1 == null) {
                return 1; // Null dates come last
            } else if (date2 == null) {
                return -1; // Null dates come last
            } else {
                return date1.compareTo(date2); // Compare by date and time
            }
        });

        // Log the sorted order of interactions
        System.out.println("Sorted Order: ");
        interactions.forEach(i -> {
            System.out.println(i.getInteractionDate() + " " + i.getTime());
        });

        return interactions;
    }
    public List<OldInteraction> getAllOldInteractionsOrdered() {
        List<OldInteraction> interactions = oldInteractionRepository.findAll();

        // Log the original order of interactions
        System.out.println("Original Order: ");
        interactions.forEach(i -> {
            System.out.println(i.getInteractionDate() + " " + i.getTime());
        });

        // Sort interactions by date and time in ascending order using Date objects
        interactions.sort((i1, i2) -> {
            Date date1 = parseDate(i1.getInteractionDate(), i1.getTime());
            Date date2 = parseDate(i2.getInteractionDate(), i2.getTime());

            // Compare the two dates
            if (date1 == null && date2 == null) {
                return 0; // Both are null, considered equal
            } else if (date1 == null) {
                return 1; // Null dates come last
            } else if (date2 == null) {
                return -1; // Null dates come last
            } else {
                return date1.compareTo(date2); // Compare by date and time
            }
        });

        // Log the sorted order of interactions
        System.out.println("Sorted Order: ");
        interactions.forEach(i -> {
            System.out.println(i.getInteractionDate() + " " + i.getTime());
        });

        return interactions;
    }

    public List<UpcomingInteraction> getUpcomingInteractionsByContactId(Long contactId) {
        return upcomingInteractionRepository.findByContactId(contactId);
    }
    public List<OldInteraction> getOldInteractionsByContactId(Long contactId) {
        return oldInteractionRepository.findByContactId(contactId);
    }

    public boolean deleteOldInteraction(Long interactionId) {
        // Check if the interaction exists in the database
        System.out.println(interactionId);
        if (oldInteractionRepository.existsById(interactionId)) {
            // Delete the interaction
            oldInteractionRepository.deleteById(interactionId);
            return true;
        }
        return false;
    }

    public boolean deleteUpcomingInteraction(Long interactionId) {
        // Check if the upcoming interaction exists in the database
        if (upcomingInteractionRepository.existsById(interactionId)) {
            // Delete the upcoming interaction
            upcomingInteractionRepository.deleteById(interactionId);
            return true;
        }
        return false;
    }


    public UpcomingInteraction updateUpcomingInteraction(Long interactionId, UpcomingInteraction updatedInteraction) {
        if (upcomingInteractionRepository.existsById(interactionId)) {
            updatedInteraction.setId(interactionId); // Set the ID of the existing interaction to ensure update
            return upcomingInteractionRepository.save(updatedInteraction); // Save updated interaction
        }
        return null; // If not found, return null
    }

}