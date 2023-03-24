package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.PazienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * L'interfaccia PazienteRepository è un repository che realizza la logica di persistenza dei dati di PazienteEntity,
 * estende la superInterface PersonaRepository e ne eredita metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface PazienteRepository extends PersonaRepository<PazienteEntity> {

    /**
     * Ricerca i pazienti a partire dall'id del medico (foreign key medicoId in paziente)
     * @param medicoId id del medico
     * @return lista di pazienti filtrati per id medico
     */
    List<PazienteEntity> findPazientiByMedicoId(Long medicoId);

    /**
     * Ricerca i pazienti a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista di pazienti filtrati per id segretario
     */
    @Query("""
            SELECT p FROM paziente p
            INNER JOIN segretario s
            WHERE s.id = :segretarioId""")
    List<PazienteEntity> findPazientiBySegretarioId(@Param("segretarioId") Long segretarioId);

    /**
     * Ricerca il paziente a partire dall'id della prenotazione
     * (foreign key pazienteId in prenotazione)
     * @param prenotazioneId id della prenotazione
     * @return il paziente
     */
    @Query("""
            SELECT p FROM paziente p
            INNER JOIN prenotazione pr
            WHERE pr.id = :prenotazioneId""")
    Optional<PazienteEntity> findPazienteByPrenotazioneId(@Param("prenotazioneId") Long prenotazioneId);

}
