package co.develhope.team1studiomedico.exceptions;

/**
 * La classe EntityStatusException rappresenta un unchecked exception poichè sottoclasse di RuntimeException,
 * tale eccezione custom si verifica nel caso in cui nelle operazioni di cancellazione logica e ripristino
 * una data risorsa risulta essere già cancellata logicamente piuttosto con status ACTIVE
 */
public class EntityStatusException extends RuntimeException {

    /**
     * Costruttore parametrico che istanzia un'eccezione EntityStatusException
     * @param message messaggio di errore
     */
    public EntityStatusException(String message) {
        super(message);
    }

}
