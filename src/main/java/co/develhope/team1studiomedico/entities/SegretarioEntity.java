package co.develhope.team1studiomedico.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

/**
 * La classe SegretarioEntity rappresenta il modello dei dati del Segretario
 */
@Entity(name = "segretario")
@Table(name = "segretario")
@JsonPropertyOrder({"id", "nome", "cognome", "telefono",
        "email", "medico", "status"})
public class SegretarioEntity extends PersonaEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id", unique = true)
    private MedicoEntity medico;

    /**
     * Costruttore di default che istanzia un nuovo SegretarioEntity.
     */
    public SegretarioEntity(){ }

    /**
     * Costruttore parametrico che istanzia un nuovo SegretarioEntity.
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
     * Metodo che restituisce il medico associato.
     *
     * @return il medico
     */
    public MedicoEntity getMedico() {
        return medico;
    }

    /**
     * Metodo che setta il medico associato.
     *
     * @param medico il medico
     */
    public void setMedico(MedicoEntity medico) {
        this.medico = medico;
    }

}

