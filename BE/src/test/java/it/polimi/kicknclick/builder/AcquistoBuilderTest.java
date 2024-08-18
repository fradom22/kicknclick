package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AcquistoBuilderTest {

    @Test
    void acquistoBuilderTest() {
        Acquisto acquisto = new AcquistoBuilder()
                .acquistoId(1L)
                .testoRecensione("testo")
                .votoRecensione(4)
                .nomeImmagine("nome")
                .tipoImmagine("tipo")
                .percorsoImmagine("percorso")
                .build();
    }
}
