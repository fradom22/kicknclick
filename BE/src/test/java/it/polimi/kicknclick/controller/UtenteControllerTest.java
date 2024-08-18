package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.service.UtenteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class UtenteControllerTest {

    @Mock
    UtenteService utenteService;
    @InjectMocks
    UtenteController utenteController;

    @Test
    void get() {
        assertAll(() -> utenteController.get("1"));
    }

    @Test
    void modifica() {
        assertAll(() -> utenteController.modifica(null));
    }

    @Test
    void elimina() {
        assertAll(() -> utenteController.elimina("1"));
    }

    @Test
    void getAll() {
        assertAll(() -> utenteController.getAll());
    }
}
