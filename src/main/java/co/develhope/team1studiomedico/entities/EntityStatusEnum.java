package co.develhope.team1studiomedico.entities;
/**
 * L'enumerato Entity status enum.
 */
public enum EntityStatusEnum {

    /**
     * Attiva entity status enum.
     */
    ACTIVE('A'),
    /**
     * Cancella entity status enum.
     */
    DELETED('D');

    private final Character status;

    /**
     *  Costruttore.
     * @param status che passa il parametro status.
     */
    EntityStatusEnum(Character status) {
        this.status = status;
    }

    /**
     * Ottiene lo status.
     *
     * @return lo status
     */
    public Character getStatus() {
        return status;
    }

}
