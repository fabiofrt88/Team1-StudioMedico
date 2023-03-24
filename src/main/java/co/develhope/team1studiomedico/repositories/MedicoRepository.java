package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * L'interfaccia MedicoRepository è un repository che realizza la logica di persistenza dei dati di MedicoEntity,
 * estende la superInterface PersonaRepository e ne eredita metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface MedicoRepository extends PersonaRepository<MedicoEntity> {

    /**
     * Ricerca il medico a partire dall'id del segretario (foreign key medicoId in segretario)
     * @param segretarioId id del segretario
     * @return il medico
     */
    @Query(value = """
            SELECT m FROM medico m
            INNER JOIN segretario s
            WHERE s.id = :segretarioId""")
    Optional<MedicoEntity> findMedicoBySegretarioId(@Param("segretarioId") Long segretarioId);

    /**
     * Ricerca il medico a partire dall'id del paziente (foreign key medicoId in paziente)
     * @param pazienteId id del paziente
     * @return il medico
     */
    @Query("""
            SELECT m FROM medico m
            INNER JOIN paziente p
            WHERE p.id = :pazienteId""")
    Optional<MedicoEntity> findMedicoByPazienteId(@Param("pazienteId") Long pazienteId);

    /**
     * Ricerca il medico a partire dall'id della prenotazione
     * (foreign key medicoId in prenotazione)
     * @param prenotazioneId id della prenotazione
     * @return il medico
     */
    @Query("""
            SELECT m FROM medico m
            INNER JOIN prenotazione pr
            WHERE pr.id = :prenotazioneId""")
    Optional<MedicoEntity> findMedicoByPrenotazioneId(@Param("prenotazioneId") Long prenotazioneId);

}
