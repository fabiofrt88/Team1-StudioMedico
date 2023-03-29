package co.develhope.team1studiomedico.dto.segretario;

import jakarta.validation.constraints.*;

/**
 * La classe SegretarioCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di SegretarioEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare una nuovo segretario nel database
 */
public class SegretarioCreateDTO {

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
    @NotNull(message = "{input.validation.medicoId.notNull}")
    private final Long medicoId;

    /**
     * Costruttore parametrico che istanzia un nuovo SegretarioCreateDTO
     *
     * @param id        id segretario
     * @param nome      nome segretario
     * @param cognome   cognome segretario
     * @param telefono  telefono segretario
     * @param email     email segretario
     * @param password  password segretario
     * @param medicoId  medico id di riferimento
     */
    public SegretarioCreateDTO(Long id, String nome, String cognome, String telefono, String email, String password, Long medicoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.medicoId = medicoId;
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
     * Metodo che restituisce il nome del segretario.
     *
     * @return Il nome del segretario
     */
    public String getNome() {
        return nome;
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
     * Metodo che restituisce il telefono.
     *
     * @return Il telefono del segretario
     */
    public String getTelefono() {
        return telefono;
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
     * Metodo che restituisce la password del segretario.
     *
     * @return La password del segretario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metodo che restituisce l'id del medico di riferimento.
     *
     * @return L'id del medico
     */
    public Long getMedicoId() {
        return medicoId;
    }

}
