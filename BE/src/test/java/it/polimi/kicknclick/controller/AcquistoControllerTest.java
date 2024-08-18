package it.polimi.kicknclick.controller;

import it.polimi.kicknclick.service.AcquistoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class AcquistoControllerTest {

    @Mock
    AcquistoService acquistoService;
    @InjectMocks
    AcquistoController acquistoController;

    @Test
    void acquista() {
        assertAll(() -> acquistoController.acquista(null));
    }

    @Test
    void recensione() {
        assertAll(() -> acquistoController.recensione(null, null, null, null, null));
    }

    @Test
    void visualizzaRecensione() {
        assertAll(() -> acquistoController.visualizzaRecensione(null));
    }
}
