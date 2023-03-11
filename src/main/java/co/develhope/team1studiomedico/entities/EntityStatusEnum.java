package co.develhope.team1studiomedico.entities;

/**
 * L'enumerato EntityStatusEnum presenta gli stati che un record entità può assumere
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

    private final Character recordStatus;

    /**
     * Costruttore EntityStatusEnum.
     * @param recordStatus il parametro status.
     */
    EntityStatusEnum(Character recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     * Metodo che restituisce lo status.
     *
     * @return lo status
     */
    public Character getRecordStatus() {
        return recordStatus;
    }

}
