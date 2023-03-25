package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * La classe SegretarioCreateDTO rappresenta il DTO (Data Transfer Object) di update e lettura di SegretarioEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata (update) e uscita (lettura) mediante i quali sarà possibile
 * rispettivamente modificare un segretario e visualizzare una selezione dei dati di un segretario dal database
 */
public class SegretarioDTO {

    private Long id;
    @Size(min = 2, max = 25, message = "nome non valido: deve essere compreso tra i 2 - 25 caratteri")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "nome non valido: non segue gli standard di validazione")
    private String nome;
    @Size(min = 2, max = 25, message = "cognome non valido: deve essere compreso tra 2 - 25 caratteri")
    @Pattern(regexp = "^[a-zA-Z']+(?:\\s[a-zA-Z']+)*$", message = "cognome non valido: non segue gli standard di validazione")
    private String cognome;
    @Size(min = 8, max = 16, message = "nome non valido: deve essere compreso tra 8 - 16 caratteri")
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[4-90]|36[680]|33[13-90]|32[89]|35[01]|37[019])\\d{6,7}$",
            message = "telefono non valido: non segue gli standard di validazione")
    private String telefono;
    @Pattern(regexp = "^(?=.{1,32}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$",
            message = "email non valida: non segue gli standard di validazione")
    private String email;
    private MedicoDTO medico;

    /**
     * Costruttore di default che istanzia un nuovo SegretarioDTO.
     */
    public SegretarioDTO(){ }

    /**
     * Costruttore parametrico che istanzia un nuovo SegretarioDTO
     *
     * @param id        id segretario
     * @param nome      nome segretario
     * @param cognome   cognome segretario
     * @param email     email segretario
     * @param telefono  telefono segretario
     * @param medico    medico di riferimento
     */
    public SegretarioDTO(Long id, String nome, String cognome, String telefono, String email, MedicoDTO medico) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.medico = medico;
    }

    /**
     * Metodo che restituisce l'id del segretario.
     *
     * @return l'id del segretario
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodo che setta l'id del segretario.
     *
     * @param id l'id del segretario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo che restituisce il nome del segretario.
     *
     * @return Il nome del segretario
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che setta il nome del segretario.
     *
     * @param nome il nome del segretario.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che restituisce il cognome del segretario.
     *
     * @return Il cognome del segretario
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che setta il cognome del segretario
     *
     * @param cognome il cognome del segretario.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Metodo che restituisce l'email del segretario.
     *
     * @return L'email del segretario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo che setta l'email del segretario.
     *
     * @param email l'email del segretario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metodo che restituisce il telefono.
     *
     * @return Il telefono del segretario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo che setta il telefono del segretario.
     *
     * @param telefono il telefono del segretario.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Metodo che restituisce l'id del medico di riferimento.
     *
     * @return medico di riferimento
     */
    public MedicoDTO getMedico() {
        return medico;
    }

    /**
     * Metodo che setta l'id del medico di riferimento.
     *
     * @param medico medico di riferimento
     */
    public void setMedico(MedicoDTO medico) {
        this.medico = medico;
    }

}
