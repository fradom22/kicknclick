package it.polimi.kicknclick.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Model per la gestione delle eccezioni personalizzate.
 */
@Getter
@AllArgsConstructor
public class ExceptionModel {

    private String message;

    private HttpStatus status;
}
