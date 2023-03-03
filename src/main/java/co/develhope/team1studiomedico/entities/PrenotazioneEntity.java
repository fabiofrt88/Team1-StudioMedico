package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 *  La Classe PrenotazioneEntity rappresenta le informazioni memorizzate nel database per l'attore Prenotazione.
 */
@Entity
@Table(name = "prenotazione")
public class PrenotazioneEntity {

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

    /**
     * Instanzia una nuova Prenotazione entity.
     */
    public PrenotazioneEntity(){}

    /**
     *  Instanzia una nuova Prenotazione entity.
     *
     * @param id               the id
     * @param bookedAt         the booked at
     * @param dataPrenotazione the data prenotazione
     * @param oraPrenotazione  the ora prenotazione
     * @param paziente         the paziente
     * @param medico           the medico
     */
    public PrenotazioneEntity(Long id, String bookedAt, LocalDate dataPrenotazione, LocalTime oraPrenotazione, PazienteEntity paziente, MedicoEntity medico) {
        this.id = id;
        this.bookedAt = bookedAt;
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.paziente = paziente;
        this.medico = medico;
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
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Funzione che ottiene il booked at.
     *
     * @return il booked at
     */
    public String getBookedAt() {
        return bookedAt;
    }

    /**
     * Funzione che setta il booked at.
     *
     * @param bookedAt il booked at
     */
    public void setBookedAt(String bookedAt) {
        this.bookedAt = bookedAt;
    }

    /**
     * Funzione che ottiene la data prenotazione.
     *
     * @return the data prenotazione
     */
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    /**
     * Funzione che setta la data prenotazione.
     *
     * @param dataPrenotazione the data prenotazione
     */
    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    /**
     * Funzione che ottiene l'ora prenotazione.
     *
     * @return l'ora prenotazione
     */
    public LocalTime getOraPrenotazione() {
        return oraPrenotazione;
    }

    /**
     * Funzione che setta l'ora prenotazione.
     *
     * @param oraPrenotazione the ora prenotazione
     */
    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    /**
     * Funzione che ottiene il paziente.
     *
     * @return the paziente
     */
    public PazienteEntity getPaziente() {
        return paziente;
    }

    /**
     * Funzione che setta il paziente.
     *
     * @param paziente il paziente
     */
    public void setPaziente(PazienteEntity paziente) {
        this.paziente = paziente;
    }

    /**
     * Funzione che ottiene il medico.
     *
     * @return il medico
     */
    public MedicoEntity getMedico() {
        return medico;
    }

    /**
     * Funzione che setta il medico.
     *
     * @param medico il medico
     */
    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

}