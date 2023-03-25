package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.*;

/**
 * La classe SegretarioCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di SegretarioEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare una nuovo segretario nel database
 */
public class SegretarioCreateDTO {

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
    @NotNull(message = "il campo foreign key medicoId è obbligatorio")
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
