package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "medico")
@Table(name = "medico")
public class MedicoEntity extends PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String telefono;
    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PazienteEntity> pazienti;
    @Enumerated(EnumType.ORDINAL)
    private EntityStatusEnum status;

    public MedicoEntity(){}

    public MedicoEntity(Long id, String nome, String cognome, String email, String telefono, List<PrenotazioneEntity> prenotazioni, List<PazienteEntity> pazienti) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.prenotazioni = prenotazioni;
        this.pazienti = pazienti;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<PrenotazioneEntity> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<PrenotazioneEntity> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public List<PazienteEntity> getPazienti() {
        return pazienti;
    }

    public void setPazienti(List<PazienteEntity> pazienti) {
        this.pazienti = pazienti;
    }

    public EntityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EntityStatusEnum status) {
        this.status = status;
    }

}
