package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * La classe MedicoCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di MedicoEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare un nuovo medico nel database
 */
public class MedicoCreateDTO {

    private final String id;

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

    /**
     * Costruttore parametrico che istanzia una nuova entità Paziente.
     *
     * @param id             id
     * @param nome           nome
     * @param cognome        cognome
     * @param telefono       telefono
     * @param email          email
     * @param password       password
     */
    public MedicoCreateDTO(String id, String nome, String cognome, String telefono, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
    }

    /**
     * Metodo che restituisce l'id del medico.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo che restituisce il nome del medico.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che restituisce il cognome del medico.
     *
     * @return cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che restituisce il telefono del medico.
     *
     * @return telefono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Metodo che restituisce l'email del medico.
     *
     * @return l'email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo che restituisce la password del medico.
     *
     * @return password.
     */
    public String getPassword() {
        return password;
    }

}
