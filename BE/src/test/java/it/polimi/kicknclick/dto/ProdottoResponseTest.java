package it.polimi.kicknclick.dto;

import it.polimi.kicknclick.dto.response.ProdottoResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdottoResponseTest {

    @Test
    void prodottoResponseTest() {
        ProdottoResponse response = new ProdottoResponse(1L, "Nike", "Air Max", 100.0, 42.0, new byte[0], "Nuovo");
        Long prodottoId = response.getProdottoId();
        String marca = response.getMarca();
        String modello = response.getModello();
        Double prezzo = response.getPrezzo();
        Double misura = response.getMisura();
        byte[] immagine = response.getImmagine();
        String stato = response.getStato();
    }
}
