package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

@Entity(name = "segretario")
@Table(name = "segretario")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "medico", "status"})
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

