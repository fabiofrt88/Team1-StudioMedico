package co.develhope.team1studiomedico.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * La classe MedicoDTO rappresenta il DTO del MedicoEntity.
 */
public class MedicoDTO {

    @JsonProperty("prenotazioni")
    private List<Long> prenotazioniId;
    @JsonProperty("pazienti")
    private List<Long> pazientiId;

    /**
     * Costruttore di default che istanzia un nuovo MedicoDTO.
     */
    public MedicoDTO(){ }



    public MedicoDTO(List<Long> prenotazioniId, List<Long> pazientiId) {
        this.prenotazioniId = prenotazioniId;
        this.pazientiId = pazientiId;
    }

    /**
     * Metodo che restituisce gli id delle prenotazioni.
     *
     * @return gli id delle prenotazioni
     */
    public List<Long> getPrenotazioniId() {
        return prenotazioniId;
    }

    /**
     * Metodo che setta gli id delle prenotazioni.
     *
     * @param prenotazioniId gli id delle prenotazioni
     */
    public void setPrenotazioniId(List<Long> prenotazioniId) {
        this.prenotazioniId = prenotazioniId;
    }

    /**
     * Metodo che restituisce gli id dei pazienti associati.
     *
     * @return gli id dei pazienti
     */
    public List<Long> getPazientiId() {
        return pazientiId;
    }

    /**
     * Metodo che setta gli id dei pazienti associati.
     *
     * @param pazientiId gli id dei pazienti
     */
    public void setPazientiId(List<Long> pazientiId) {
        this.pazientiId = pazientiId;
    }
}

