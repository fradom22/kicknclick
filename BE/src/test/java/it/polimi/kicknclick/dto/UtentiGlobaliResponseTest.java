package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.UtentiGlobaliResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UtentiGlobaliResponseTest {

    @Test
    void utentiGlobaliResponseTest() {
        UtentiGlobaliResponse response = new UtentiGlobaliResponse(1L, "Mario", "Rossi", "Ruolo");
        Long utenteId = response.getUtenteId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String ruolo = response.getRuolo();
    }
}
