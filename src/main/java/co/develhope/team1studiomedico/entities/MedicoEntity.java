package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.List;
/**
 * La sottoClasse MedicoEntity rappresenta le informazioni memorizzate nel database per l'attore Medico.
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
     * Instanzia un nuovo Medico entity.
     */
    public MedicoEntity(){}

    /**
     * Instanzia un nuovo Medico entity con parametri:
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
     * Funzione che ottiene le prenotazioni.
     *
     * @return the prenotazioni
     */
    public List<PrenotazioneEntity> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Funzione che setta le prenotazioni.
     *
     * @param prenotazioni the prenotazioni
     */
    public void setPrenotazioni(List<PrenotazioneEntity> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    /**
     * Funzione che ottiene i pazienti.
     *
     * @return the pazienti
     */
    public List<PazienteEntity> getPazienti() {
        return pazienti;
    }

    /**
     * Funzione che setta le i pazienti.
     *
     * @param pazienti the pazienti
     */
    public void setPazienti(List<PazienteEntity> pazienti) {
        this.pazienti = pazienti;
    }

}

