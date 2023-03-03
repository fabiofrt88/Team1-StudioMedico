package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
/**
 * La sottoClasse SegretarioEntity rappresenta le informazioni memorizzate nel database per l'attore Segretario.
 */
@Entity(name = "segretario")
@Table(name = "segretario")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "medico", "status"})
public class SegretarioEntity extends PersonaEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    /**
     * Instanzia un nuovo Segretario entity.
     */
    public SegretarioEntity(){}

    /**
     * Instanzia un nuovo Segretario entity.
     *
     * @param id       the id
     * @param nome     the nome
     * @param cognome  the cognome
     * @param telefono the telefono
     * @param email    the email
     * @param medico   the medico
     */
    public SegretarioEntity(Long id, String nome, String cognome, String telefono, String email, MedicoEntity medico) {
        super(id, nome, cognome, telefono, email);
        this.medico = medico;
    }

    /**
     * Funzione che ottiene il medico.
     *
     * @return the medico
     */
    public MedicoEntity getMedico() {
        return medico;
    }

    /**
     * Funzione che setta il medico.
     *
     * @param medico the medico
     */
    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

}

