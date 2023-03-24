package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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

    /**
     * Ricerca le prenotazioni a partire dall'id del medico (foreign key medicoId in prenotazione)
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per id medico
     */
    List<PrenotazioneEntity> findPrenotazioniByMedicoId(Long medicoId);

    /**
     * Ricerca le prenotazioni a partire dall'id del paziente (foreign key pazienteId in prenotazione)
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per id paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByPazienteId(Long pazienteId);

    /**
     * Ricerca le prenotazioni a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per id segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s
            WHERE s.id = :segretarioId""")
    List<PrenotazioneEntity> findPrenotazioniBySegretarioId(@Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione
     * @param dataPrenotazione data di prenotazione
     * @return lista delle prenotazioni filtrate per data di prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazione(LocalDate dataPrenotazione);

    /**
     * Ricerca le prenotazioni a partire dalla data e dall'ora della prenotazione
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @return lista delle prenotazioni filtrate per data e ora della prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndOraPrenotazione(LocalDate dataPrenotazione, LocalTime oraPrenotazione);

    /**
     * Ricerca le prenotazioni nell'intervallo di due date considerate
     * @param startDate data inizio
     * @param endDate data fine
     * @return lista delle prenotazioni nell'intervallo di due date considerate
     */
    @Query("SELECT pr FROM prenotazione pr WHERE pr.dataPrenotazione BETWEEN :startDate AND :endDate")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazione(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Ricerca le prenotazioni per stato prenotazione
     * @param statoPrenotazione stato della prenotazione
     * @return lista delle prenotazioni filtrate per stato prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndMedicoId(LocalDate dataPrenotazione, Long medicoId);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del paziente
     * @param dataPrenotazione data di prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndPazienteId(LocalDate dataPrenotazione, Long pazienteId);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s
            WHERE pr.dataPrenotazione = :dataPrenotazione AND s.id = :segretarioId""")
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndSegretarioId(@Param("dataPrenotazione") LocalDate dataPrenotazione, @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long medicoId);

    /**
     * Ricerca le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s
            WHERE pr.dataPrenotazione = :dataPrenotazione
            AND pr.oraPrenotazione = :oraPrenotazione
            AND s.id = :segretarioId""")
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndSegretarioId(@Param("dataPrenotazione") LocalDate dataPrenotazione,
                                                                                                 @Param("oraPrenotazione") LocalTime oraPrenotazione,
                                                                                                 @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni nell'intervallo di due date considerate e id del medico
     * @param startDate data inizio
     * @param endDate data fine
     * @param medicoId id del medico
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del medico
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE pr.dataPrenotazione BETWEEN :startDate AND :endDate
            AND pr.medico.id = :medicoId""")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazioneAndMedicoId(@Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate,
                                                                                @Param("medicoId") Long medicoId);

    /**
     * Ricerca le prenotazioni nell'intervallo di due date considerate e id del segretario
     * @param startDate data inizio
     * @param endDate data fine
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            INNER JOIN segretario s
            WHERE pr.dataPrenotazione BETWEEN :startDate AND :endDate
            AND s.id = :segretarioId""")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazioneAndSegretarioId(@Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate,
                                                                                @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni nell'intervallo di due date considerate e id del paziente
     * @param startDate data inizio
     * @param endDate data fine
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del paziente
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE pr.dataPrenotazione BETWEEN :startDate AND :endDate
            AND pr.paziente.id = :pazienteId""")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazioneAndPazienteId(@Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate,
                                                                                @Param("pazienteId") Long pazienteId);

    /**
     * Ricerca le prenotazioni per stato prenotazione e id del medico
     * @param statoPrenotazione stato della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndMedicoId(PrenotazioneStatusEnum statoPrenotazione, Long medicoId);

    /**
     * Ricerca le prenotazioni per stato prenotazione e id del paziente
     * @param statoPrenotazione stato della prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndPazienteId(PrenotazioneStatusEnum statoPrenotazione, Long pazienteId);

    /**
     * Ricerca le prenotazioni per stato prenotazione e id del segretario
     * @param statoPrenotazione stato della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            INNER JOIN segretario s
            WHERE pr.statoPrenotazione = :statoPrenotazione
            AND s.id = :segretarioId""")
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndSegretarioId(@Param("statoPrenotazione") PrenotazioneStatusEnum statoPrenotazione,
                                                                                @Param("segretarioId") Long segretarioId);


}
