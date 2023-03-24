package co.develhope.team1studiomedico.entities;

import co.develhope.team1studiomedico.entities.auditing.Auditable;
import co.develhope.team1studiomedico.entities.utils.EntityStatusEnumConverter;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

/**
 * La superclasse astratta PersonaEntity rappresenta il modello dei dati di una generica persona.
 * Fa ereditare tutti metodi e attributi alle sottoclassi derivate. Poichè astratta, non è istanziabile.
 */
@Component
@MappedSuperclass
public abstract class PersonaEntity extends Auditable<String> {

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
    private EntityStatusEnum recordStatus;

    /**
     * Costruttore di default di PersonaEntity.
     */
    public PersonaEntity (){ }

    /**
     * Costruttore parametrico di PersonaEntity
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
        this.recordStatus = EntityStatusEnum.ACTIVE;
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
     * Metodo che setta l'id.
     *
     * @param id l' id
     */
    public void setId(Long id) {
        this.id = id;
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
     * Metodo che setta il nome.
     *
     * @param nome il nome
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * Metodo che setta il cognome.
     *
     * @param cognome il cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
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
     * Metodo che setta il numero di telefono.
     *
     * @param telefono il telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * Metodo che setta l'email.
     *
     * @param email l' email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metodo che restituisce lo status.
     *
     * @return lo status
     */
    public EntityStatusEnum getRecordStatus() {
        return recordStatus;
    }

    /**
     * Metodo che setta lo status.
     *
     * @param recordStatus lo status
     */
    public void setRecordStatus(EntityStatusEnum recordStatus) {
        this.recordStatus = recordStatus;
    }

}