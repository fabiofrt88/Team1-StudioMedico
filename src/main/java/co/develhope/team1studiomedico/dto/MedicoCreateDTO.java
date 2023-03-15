package co.develhope.team1studiomedico.dto;

public class MedicoCreateDTO {
    private final String id;
    private final String nome;
    private final String cognome;
    private final String email;
    private final String telefono;
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
    public MedicoCreateDTO(String id, String nome, String cognome, String email, String telefono, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
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
     * Metodo che restituisce l'email del medico.
     *
     * @return l'email.
     */
    public String getEmail() {
        return email;
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
     * Metodo che restituisce la password del medico.
     *
     * @return password.
     */
    public String getPassword() {
        return password;
    }
}
