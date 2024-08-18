package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.ProdottoBuilder;
import it.polimi.kicknclick.state.StateProdotto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdottoTest {

    @Test
    void prodottoTest() {
        Prodotto p = new ProdottoBuilder()
                .nomeImmagine("nome")
                .tipoImmagine("tipo")
                .percorsoImmagine("percorso")
                .utente(new Utente())
                .acquisto(new Acquisto())
                .build();
        String nomeImmagine = p.getNomeImmagine();
        String tipoImmagine = p.getTipoImmagine();
        String percorsoImmagine = p.getPercorsoImmagine();
        Utente utente = p.getUtente();
        Acquisto acquisto = p.getAcquisto();
        StateProdotto stato = p.getStato();
    }
}
