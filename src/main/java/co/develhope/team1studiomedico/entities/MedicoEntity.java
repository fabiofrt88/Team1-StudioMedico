package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.List;

@Entity(name = "medico")
@Table(name = "medico")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "prenotazioni", "pazienti", "status"})
public class MedicoEntity extends PersonaEntity{

    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PazienteEntity> pazienti;

    public MedicoEntity(){}

    public MedicoEntity(Long id, String nome, String cognome, String telefono, String email, List<PrenotazioneEntity> prenotazioni, List<PazienteEntity> pazienti) {
        super(id, nome, cognome, telefono, email);
        this.prenotazioni = prenotazioni;
        this.pazienti = pazienti;
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

}

