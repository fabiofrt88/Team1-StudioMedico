package co.develhope.team1studiomedico.dto;

/**
 * La classe SegretarioCreateDTO rappresenta il DTO (Data Transfer Object) di creazione di SegretarioEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata a partire dai quali sarà possibile
 * creare una nuovo segretario nel database
 */
public class SegretarioCreateDTO {

    private final Long id;
    private final String nome;
    private final String cognome;
    private final String email;
    private final String telefono;
    private final String password;
    private final String medicoId;

    /**
     * Costruttore parametrico che istanzia un nuovo SegretarioCreateDTO
     *
     * @param id       id segretario
     * @param nome     nome segretario
     * @param cognome  cognome segretario
     * @param email    email segretario
     * @param telefono telefono segretario
     * @param password password segretario
     * @param medicoId medico id di riferimento(per il quale lavora)
     */
    public SegretarioCreateDTO(Long id, String nome, String cognome, String email, String telefono, String password, String medicoId) {

        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
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
     * Metodo che restituisce l'email del segretario.
     *
     * @return L'email del segretario
     */
    public String getEmail() {
        return email;
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
    public String getMedicoId() {
        return medicoId;
    }

}
