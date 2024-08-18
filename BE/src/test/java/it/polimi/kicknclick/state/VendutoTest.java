package it.polimi.kicknclick.state;

import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.state.implementation.Venduto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
public class VendutoTest {

    @InjectMocks
    Venduto venduto;

    @Test
    void vendutoTest() {
        Prodotto p = new Prodotto();
        venduto = new Venduto(p);
        assertAll(() -> venduto.venduto());
    }

    @Test
    void recensitoTest() {
        Prodotto p = new Prodotto();
        venduto = new Venduto(p);
        assertAll(() -> venduto.recensito());
    }

    @Test
    void disponibileTest() {
        Prodotto p = new Prodotto();
        venduto = new Venduto(p);
        assertAll(() -> venduto.disponibile());
    }
}
