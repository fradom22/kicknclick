package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.model.Utente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UtenteBuilderTest {

    @Test
    void utenteBuilderTest() {
        Utente utente = new UtenteBuilder()
                .acquisti(List.of(new Acquisto()))
                .prodottiPubblicati(List.of(new Prodotto()))
                .build();
    }
}
