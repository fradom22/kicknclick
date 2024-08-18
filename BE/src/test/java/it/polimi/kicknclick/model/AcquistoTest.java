package it.polimi.kicknclick.model;

import it.polimi.kicknclick.builder.AcquistoBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension .class)
public class AcquistoTest {

    @Test
    void acquistoTest() {
        Acquisto a = new AcquistoBuilder()
                .acquistoId(1L)
                .data(LocalDateTime.now())
                .nomeImmagine("nomeImmagine")
                .tipoImmagine("tipoImmagine")
                .utente(new Utente())
                .build();
        Long acquistoId = a.getAcquistoId();
        LocalDateTime data = a.getData();
        String nomeImmagine = a.getNomeImmagine();
        String tipoImmagine = a.getTipoImmagine();
        Utente utente = a.getUtente();
    }
}
