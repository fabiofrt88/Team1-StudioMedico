package co.develhope.team1studiomedico.dto;

import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * La classe PrenotazioneCreateDTO rappresenta il DTO (Data Transfer Object) di update e lettura di PrenotazioneEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata (update) e uscita (lettura) mediante i quali sarà possibile
 * rispettivamente modificare una prenotazione e visualizzare una selezione dei dati di una prenotazione dal database
 */
public class PrenotazioneDTO {

    private Long id;
    private LocalDateTime bookedAt;
    private LocalDate dataPrenotazione;
    private LocalTime oraPrenotazione;
    private Long medicoId;
    private Long pazienteId;
    private PrenotazioneStatusEnum statoPrenotazione;

    /**
     * Costruttore di default che istanzia una nuovo PrenotazioneDTO.
     */
    public PrenotazioneDTO() { }

    /**
     * Costruttore parametrico che istanzia una nuovo PrenotazioneDTO
     *
     * @param id               id prenotazione
     * @param bookedAt         timestamp prenotazione
     * @param dataPrenotazione data prenotazione
     * @param oraPrenotazione  ora prenotazione
     * @param pazienteId       id del paziente
     * @param medicoId         id del medico
     */
    public PrenotazioneDTO(Long id, LocalDateTime bookedAt, LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long medicoId, Long pazienteId, PrenotazioneStatusEnum statoPrenotazione) {
        this.id = id;
        this.bookedAt = bookedAt;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.medicoId = medicoId;
        this.pazienteId = pazienteId;
        this.statoPrenotazione = statoPrenotazione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo che restituisce il booked at.
     *
     * @return il timestamp prenotazione
     */
    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    /**
     * Metodo che setta il timestamp prenotazione.
     */
    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }

    /**
     * Metodo che restituisce la data prenotazione.
     *
     * @return il timestamp prenotazione
     */
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    /**
     * Metodo che setta la data prenotazione.
     */
    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    /**
     * Metodo che restituisce l'ora prenotazione.
     *
     * @return il booked at
     */
    public LocalTime getOraPrenotazione() {
        return oraPrenotazione;
    }

    /**
     * Metodo che setta l'ora prenotazione.
     */
    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    /**
     * Metodo che restituisce l'id del medico.
     *
     * @return il booked at
     */
    public Long getMedicoId() {
        return medicoId;
    }

    /**
     * Metodo che setta l'id del medico.
     */
    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    /**
     * Metodo che restituisce l'id del paziente.
     *
     * @return id del paziente
     */
    public Long getPazienteId() {
        return pazienteId;
    }

    /**
     * Metodo che setta l'id del paziente.
     */
    public void setPazienteId(Long pazienteId) {
        this.pazienteId = pazienteId;
    }

    /**
     * Metodo che restituisce lo stato prenotazione.
     *
     * @return stato prenotazione
     */
    public PrenotazioneStatusEnum getStatoPrenotazione() {
        return statoPrenotazione;
    }

    /**
     * Metodo che setta lo stato prenotazione.
     */
    public void setStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

}
