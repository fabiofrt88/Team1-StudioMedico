package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;

@Entity(name = "segretario")
@Table(name = "segretario")
public class SegretarioEntity extends PersonaEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;
    @Enumerated(EnumType.ORDINAL)
    private EntityStatusEnum status;

    public SegretarioEntity(){}

    public SegretarioEntity(Long id, String nome, String cognome, String telefono, String email, MedicoEntity medico) {
        super(id, nome, cognome, telefono, email);
        this.medico = medico;
        this.status = EntityStatusEnum.ACTIVE;
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

