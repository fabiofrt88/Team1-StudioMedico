package co.develhope.team1studiomedico.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * La classe PazienteDTO rappresenta il DTO (Data Transfer Object) di update e lettura di PazienteEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata (update) e uscita (lettura) mediante i quali sarà possibile
 * rispettivamente modificare un paziente e restituire una selezione dei dati di un paziente nel payload delle response
 */
public class PazienteDTO {

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
    @Past(message = "data nascita non valida: deve essere diversa da quella attuale o da una data futura")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascita;
    @Pattern(regexp = "^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T]" +
            "(?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T]" +
            "[26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L]" +
            "(?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$", message = "codice fiscale non valido: non segue gli standard di validazione")
    private String codiceFiscale;
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
     * @param medicoId       medico id
     */

    public PazienteDTO(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, Long medicoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
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
