package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.UtenteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UtenteResponseTest {

    @Test
    void utenteResponseTest() {
        UtenteResponse utenteResponse = new UtenteResponse("username", "nome", "cognome");
        String username = utenteResponse.getUsername();
        String nome = utenteResponse.getNome();
        String cognome = utenteResponse.getCognome();
    }
}
