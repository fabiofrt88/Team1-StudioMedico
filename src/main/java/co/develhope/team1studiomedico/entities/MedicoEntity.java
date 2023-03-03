package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.List;

/**
 * La classe MedicoEntity rappresenta il modello dei dati del Medico
 */
@Entity(name = "medico")
@Table(name = "medico")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "prenotazioni", "pazienti", "status"})
public class MedicoEntity extends PersonaEntity{

    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PazienteEntity> pazienti;

    /**
     * Costruttore di default che istanzia un nuovo MedicoEntity.
     */
    public MedicoEntity(){}

    /**
     * Costruttore parametrico che istanzia un nuovo MedicoEntity.
     *
     * @param id           l' id
     * @param nome         il nome
     * @param cognome      il cognome
     * @param telefono     il telefono
     * @param email        l' email
     * @param prenotazioni le prenotazioni
     * @param pazienti     i pazienti
     */
    public MedicoEntity(Long id, String nome, String cognome, String telefono, String email, List<PrenotazioneEntity> prenotazioni, List<PazienteEntity> pazienti) {
        super(id, nome, cognome, telefono, email);
        this.prenotazioni = prenotazioni;
        this.pazienti = pazienti;
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
     * Metodo che restituisce i pazienti associati.
     *
     * @return i pazienti
     */
    public List<PazienteEntity> getPazienti() {
        return pazienti;
    }

    /**
     * Metodo che setta i pazienti associati.
     *
     * @param pazienti le pazienti
     */
    public void setPazienti(List<PazienteEntity> pazienti) {
        this.pazienti = pazienti;
    }

}

