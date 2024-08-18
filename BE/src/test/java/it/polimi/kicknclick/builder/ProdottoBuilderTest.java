package it.polimi.kicknclick.builder;

import it.polimi.kicknclick.model.Acquisto;
import it.polimi.kicknclick.model.Prodotto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProdottoBuilderTest {

    @Test
    void prodottoBuilderTest() {
        Prodotto prodotto = new ProdottoBuilder()
                .prodottoId(1L)
                .acquisto(new Acquisto())
                .build();
    }
}
