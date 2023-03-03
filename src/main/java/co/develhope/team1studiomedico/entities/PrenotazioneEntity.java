package co.develhope.team1studiomedico.entities;

import co.develhope.team1studiomedico.entities.auditing.Auditable;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "prenotazione")
public class PrenotazioneEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "booked_at")
    private String bookedAt;
    @Column(nullable = false, name = "data_prenotazione")
    private LocalDate dataPrenotazione;
    @Column(nullable = false, name = "ora_prenotazione")
    private LocalTime oraPrenotazione;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paziente_id")
    private PazienteEntity paziente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    public PrenotazioneEntity(){}

    public PrenotazioneEntity(Long id, String bookedAt, LocalDate dataPrenotazione, LocalTime oraPrenotazione, PazienteEntity paziente, MedicoEntity medico) {
        this.id = id;
        this.bookedAt = bookedAt;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.paziente = paziente;
        this.medico = medico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(String bookedAt) {
        this.bookedAt = bookedAt;
    }

    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public LocalTime getOraPrenotazione() {
        return oraPrenotazione;
    }

    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    public PazienteEntity getPaziente() {
        return paziente;
    }

    public void setPaziente(PazienteEntity paziente) {
        this.paziente = paziente;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

}