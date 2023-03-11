package co.develhope.team1studiomedico.entities;

import co.develhope.team1studiomedico.entities.auditing.Auditable;
import co.develhope.team1studiomedico.entities.utils.EntityStatusEnumConverter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * La classe PrenotazioneEntity rappresenta il modello dei dati della Prenotazione
 */
@Entity(name = "prenotazione")
@Table(name = "prenotazione")
@JsonPropertyOrder({"id", "bookedAt", "dataPrenotazione", "oraPrenotazione",
        "paziente", "medico", "statoPrenotazione", "recordStatus"})
public class PrenotazioneEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;
    @Column(nullable = false, name = "booked_at")
    private final LocalDateTime bookedAt;
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "stato_prenotazione")
    private PrenotazioneStatusEnum statoPrenotazione;
    @Convert(converter = EntityStatusEnumConverter.class)
    @Column(nullable = false, name = "record_status")
    private EntityStatusEnum recordStatus; //Character

    /**
     * Costruttore di default che istanzia una nuova PrenotazioneEntity.
     */
    public PrenotazioneEntity(){
        this.bookedAt = LocalDateTime.now();
    }

    /**
     * Costruttore parametrico che istanzia una nuova PrenotazioneEntity.
     *
     * @param id               the id
     * @param dataPrenotazione the data prenotazione
     * @param oraPrenotazione  the ora prenotazione
     * @param paziente         the paziente
     * @param medico           the medico
     */
    public PrenotazioneEntity(Long id, LocalDate dataPrenotazione, LocalTime oraPrenotazione, PazienteEntity paziente, MedicoEntity medico, PrenotazioneStatusEnum statoPrenotazione) {
        this.id = id;
        this.bookedAt = LocalDateTime.now();
        this.dataPrenotazione = dataPrenotazione;
        this.oraPrenotazione = oraPrenotazione;
        this.paziente = paziente;
        this.medico = medico;
        this.statoPrenotazione = statoPrenotazione;
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
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodo che restituisce il booked at.
     *
     * @return il booked at
     */
    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    /**
     * Metodo che restituisce la data prenotazione.
     *
     * @return the data prenotazione
     */
    public LocalDate getDataPrenotazione() {
        return dataPrenotazione;
    }

    /**
     * Metodo che setta la data prenotazione.
     *
     * @param dataPrenotazione the data prenotazione
     */
    public void setDataPrenotazione(LocalDate dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    /**
     * Metodo che restituisce l'ora prenotazione.
     *
     * @return l'ora prenotazione
     */
    public LocalTime getOraPrenotazione() {
        return oraPrenotazione;
    }

    /**
     * Metodo che setta l'ora prenotazione.
     *
     * @param oraPrenotazione the ora prenotazione
     */
    public void setOraPrenotazione(LocalTime oraPrenotazione) {
        this.oraPrenotazione = oraPrenotazione;
    }

    /**
     * Metodo che restituisce il paziente.
     *
     * @return the paziente
     */
    public PazienteEntity getPaziente() {
        return paziente;
    }

    /**
     * Metodo che setta il paziente.
     *
     * @param paziente il paziente
     */
    public void setPaziente(PazienteEntity paziente) {
        this.paziente = paziente;
    }

    /**
     * Metodo che restituisce il medico.
     *
     * @return il medico
     */
    public MedicoEntity getMedico() {
        return medico;
    }

    /**
     * Metodo che setta il medico.
     *
     * @param medico il medico
     */
    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

    /**
     * Metodo che restituisce lo stato della prenotazione.
     *
     * @return lo stato della prenotazione
     */
    public PrenotazioneStatusEnum getStatoPrenotazione() {
        return statoPrenotazione;
    }

    /**
     * Metodo che setta lo stato dela prenotazione.
     *
     * @param statoPrenotazione lo stato della prenotazione
     */
    public void setStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
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