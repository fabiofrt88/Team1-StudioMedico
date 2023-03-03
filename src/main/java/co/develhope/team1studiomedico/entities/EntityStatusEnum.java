package co.develhope.team1studiomedico.entities;

/**
 * L'enumerato EntityStatusEnum presenta gli stati che un record entità puà assumere
 * e cambiare dalla creazione alla cancellazione logica, con eventuale ripristino
 */
public enum EntityStatusEnum {

    /**
     * Stato ATTIVO.
     */
    ACTIVE('A'),
    /**
     * Stato CANCELLATO.
     */
    DELETED('D');

    private final Character status;

    /**
     *  Costruttore EntityStatusEnum.
     * @param status il parametro status.
     */
    EntityStatusEnum(Character status) {
        this.status = status;
    }

    /**
     * Restituisce lo status.
     *
     * @return lo status
     */
    public Character getStatus() {
        return status;
    }

}
