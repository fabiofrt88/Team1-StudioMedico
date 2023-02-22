package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "paziente")
@Table(name = "paziente")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "dataNascita", "codiceFiscale", "prenotazioni", "medico", "status"})
public class PazienteEntity extends PersonaEntity{

    @Column(nullable = false)
    private LocalDate dataNascita;
    @Column(nullable = false, unique = true)
    private String codiceFiscale;
    @OneToMany(mappedBy = "paziente",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    public PazienteEntity(){}

    public PazienteEntity(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, List<PrenotazioneEntity> prenotazioni, MedicoEntity medico) {
        super(id, nome, cognome, telefono, email);
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.prenotazioni = prenotazioni;
        this.medico = medico;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
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

}