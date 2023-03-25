package co.develhope.team1studiomedico.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * La classe MedicoDTO rappresenta il DTO (Data Transfer Object) di update e lettura di MedicoEntity,
 * consente di creare degli oggetti di trasferimento dati in entrata (update) e uscita (lettura) mediante i quali sarà possibile
 * rispettivamente modificare un medico e restituire una selezione dei dati di un medico nel payload delle response
 */
public class MedicoDTO {

    private String id;
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

    /**
     * Costruttore di default che istanzia un nuovo MedicoDTO.
     */
    public MedicoDTO(){ }

    /**
     * Costruttore parametrico che istanzia una nuova entità MedicoDTO..
     *
     * @param id             id
     * @param nome           nome
     * @param cognome        cognome
     * @param telefono       telefono
     * @param email          email
     */
    public MedicoDTO(String id, String nome, String cognome, String telefono, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
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
     * Metodo che setta l'id del medico.
     *
     * @param id l'id.
     */
    public void setId(String id) {
        this.id = id;
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
     * Metodo che setta il nome del medico.
     *
     * @param nome il nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * Metodo che setta il cognome del medico.
     *
     * @param cognome il cognome.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
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
     * Metodo che setta il telefono del medico.
     *
     * @param telefono il telefono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * Metodo che setta l'email del medico.
     *
     * @param email l'email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MedicoDTO{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
