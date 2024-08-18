package it.polimi.kicknclick.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller per la gestione delle eccezioni customizzate.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * Metodo per la gestione delle eccezioni.
     *
     * @param e eccezione da gestire
     * @return ResponseEntity con la risposta dell'eccezione
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionModel> handleException(Exception e) {

        ExceptionModel customException = new ExceptionModel(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }
}
