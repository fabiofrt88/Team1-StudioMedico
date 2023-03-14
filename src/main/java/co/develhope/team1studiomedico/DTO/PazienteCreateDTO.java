package co.develhope.team1studiomedico.DTO;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;

import java.time.LocalDate;

public class PazienteCreateDTO {

    private final Long id;

    private final String nome;

    private final String cognome;

    private final String telefono;

    private final String email;

    private final LocalDate dataNascita;

    private final String codiceFiscale;

    private final EntityStatusEnum recordStatus;

    private final Long medicoId;


    /**
     * Costruttore parametrico che istanzia una nuova entità Paziente.
     *
     * @param id             id
     * @param nome           nome
     * @param cognome        cognome
     * @param telefono       telefono
     * @param email          email
     * @param dataNascita    data nascita
     * @param codiceFiscale  codice fiscale
     * @param medicoId         medico id
     */

    public PazienteCreateDTO(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, EntityStatusEnum recordStatus, Long medicoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.recordStatus = recordStatus;
        this.medicoId = medicoId;
    }

    /**
     * Metodo che restituisce l'id.
     *
     * @return l' id
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo che restituisce il nome.
     *
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che restituisce il cognome.
     *
     * @return il cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che restituisce il numero di telefono.
     *
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo che restituisce l'email.
     *
     * @return l' email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo che restituisce lo status.
     *
     * @return lo status
     */
    public EntityStatusEnum getRecordStatus() {
        return recordStatus;
    }

    /**
     * Metodo che restituisce la data nascita.
     *
     * @return la data nascita
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * Metodo che restituisce il codice fiscale.
     *
     * @return il codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Metodo che restituisce l'id del medico.
     *
     * @return medicoId il medico id
     */
    public Long getMedicoId() {
        return medicoId;
    }

}
