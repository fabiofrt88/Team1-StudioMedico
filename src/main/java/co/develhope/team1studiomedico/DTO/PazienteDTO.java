package co.develhope.team1studiomedico.DTO;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;

import java.time.LocalDate;

public class PazienteDTO {

    private Long id;

    private String nome;

    private String cognome;

    private String telefono;

    private String email;

    private LocalDate dataNascita;

    private String codiceFiscale;

    private EntityStatusEnum recordStatus;

    private Long medicoId;

    /**
     * Costruttore di default di PersonaEntity.
     */
    public PazienteDTO (){ }

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
     * @param medicoId         medico id
     */

    public PazienteDTO(Long id, String nome, String cognome, String telefono, String email, LocalDate dataNascita, String codiceFiscale, EntityStatusEnum recordStatus, Long medicoId) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.email = email;
        this.dataNascita = dataNascita;
        this.codiceFiscale = codiceFiscale;
        this.recordStatus = recordStatus;
        this.medicoId = medicoId;
    }
}
