package co.develhope.team1studiomedico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * La classe ResourceNotFoundException rappresenta un unchecked exception poichè sottoclasse di RuntimeException,
 * tale eccezione custom si verifica nel caso in cui una data risorsa non viene trovata nel database.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Costruttore parametrico che istanzia un'eccezione ResourceNotFoundException
     * @param message messaggio di errore
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
