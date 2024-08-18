package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.LoginResponse;
import it.polimi.kicknclick.model.Ruolo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginResopnseTest {

    @Test
    void loginResponseTest() {
        LoginResponse response = new LoginResponse(1L, "nome", "cognome", "username", Ruolo.CUSTOMER, "jwt");
        Long utenteId = response.getUtenteId();
        String nome = response.getNome();
        String cognome = response.getCognome();
        String username = response.getUsername();
        Ruolo ruolo = response.getRuolo();
        String jwt = response.getJwt();
    }
}
