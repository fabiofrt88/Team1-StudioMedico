package co.develhope.team1studiomedico.dto;

import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * La classe PrenotazioneDTO rappresenta il DTO (Data Transfer Object) di update e lettura di PrenotazioneEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata (update) e uscita (lettura) mediante i quali sarà possibile
 * rispettivamente modificare una prenotazione e restituire una selezione dei dati di una prenotazione nel payload delle response
 */
public class PrenotazioneDTO {

    private Long id;
    private LocalDateTime bookedAt;
    private LocalDate dataPrenotazione;
    private LocalTime oraPrenotazione;
    private PrenotazioneStatusEnum statoPrenotazione;
    private PazienteDTO paziente;
    private MedicoDTO medico;

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
     * @param paziente         paziente
     * @param medico           medico
     */
    public PrenotazioneDTO(Long id, LocalDateTime bookedAt, LocalDate dataPrenotazione, LocalTime oraPrenotazione,
                           PrenotazioneStatusEnum statoPrenotazione, PazienteDTO paziente, MedicoDTO medico) {
        this.id = id;
        this.bookedAt = bookedAt;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.statoPrenotazione = statoPrenotazione;
        this.paziente = paziente;
        this.medico = medico;
    }

    /**
     * Metodo che restituisce l'id del paziente.
     *
     * @return l'id del paziente
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo che setta l'id del paziente.
     *
     * @param id l'id del paziente
     */
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
     *
     * @param bookedAt il timestamp prenotazione
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
     *
     * @param dataPrenotazione the data prenotazione
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
     *
     * @param oraPrenotazione l'ora prenotazione
     */
    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
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
     *
     * @param statoPrenotazione lo stato prenotazione
     */
    public void setStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    /**
     * Metodo che restituisce il paziente.
     *
     * @return il medico
     */
    public PazienteDTO getPaziente() {
        return paziente;
    }

    /**
     * Metodo che setta il paziente.
     *
     * @param paziente il paziente
     */
    public void setPaziente(PazienteDTO paziente) {
        this.paziente = paziente;
    }

    /**
     * Metodo che restituisce il medico.
     *
     * @return il medico
     */
    public MedicoDTO getMedico() {
        return medico;
    }

    /**
     * Metodo che setta il medico.
     *
     * @param medico il medico
     */
    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

}
