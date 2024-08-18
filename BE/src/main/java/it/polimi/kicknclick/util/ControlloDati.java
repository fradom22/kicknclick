package it.polimi.kicknclick.util;

import it.polimi.kicknclick.exception.Exception;

import java.util.List;

/**
 * Classe che si occupa di controllare i dati inseriti dal client.
 * I metodi sono statici per permettere di utilizzarli senza dover istanziare l'oggetto.
 */
public class ControlloDati {

    /**
     * Metodo che controlla se le stringhe inserite sono vuote.
     * @param stringhe lista di stringhe da controllare
     */
    public static void controlloStringhe(List<String> stringhe) {

        for (String stringa : stringhe) {
            if (stringa.isBlank()) {
                throw new Exception("Inserire tutti i campi");
            }
        }
    }

    /**
     * Metodo che controlla se i numeri inseriti sono validi.
     * @param numeri lista di numeri da controllare
     */
    public static void controlloNumeri(List<Number> numeri) {

        for (Number n : numeri) {
            //Se il numero è un Integer, vuol dire che si tratta della valutazione di una recensione
            if (n instanceof Integer && (n.intValue() < 1 || n.intValue() > 5)) {
                throw new Exception("Numero non valido");

                //Se il numero è un Long, vuol dire che si tratta di un id
            } else if (n instanceof Long && n.longValue() < 1) {
                throw new Exception("Id non valido");
            }
        }
    }
}
