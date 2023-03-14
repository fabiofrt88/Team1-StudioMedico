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
}
