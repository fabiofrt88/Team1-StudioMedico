package co.develhope.team1studiomedico.dto.paziente;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * La classe PazienteCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di PazienteEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare un nuovo paziente nel database
 */
public class PazienteCreateDTO {

    private final Long id;
    @NotBlank(message = "{input.validation.nome.notBlank}")
    @Size(min = 2, max = 25, message = "{input.validation.nome.size}")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "{input.validation.nome.pattern}")
    private final String nome;
    @NotBlank(message = "{input.validation.cognome.notBlank}")
    @Size(min = 2, max = 25, message = "{input.validation.cognome.size}")
    @Pattern(regexp = "^[a-zA-Z']+(?:\\s[a-zA-Z']+)*$", message = "{input.validation.cognome.pattern}")
    private final String cognome;
    @NotBlank(message = "{input.validation.telefono.notBlank}")
    @Size(min = 8, max = 16, message = "{input.validation.telefono.size}")
    @Pattern(regexp = "^(\\((00|\\+)39\\)|(00|\\+)39)?(38[890]|34[4-90]|36[680]|33[13-90]|32[89]|35[01]|37[019])\\d{6,7}$",
            message = "{input.validation.telefono.pattern}")
    private final String telefono;
    @NotBlank(message = "{input.validation.email.notBlank}")
    @Pattern(regexp = "^(?=.{1,32}@)[a-z0-9_-]+(\\.[a-z0-9_-]+)*@[^-][a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$",
            message = "{input.validation.email.pattern}")
    private final String email;
    @NotBlank(message = "{input.validation.password.notBlank}")
    @Size(min = 8, max = 16, message = "{input.validation.password.size}")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,16}$",
            message = "{input.validation.password.pattern}")
    private final String password;
    @NotNull(message = "{input.validation.dataNascita.notNull}")
    @Past(message = "{input.validation.dataNascita.past}")
    private final LocalDate dataNascita;
    @NotBlank(message = "{input.validation.codiceFiscale.notBlank}")
    @Pattern(regexp = "^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T]" +
            "(?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T]" +
            "[26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L]" +
            "(?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$", message = "{input.validation.codiceFiscale.pattern}")
    private final String codiceFiscale;
    @NotNull(message = "{input.validation.medicoId.notNull}")
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
