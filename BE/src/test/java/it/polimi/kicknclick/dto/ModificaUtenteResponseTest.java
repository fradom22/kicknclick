package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.ModificaUtenteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ModificaUtenteResponseTest {

    @Test
    void modificaUtenteResponseTest() {
        ModificaUtenteResponse response = new ModificaUtenteResponse(1L, "nome", "cognome", "username", "ruolo");
        Long utenteId = response.getUtenteId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String username = response.getUsername();
        String ruolo = response.getRuolo();
    }
}
