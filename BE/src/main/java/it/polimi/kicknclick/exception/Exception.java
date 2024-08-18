package it.polimi.kicknclick.exception;

/**
 * Classe per la gestione delle eccezioni personalizzate.
 * Estende la classe RuntimeException.
 */
public class Exception extends RuntimeException {

    public Exception(String message) {

        super(message);
    }
}
