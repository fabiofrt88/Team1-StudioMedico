package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "segretario")
public class SegretarioEntity extends PersonaEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    public SegretarioEntity(){}

    public SegretarioEntity(Long id, String nome, String cognome, String telefono, String email, MedicoEntity medico) {
        super(id, nome, cognome, telefono, email);
        this.medico = medico;
    }

    public MedicoEntity getMedico() {
        return medico;
    }

    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

}

