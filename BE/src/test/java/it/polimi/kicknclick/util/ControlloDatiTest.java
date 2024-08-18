package it.polimi.kicknclick.util;

import it.polimi.kicknclick.exception.Exception;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ControlloDatiTest {

    @Test
    void controlloStringheThrowsInserireTuttiICampi() {
        assertThrows(Exception.class, () -> ControlloDati.controlloStringhe(List.of("")));
    }

    @Test
    void controlloNumeriThrowsNumeroNonValido() {
        assertThrows(Exception.class, () -> ControlloDati.controlloNumeri(List.of(6)));
    }

    @Test
    void controlloNumeriThrowsIdNonValido() {
        assertThrows(Exception.class, () -> ControlloDati.controlloNumeri(List.of(0L)));
    }
}
