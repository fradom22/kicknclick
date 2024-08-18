package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.RecensioneResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RecensioneResponseTest {

    @Test
    void recensioneResponseTest() {
        RecensioneResponse response = new RecensioneResponse(1, "testo", new byte[0]);
        Integer votoRecensione = response.getVotoRecensione();
        String testoRecensione = response.getTestoRecensione();
        byte[] immagine = response.getImmagine();
    }
}
