package com.example.CRM;

import com.example.CRM.controller.InteractionController;
import com.example.CRM.entity.OldInteraction;
import com.example.CRM.entity.UpcomingInteraction;
import com.example.CRM.service.InteractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class InteractionControllerTest {

    @Mock
    private InteractionService interactionService;

    @InjectMocks
    private InteractionController interactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInteraction() {
        UpcomingInteraction interaction = new UpcomingInteraction();
        interaction.setId(1L);

        when(interactionService.addInteraction(interaction)).thenReturn(interaction);

        ResponseEntity<UpcomingInteraction> response = interactionController.addInteraction(interaction);

        assertEquals(interaction, response.getBody());
        verify(interactionService, times(1)).addInteraction(interaction);
    }

    @Test
    void testGetAllUpcomingInteractions() {
        List<UpcomingInteraction> interactions = new ArrayList<>();
        interactions.add(new UpcomingInteraction());
        interactions.add(new UpcomingInteraction());

        when(interactionService.getAllInteractionsOrdered()).thenReturn(interactions);

        ResponseEntity<List<UpcomingInteraction>> response = interactionController.getAllUpcomingInteractions();

        assertEquals(2, response.getBody().size());
        verify(interactionService, times(1)).getAllInteractionsOrdered();
    }

    @Test
    void testGetAllOldInteractions() {
        List<OldInteraction> interactions = new ArrayList<>();
        interactions.add(new OldInteraction());
        interactions.add(new OldInteraction());

        when(interactionService.getAllOldInteractionsOrdered()).thenReturn(interactions);

        ResponseEntity<List<OldInteraction>> response = interactionController.getAllOldInteractions();

        assertEquals(2, response.getBody().size());
        verify(interactionService, times(1)).getAllOldInteractionsOrdered();
    }

    @Test
    void testGetUpcomingInteractionsByContactId_Found() {
        List<UpcomingInteraction> interactions = new ArrayList<>();
        interactions.add(new UpcomingInteraction());

        when(interactionService.getUpcomingInteractionsByContactId(1L)).thenReturn(interactions);

        ResponseEntity<List<UpcomingInteraction>> response = interactionController.getUpcomingInteractionsByContactId(1L);

        assertEquals(1, response.getBody().size());
        verify(interactionService, times(1)).getUpcomingInteractionsByContactId(1L);
    }

    @Test
    void testGetUpcomingInteractionsByContactId_NotFound() {
        when(interactionService.getUpcomingInteractionsByContactId(1L)).thenReturn(new ArrayList<>());

        ResponseEntity<List<UpcomingInteraction>> response = interactionController.getUpcomingInteractionsByContactId(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(interactionService, times(1)).getUpcomingInteractionsByContactId(1L);
    }

    @Test
    void testGetOldInteractionsByContactId_Found() {
        List<OldInteraction> interactions = new ArrayList<>();
        interactions.add(new OldInteraction());

        when(interactionService.getOldInteractionsByContactId(1L)).thenReturn(interactions);

        ResponseEntity<List<OldInteraction>> response = interactionController.getInteractionsByContactId(1L);

        assertEquals(1, response.getBody().size());
        verify(interactionService, times(1)).getOldInteractionsByContactId(1L);
    }

    @Test
    void testGetOldInteractionsByContactId_NotFound() {
        when(interactionService.getOldInteractionsByContactId(1L)).thenReturn(new ArrayList<>());

        ResponseEntity<List<OldInteraction>> response = interactionController.getInteractionsByContactId(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(interactionService, times(1)).getOldInteractionsByContactId(1L);
    }

    @Test
    void testDeleteOldInteraction_Found() {
        when(interactionService.deleteOldInteraction(1L)).thenReturn(true);

        ResponseEntity<String> response = interactionController.deleteOldInteraction(1L);

        assertEquals("Interaction deleted successfully.", response.getBody());
        verify(interactionService, times(1)).deleteOldInteraction(1L);
    }

    @Test
    void testDeleteOldInteraction_NotFound() {
        when(interactionService.deleteOldInteraction(1L)).thenReturn(false);

        ResponseEntity<String> response = interactionController.deleteOldInteraction(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Interaction not found.", response.getBody());
        verify(interactionService, times(1)).deleteOldInteraction(1L);
    }

    @Test
    void testDeleteUpcomingInteraction_Found() {
        when(interactionService.deleteUpcomingInteraction(1L)).thenReturn(true);

        ResponseEntity<String> response = interactionController.deleteUpcomingInteraction(1L);

        assertEquals("Upcoming interaction deleted successfully.", response.getBody());
        verify(interactionService, times(1)).deleteUpcomingInteraction(1L);
    }

    @Test
    void testDeleteUpcomingInteraction_NotFound() {
        when(interactionService.deleteUpcomingInteraction(1L)).thenReturn(false);

        ResponseEntity<String> response = interactionController.deleteUpcomingInteraction(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Upcoming interaction not found.", response.getBody());
        verify(interactionService, times(1)).deleteUpcomingInteraction(1L);
    }

    @Test
    void testUpdateUpcomingInteraction_Found() {
        UpcomingInteraction interaction = new UpcomingInteraction();
        interaction.setId(1L);

        when(interactionService.updateUpcomingInteraction(1L, interaction)).thenReturn(interaction);

        ResponseEntity<UpcomingInteraction> response = interactionController.updateUpcomingInteraction(1L, interaction);

        assertEquals(interaction, response.getBody());
        verify(interactionService, times(1)).updateUpcomingInteraction(1L, interaction);
    }

    @Test
    void testUpdateUpcomingInteraction_NotFound() {
        UpcomingInteraction interaction = new UpcomingInteraction();

        when(interactionService.updateUpcomingInteraction(1L, interaction)).thenReturn(null);

        ResponseEntity<UpcomingInteraction> response = interactionController.updateUpcomingInteraction(1L, interaction);

        assertNull(response.getBody());
        assertEquals(404, response.getStatusCodeValue());
        verify(interactionService, times(1)).updateUpcomingInteraction(1L, interaction);
    }
}