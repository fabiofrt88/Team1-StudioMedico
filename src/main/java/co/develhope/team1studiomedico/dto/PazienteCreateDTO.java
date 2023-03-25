package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * La classe PazienteCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di PazienteEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare un nuovo paziente nel database
 */
public class PazienteCreateDTO {

    private final Long id;
    @NotBlank(message = "il campo nome è obbligatorio")
    @Size(min = 2, max = 25, message = "nome non valido: deve essere compreso tra i 2 - 25 caratteri")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "nome non valido: non segue gli standard di validazione")
    private final String nome;
    @NotBlank(message = "il campo cognome è obbligatorio")
    @Size(min = 2, max = 25, message = "cognome non valido: deve essere compreso tra 2 - 25 caratteri")
    @Pattern(regexp = "^[a-zA-Z']+(?:\\s[a-zA-Z']+)*$", message = "cognome non valido: non segue gli standard di validazione")
    private final String cognome;
    @NotBlank(message = "il campo telefono è obbligatorio")
    @Size(min = 8, max = 16, message = "nome non valido: deve essere compreso tra 8 - 16 caratteri")
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[4-90]|36[680]|33[13-90]|32[89]|35[01]|37[019])\\d{6,7}$",
            message = "telefono non valido: non segue gli standard di validazione")
    private final String telefono;
    @NotBlank(message = "il campo email è obbligatorio")
    @Pattern(regexp = "^(?=.{1,32}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$",
            message = "email non valida: non segue gli standard di validazione")
    private final String email;
    @NotBlank(message = "il campo password è obbligatorio")
    @Size(min = 8, max = 16, message = "password non valida: deve essere compresa tra 8 - 16 caratteri")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,16}$",
            message = "password non valida: non segue gli standard di validazione")
    private final String password;
    @NotNull(message = "il campo data nascita è obbligatorio")
    @Past(message = "data nascita non valida: deve essere diversa da quella attuale o da una data futura")
    private final LocalDate dataNascita;
    @NotBlank(message = "il campo codice fiscale è obbligatorio")
    @Pattern(regexp = "^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T]" +
            "(?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T]" +
            "[26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L]" +
            "(?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$", message = "codice fiscale non valido: non segue gli standard di validazione")
    private final String codiceFiscale;
    @NotNull(message = "il campo foreign key medicoId è obbligatorio")
    private final Long medicoId;

    /**
     * Costruttore parametrico che istanzia una nuova entità Paziente.
     *
     * @param id             id
     * @param nome           nome
     * @param cognome        cognome
     * @param telefono       telefono
     * @param email          email
     * @param password       password
     * @param dataNascita    data nascita
     * @param codiceFiscale  codice fiscale
     * @param medicoId       medico id
     */

    public PazienteCreateDTO(Long id, String nome, String cognome, String telefono, String email, String password, LocalDate dataNascita, String codiceFiscale, Long medicoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
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
     * Metodo che restituisce la password.
     *
     * @return la password
     */
    public String getPassword() {
        return password;
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
