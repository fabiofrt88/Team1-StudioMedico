package co.develhope.team1studiomedico.entities;

import co.develhope.team1studiomedico.entities.auditing.Auditable;
import co.develhope.team1studiomedico.entities.utils.EntityStatusEnumConverter;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

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
    private EntityStatusEnum status; //Character

    public PersonaEntity (){}

    public PersonaEntity(Long id, String nome, String cognome, String telefono, String email) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.status = EntityStatusEnum.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EntityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EntityStatusEnum status) {
        this.status = status;
    }

}