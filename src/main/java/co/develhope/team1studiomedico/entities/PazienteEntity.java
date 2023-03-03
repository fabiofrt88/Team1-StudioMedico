package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * La classe PazienteEntity rappresenta il modello dei dati del Paziente
 */
@Entity(name = "paziente")
@Table(name = "paziente")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "dataNascita", "codiceFiscale", "prenotazioni", "medico", "status"})
public class PazienteEntity extends PersonaEntity{

    @Column(nullable = false, name = "data_nascita")
    private LocalDate dataNascita;
    @Column(nullable = false, unique = true, name = "codice_fiscale")
    private String codiceFiscale;
    @OneToMany(mappedBy = "paziente",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    /**
     * Costruttore di default che istanzia un nuovo PazienteEntity.
     */
    public PazienteEntity(){}

    /**
     * Costruttore parametrico che istanzia una nuova entità Paziente.
     *
     * @param id             id
     * @param nome           nome
     * @param cognome        cognome
     * @param telefono       telefono
     * @param email          email
     * @param dataNascita    data nascita
     * @param codiceFiscale  codice fiscale
     * @param prenotazioni   prenotazioni
     * @param medico         medico
     */
    public PazienteEntity(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, List<PrenotazioneEntity> prenotazioni, MedicoEntity medico) {
        super(id, nome, cognome, telefono, email);
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.prenotazioni = prenotazioni;
        this.medico = medico;
    }

    /**
     * Metodo che restituisce la data nascita.
     *
     * @return la data nascita
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * Metodo che setta la data nascita.
     *
     * @param dataNascita la data nascita
     */
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * Metodo che restituisce il codice fiscale.
     *
     * @return il codice fiscale
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Metodo che setta il codice fiscale.
     *
     * @param codiceFiscale il codice fiscale
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Metodo che restituisce le prenotazioni.
     *
     * @return le prenotazioni
     */
    public List<PrenotazioneEntity> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Metodo che setta le prenotazioni.
     *
     * @param prenotazioni le prenotazioni
     */
    public void setPrenotazioni(List<PrenotazioneEntity> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    /**
     * Metodo che retituisce il medico.
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

}