package it.polimi.kicknclick.state;

import it.polimi.kicknclick.model.Prodotto;

/**
 * Classe astratta che rappresenta lo stato di un prodotto.
 * Pattern State.
 */
public abstract class StateProdotto {

    // Riferimento al prodotto che contiene lo stato
    protected Prodotto prodotto;

    // Costruttore che inizializza il prodotto
    protected StateProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    //Metodi astratti che verranno implementati dalle sottoclassi
    public abstract void disponibile();

    public abstract void venduto();

    public abstract void recensito();
}
