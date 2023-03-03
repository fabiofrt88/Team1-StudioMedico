package co.develhope.team1studiomedico.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ExceptionController è uno speciale controller che consente di gestire le eccezioni
 * nell'intera applicazione in un unico componente di gestione globale mediante relativi metodi @ExceptionHandler.
 * Può essere visto come un interceptor di eccezioni lanciate da metodi annotati con @RequestMapping e simili.
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * Metodo che gestisce le eccezioni EntityNotFoundException
     * @param e oggetto eccezione di tipo EntityNotFoundException
     * @return response con status di errore 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFoundException(EntityNotFoundException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Metodo che gestisce le eccezioni IllegalArgumentException
     * @param e oggetto eccezione di tipo IllegalArgumentException
     * @return response con status di errore 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
