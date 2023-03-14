package co.develhope.team1studiomedico.dto;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;

import java.time.LocalDate;

public class PazienteDTO {

    private Long id;

    private String nome;

    private String cognome;

    private String telefono;

    private String email;

    private LocalDate dataNascita;

    private String codiceFiscale;

    private EntityStatusEnum recordStatus;

    private Long medicoId;

    /**
     * Costruttore di default di PersonaEntity.
     */
    public PazienteDTO (){ }

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

    public PazienteDTO(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, EntityStatusEnum recordStatus, Long medicoId) {
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
     * Metodo che setta l'id.
     *
     * @param id l' id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Metodo che setta il nome.
     *
     * @param nome il nome
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * Metodo che setta il cognome.
     *
     * @param cognome il cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
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
     * Metodo che setta il numero di telefono.
     *
     * @param telefono il telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * Metodo che setta l'email.
     *
     * @param email l' email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * Metodo che setta lo status.
     *
     * @param recordStatus lo status
     */
    public void setRecordStatus(EntityStatusEnum recordStatus) {
        this.recordStatus = recordStatus;
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
     * Metodo che setta la data nascita.
     *
     * @param dataNascita la data nascita
     */
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
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
     * Metodo che setta il codice fiscale.
     *
     * @param codiceFiscale il codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Metodo che restituisce l'id del medico.
     *
     * @return medicoId il medico id
     */
    public Long getMedicoId() {
        return medicoId;
    }

    /**
     * Metodo che setta l'id del medico.
     *
     * @param medicoId il medico id
     */
    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }
}
