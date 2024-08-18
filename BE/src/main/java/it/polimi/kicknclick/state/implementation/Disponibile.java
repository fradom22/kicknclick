package it.polimi.kicknclick.state.implementation;

import it.polimi.kicknclick.model.Prodotto;
import it.polimi.kicknclick.state.StateProdotto;

/**
 * Classe che definisce lo stato di un prodotto disponibile.
 */
public class Disponibile extends StateProdotto {

    /**
     * Costruttore della classe.
     * @param prodotto prodotto a cui associare lo stato.
     */
    public Disponibile(Prodotto prodotto) {
        super(prodotto);
    }

    /**
     * Metodo che permette di cambiare lo stato di un prodotto in disponibile.
     */
    @Override
    public void disponibile() {
        prodotto.cambiaStato(new Disponibile(prodotto));
    }

    /**
     * Metodo che permette di cambiare lo stato di un prodotto in venduto.
     */
    @Override
    public void venduto() {
        prodotto.cambiaStato(new Venduto(prodotto));
    }

    /**
     * Metodo che permette di cambiare lo stato di un prodotto in recensito.
     */
    @Override
    public void recensito() {
        prodotto.cambiaStato(new Recensito(prodotto));
    }
}
