package co.develhope.team1studiomedico.entities;

import co.develhope.team1studiomedico.entities.utils.EntityStatusEnumConverter;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;
/**
 * La superClasse PersonaEntity fa ereditare tutti metodi e attributi alle sottoClassi.
 */
@Component
@MappedSuperclass
public abstract class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "nome")
    private String nome;
    @Column(nullable = false, name = "cognome")
    private String cognome;
    @Column(nullable = false, name = "telefono")
    private String telefono;
    @Column(nullable = false, unique = true, name = "email")
    private String email;
    @Convert(converter = EntityStatusEnumConverter.class)
    @Column(nullable = false, name = "record_status")
    private EntityStatusEnum status; //Character

    /**
     * Instanzia una nuova Persona entity.
     */
    public PersonaEntity (){}

    /**
     * Instanzia una nuova Persona entity.
     *
     * @param id       l' id
     * @param nome     il nome
     * @param cognome  il cognome
     * @param telefono il telefono
     * @param email    l' email
     */
    public PersonaEntity(Long id, String nome, String cognome, String telefono, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.status = EntityStatusEnum.ACTIVE;
    }

    /**
     * Funzione che ottiene l'id.
     *
     * @return l' id
     */
    public Long getId() {
        return id;
    }

    /**
     * Funzione che setta l'id.
     *
     * @param id l' id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Funzione che ottiene il nome.
     *
     * @return il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Funzione che setta il nome.
     *
     * @param nome il nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Funzione che ottiene il cognome.
     *
     * @return il cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Funzione che setta il cognome.
     *
     * @param cognome il cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Funzione che ottiene il numero di telefono.
     *
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Funzione che setta il numero di telefono.
     *
     * @param telefono il telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Funzione che ottiene l'email.
     *
     * @return l' email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Funzione che setta l'email.
     *
     * @param email l' email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Funzione che ottiene lo status.
     *
     * @return lo status
     */
    public EntityStatusEnum getStatus() {
        return status;
    }

    /**
     * Funzione che setta lo status.
     *
     * @param status lo status
     */
    public void setStatus(EntityStatusEnum status) {
        this.status = status;
    }

}