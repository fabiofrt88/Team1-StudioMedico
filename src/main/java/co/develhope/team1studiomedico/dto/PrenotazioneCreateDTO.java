package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * La classe PrenotazioneCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di PrenotazioneEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare una nuova prenotazione nel database
 */
public class PrenotazioneCreateDTO {

    private final Long id;
    @NotNull(message = "il campo data prenotazione è obbligatorio")
    @FutureOrPresent(message = "data prenotazione non valida: deve essere diversa da una data passata")
    private final LocalDate dataPrenotazione;
    @NotNull(message = "il campo ora prenotazione è obbligatorio")
    private final LocalTime oraPrenotazione;
    @NotNull(message = "il campo foreign key medicoId è obbligatorio")
    private final Long medicoId;
    @NotNull(message = "il campo foreign key pazienteId è obbligatorio")
    private final Long pazienteId;

    /**
     * Costruttore parametrico che istanzia una nuovo PrenotazioneCreateDTO
     *
     * @param id               id prenotazione
     * @param dataPrenotazione data prenotazione
     * @param oraPrenotazione  ora prenotazione
     * @param pazienteId       id del paziente
     * @param medicoId         id del medico
     */
    public PrenotazioneCreateDTO(Long id, LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long medicoId, Long pazienteId) {
        this.id = id;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.medicoId = medicoId;
        this.pazienteId = pazienteId;
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
     * Metodo che restituisce la data prenotazione.
     *
     * @return la data prenotazione
     */
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    /**
     * Metodo che restituisce l'ora prenotazione.
     *
     * @return l'ora prenotazione.
     */
    public LocalTime getOraPrenotazione() {
        return oraPrenotazione;
    }

    /**
     * Metodo che restituisce l'id del medico.
     *
     * @return l'id del medico
     */
    public Long getMedicoId() {
        return medicoId;
    }

    /**
     * Metodo che restituisce l'id del paziente.
     *
     * @return l'id del paziente
     */
    public Long getPazienteId() {
        return pazienteId;
    }

}
