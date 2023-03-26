package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;
import org.springframework.data.domain.Sort;
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
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return la lista delle prenotazioni filtrate per record status
     */
    List<PrenotazioneEntity> findByRecordStatus(EntityStatusEnum recordStatus, Sort sort);

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
     * Restituisce il numero delle prenotazioni della data considerata
     * @param dataPrenotazione data di prenotazione
     * @return il numero delle prenotazioni della data considerata
     */
    Integer countPrenotazioniByDataPrenotazione(LocalDate dataPrenotazione);

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return il numero delle prenotazioni della data considerata collegate all'id del medico
     */
    Integer countPrenotazioniByDataPrenotazioneAndMedicoId(LocalDate dataPrenotazione, Long medicoId);

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return il numero delle prenotazioni della data considerata collegate all'id del segretario
     */
    @Query("""
            SELECT COUNT(pr) FROM prenotazione pr
            INNER JOIN segretario s
            WHERE pr.dataPrenotazione = :dataPrenotazione
            AND s.id = :segretarioId""")
    Integer countPrenotazioniByDataPrenotazioneAndSegretarioId(LocalDate dataPrenotazione, Long segretarioId);

    /**
     * Ricerca le prenotazioni a partire dall'id del medico (foreign key medicoId in prenotazione)
     * @param medicoId id del medico
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per id medico
     */
    List<PrenotazioneEntity> findPrenotazioniByMedicoId(Long medicoId, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dall'id del paziente (foreign key pazienteId in prenotazione)
     * @param pazienteId id del paziente
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per id paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByPazienteId(Long pazienteId, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per id segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s
            WHERE s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBySegretarioId(@Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione
     * @param dataPrenotazione data di prenotazione
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per data di prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazione(LocalDate dataPrenotazione, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dalla data e dall'ora della prenotazione
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per data e ora della prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndOraPrenotazione(LocalDate dataPrenotazione, LocalTime oraPrenotazione, Sort sort);

    /**
     * Ricerca le prenotazioni nell'intervallo di due date considerate
     * @param startDate data inizio
     * @param endDate data fine
     * @return lista delle prenotazioni nell'intervallo di due date considerate
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE pr.dataPrenotazione BETWEEN :startDate AND :endDate 
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazione(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Ricerca le prenotazioni per stato prenotazione
     * @param statoPrenotazione stato della prenotazione
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per stato prenotazione
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndMedicoId(LocalDate dataPrenotazione, Long medicoId, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del paziente
     * @param dataPrenotazione data di prenotazione
     * @param pazienteId id del paziente
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndPazienteId(LocalDate dataPrenotazione, Long pazienteId, Sort sort);

    /**
     * Ricerca le prenotazioni a partire dalla data di prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s
            WHERE pr.dataPrenotazione = :dataPrenotazione 
            AND s.id = :segretarioId 
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndSegretarioId(@Param("dataPrenotazione") LocalDate dataPrenotazione, @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param medicoId id del medico
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long medicoId, Sort sort);

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
            AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
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
            AND pr.medico.id = :medicoId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
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
            AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
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
            AND pr.paziente.id = :pazienteId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenDatePrenotazioneAndPazienteId(@Param("startDate") LocalDate startDate,
                                                                                @Param("endDate") LocalDate endDate,
                                                                                @Param("pazienteId") Long pazienteId);

    /**
     * Ricerca le prenotazioni per stato prenotazione e id del medico
     * @param statoPrenotazione stato della prenotazione
     * @param medicoId id del medico
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del medico
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndMedicoId(PrenotazioneStatusEnum statoPrenotazione, Long medicoId, Sort sort);

    /**
     * Ricerca le prenotazioni per stato prenotazione e id del paziente
     * @param statoPrenotazione stato della prenotazione
     * @param pazienteId id del paziente
     * @param sort parametro che abilita opzioni per il dynamic sorting della query
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del paziente
     */
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndPazienteId(PrenotazioneStatusEnum statoPrenotazione, Long pazienteId, Sort sort);

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
            AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByStatoPrenotazioneAndSegretarioId(@Param("statoPrenotazione") PrenotazioneStatusEnum statoPrenotazione,
                                                                                @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni per anno (year)
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per anno (year)
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE YEAR(pr.dataPrenotazione) = :year
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByYear(@Param("year") Integer year);

    /**
     * Ricerca le prenotazioni per anno (year) e id del medico
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per anno (year) e id del medico
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE YEAR(pr.dataPrenotazione) = :year 
            AND pr.medico.id = :medicoId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByYearAndMedicoId(@Param("year") Integer year, @Param("medicoId") Long medicoId);

    /**
     * Ricerca le prenotazioni per anno (year) e id del segretario
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per anno (year) e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr
            INNER JOIN segretario s 
            WHERE YEAR(pr.dataPrenotazione) = :year 
            AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByYearAndSegretarioId(@Param("year") Integer year, @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni per anno (year) e id del paziente
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per anno (year) e id del paziente
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE YEAR(pr.dataPrenotazione) = :year 
            AND pr.paziente.id = :pazienteId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByYearAndPazienteId(@Param("year") Integer year, @Param("pazienteId") Long pazienteId);

    /**
     * Ricerca le prenotazioni per mese (month) e anno (year)
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per mese (month) e anno (year)
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE MONTH(pr.dataPrenotazione) = :month
            AND YEAR(pr.dataPrenotazione) = :year
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

    /**
     * Ricerca le prenotazioni per mese (month), anno (year) e id del medico
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del medico
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE MONTH(pr.dataPrenotazione) = :month
            AND YEAR(pr.dataPrenotazione) = :year
            AND pr.medico.id = :medicoId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByMonthAndYearAndMedicoId(@Param("month") Integer month, @Param("year") Integer year, @Param("medicoId") Long medicoId);

    /**
     * Ricerca le prenotazioni per mese (month), anno (year) e id del segretario
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            INNER JOIN segretario s
            WHERE MONTH(pr.dataPrenotazione) = :month
            AND YEAR(pr.dataPrenotazione) = :year
            AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByMonthAndYearAndSegretarioId(@Param("month") Integer month, @Param("year") Integer year, @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni per mese (month), anno (year) e id del paziente
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del paziente
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE MONTH(pr.dataPrenotazione) = :month
            AND YEAR(pr.dataPrenotazione) = :year
            AND pr.paziente.id = :pazienteId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniByMonthAndYearAndPazienteId(@Param("month") Integer month, @Param("year") Integer year, @Param("pazienteId") Long pazienteId);

    /**
     * Ricerca le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE (YEAR(pr.dataPrenotazione) = :fromYear AND MONTH(pr.dataPrenotazione) BETWEEN :fromMonth AND 12) OR
                  (YEAR(pr.dataPrenotazione) = :toYear AND MONTH(pr.dataPrenotazione) BETWEEN 1 AND :toMonth)
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenMonthsAndYears(@Param("fromMonth") Integer fromMonth,
                                                                   @Param("toMonth") Integer toMonth,
                                                                   @Param("fromYear") Integer fromYear,
                                                                   @Param("toYear") Integer toYear);

    /**
     * Ricerca le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del medico
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del medico
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE (YEAR(pr.dataPrenotazione) = :fromYear AND MONTH(pr.dataPrenotazione) BETWEEN :fromMonth AND 12) OR
                  (YEAR(pr.dataPrenotazione) = :toYear AND MONTH(pr.dataPrenotazione) BETWEEN 1 AND :toMonth)
                  AND pr.medico.id = :medicoId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenMonthsAndYearsAndMedicoId(@Param("fromMonth") Integer fromMonth,
                                                                              @Param("toMonth") Integer toMonth,
                                                                              @Param("fromYear") Integer fromYear,
                                                                              @Param("toYear") Integer toYear,
                                                                              @Param("medicoId") Long medicoId);

    /**
     * Ricerca le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del segretario
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del segretario
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            INNER JOIN segretario s
            WHERE (YEAR(pr.dataPrenotazione) = :fromYear AND MONTH(pr.dataPrenotazione) BETWEEN :fromMonth AND 12) OR
                  (YEAR(pr.dataPrenotazione) = :toYear AND MONTH(pr.dataPrenotazione) BETWEEN 1 AND :toMonth)
                  AND s.id = :segretarioId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenMonthsAndYearsAndSegretarioId(@Param("fromMonth") Integer fromMonth,
                                                                              @Param("toMonth") Integer toMonth,
                                                                              @Param("fromYear") Integer fromYear,
                                                                              @Param("toYear") Integer toYear,
                                                                              @Param("segretarioId") Long segretarioId);

    /**
     * Ricerca le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del paziente
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del paziente
     */
    @Query("""
            SELECT pr FROM prenotazione pr 
            WHERE (YEAR(pr.dataPrenotazione) = :fromYear AND MONTH(pr.dataPrenotazione) BETWEEN :fromMonth AND 12) OR
                  (YEAR(pr.dataPrenotazione) = :toYear AND MONTH(pr.dataPrenotazione) BETWEEN 1 AND :toMonth)
                  AND pr.paziente.id = :pazienteId
            ORDER BY pr.dataPrenotazione, pr.oraPrenotazione""")
    List<PrenotazioneEntity> findPrenotazioniBetweenMonthsAndYearsAndPazienteId(@Param("fromMonth") Integer fromMonth,
                                                                              @Param("toMonth") Integer toMonth,
                                                                              @Param("fromYear") Integer fromYear,
                                                                              @Param("toYear") Integer toYear,
                                                                              @Param("pazienteId") Long pazienteId);

}
