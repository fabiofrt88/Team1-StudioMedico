package co.develhope.team1studiomedico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * La classe NotFoundException è un unchecked exception poichè sottoclasse di RuntimeException,
 * tale eccezione custom si verifica nel caso in cui una data risorsa non viene trovata nel database,
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Costruttore parametrico che istanzia un'eccezione NotFoundExcpetion
     * @param message messaggio di errore
     */
    public NotFoundException(String message) {
        super(message);
    }

}
