package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.entities.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * La superInterface PersonaRepository è un repository che fornisce un insieme di metodi e custom query per la
 * gestione dei dati nel database utilizzando la JPA (Java Persistence API) per definire query ad alto livello.
 */
@NoRepositoryBean
public interface PersonaRepository<T extends PersonaEntity> extends JpaRepository<T, Long> {

    /**
     * Restituisce la lista degli utenti filtrati per record status
     *
     * @param recordStatus lo stato di attività del record
     * @return la lista delle prenotazioni filtrate per record status
     */
    List<T> findByRecordStatus(EntityStatusEnum recordStatus);

    /**
     * Cancellazione logica tramite id.
     *
     * @param id l' id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED WHERE e.id = :id")
    void softDeleteById(@Param("id") Long id);

    /**
     * Cancellazione logica.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED " +
            "WHERE e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE")
    void softDelete();

    /**
     * Ripristino tramite id.
     *
     * @param id l' id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE WHERE e.id = :id")
    void restoreById(@Param("id") Long id);

    /**
     * Ripristino.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE " +
            "WHERE e.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED")
    void restore();

    /**
     * Cambia status tramite id.
     *
     * @param recordStatus the status
     * @param id     the id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE #{#entityName} e SET e.recordStatus = :recordStatus WHERE e.id = :id")
    void changeStatusById(@Param("recordStatus") EntityStatusEnum recordStatus, @Param("id") Long id);

    /**
     * Ricerca utente (medico, segretario, paziente) per email
     * @param email email di ricerca
     * @return utente (medico, segretario, paziente)
     */
    Optional<T> findByEmail(String email);

    /**
     * Ricerca utenti (medico, segretario, paziente) per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista utenti (medico, segretario, paziente) filtrati per nome e cognome
     */
    @Query("SELECT e FROM #{#entityName} e WHERE e.nome LIKE %:nome% AND e.cognome LIKE %:cognome%")
    List<T> searchByNomeAndCognome(@Param("nome") String nome, @Param("cognome") String cognome);

}
