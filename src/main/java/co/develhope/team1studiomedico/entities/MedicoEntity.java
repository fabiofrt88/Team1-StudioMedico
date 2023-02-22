package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "medico")
@Table(name = "medico")
public class MedicoEntity extends PersonaEntity{

    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PrenotazioneEntity> prenotazioni;
    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<PazienteEntity> pazienti;
    @Enumerated(EnumType.ORDINAL)
    private EntityStatusEnum status;

    public MedicoEntity(){}

    public MedicoEntity(Long id, String nome, String cognome, String telefono, String email, List<PrenotazioneEntity> prenotazioni, List<PazienteEntity> pazienti) {
        super(id, nome, cognome, telefono, email);
        this.prenotazioni = prenotazioni;
        this.pazienti = pazienti;
        this.status = EntityStatusEnum.ACTIVE;
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

