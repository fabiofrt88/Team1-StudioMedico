package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "paziente")
@Table(name = "paziente")
public class PazienteEntity extends PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private LocalDate dataNascita;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String codiceFiscale;
    @OneToMany(mappedBy = "paziente",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;
    @Enumerated(EnumType.ORDINAL)
    private EntityStatusEnum status;

    public PazienteEntity(){}

    public PazienteEntity(Long id, String nome, String cognome, LocalDate dataNascita, String telefono, String email, String codiceFiscale, List<PrenotazioneEntity> prenotazioni, MedicoEntity medico) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.telefono = telefono;
        this.email = email;
        this.codiceFiscale = codiceFiscale;
        this.prenotazioni = prenotazioni;
        this.medico = medico;
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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
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

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public List<PrenotazioneEntity> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<PrenotazioneEntity> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

    public EntityStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EntityStatusEnum status) {
        this.status = status;
    }

}
