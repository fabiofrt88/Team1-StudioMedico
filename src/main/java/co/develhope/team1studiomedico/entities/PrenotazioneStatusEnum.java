package co.develhope.team1studiomedico.entities;

/**
 * L'enumerato PrenotazioneStatusEnum presenta gli stati che una prenotazione può assumere
 * e cambiare dalla creazione alla conferma piuttosto cancellazione da parte del segretario
 */
public enum PrenotazioneStatusEnum {

    /**
     * Stato PENDING.
     */
    PENDING("Pending"),
    /**
     * Stato CONFIRMED.
     */
    CONFIRMED("Confirmed"),
    /**
     * Stato CANCELLED.
     */
    CANCELLED("Cancelled"),
    /**
     * Stato REJECTED.
     */
    REJECTED("Rejected");

    private final String statoPrenotazione;

    /**
     * Costruttore PrenotazioneStatusEnum.
     * @param statoPrenotazione lo status della prenotazione.
     */
    PrenotazioneStatusEnum(String statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    /**
     * Metodo che restituisce lo status della prenotazione.
     *
     * @return lo status della prenotazione
     */
    public String getStatoPrenotazione() {
        return statoPrenotazione;
    }

}
