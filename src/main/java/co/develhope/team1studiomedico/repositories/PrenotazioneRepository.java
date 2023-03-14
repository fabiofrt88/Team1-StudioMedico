package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * L'interfaccia PrenotazioneRepository è un repository che fornisce un insieme di metodi e custom query per la
 * gestione dei dati nel database utilizzando la JPA (Java Persistence API) per definire query ad alto livello.
 */
@Repository
public interface PrenotazioneRepository extends JpaRepository<PrenotazioneEntity, Long> {

    /**
     * Restituisce la lista delle prenotazioni filtrate per record status
     *
     * @param recordStatus lo stato di attività del record
     * @return la lista delle prenotazioni filtrate per record status
     */
    List<PrenotazioneEntity> findByRecordStatus(EntityStatusEnum recordStatus);

    /**
     * Cancellazione logica tramite id.
     *
     * @param id l' id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE prenotazione p SET p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED WHERE p.id = :id")
    void softDeleteById(@Param("id") Long id);

    /**
     * Cancellazione logica.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE prenotazione p SET p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED " +
            "WHERE p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE")
    void softDelete();

    /**
     * Ripristino tramite id.
     *
     * @param id l' id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE prenotazione p SET p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE WHERE p.id = :id")
    void restoreById(@Param("id") Long id);

    /**
     * Ripristino.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE prenotazione p SET p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.ACTIVE " +
            "WHERE p.recordStatus = co.develhope.team1studiomedico.entities.EntityStatusEnum.DELETED")
    void restore();

    /**
     * Cambia status tramite id.
     *
     * @param recordStatus the status
     * @param id     the id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE prenotazione p SET p.recordStatus = :recordStatus WHERE p.id = :id")
    void changeStatusById(@Param("recordStatus") EntityStatusEnum recordStatus, @Param("id") Long id);

    /*@Query(value = "SELECT COUNT(*) prenotazione p WHERE p.dataPrenotazione = :dataPrenotazione")
    Integer countPrenotazioniByDate(@Param("dataPrenotazione") LocalDate dataPrenotazione);*/

}
