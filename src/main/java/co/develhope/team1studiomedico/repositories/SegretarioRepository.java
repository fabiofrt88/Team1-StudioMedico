package co.develhope.team1studiomedico.repositories;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.entities.SegretarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * L'interfaccia SegretarioRepository è un repository che realizza la logica di persistenza dei dati di SegretarioEntity,
 * estende la superInterface PersonaRepository e ne eredita metodi e
 * custom query (query personalizzate che si utilizzano per azioni più specifiche)
 */
@Repository
public interface SegretarioRepository extends PersonaRepository<SegretarioEntity> {

    /**
     * Ricerca il segretario a partire dall'id del medico (foreign key in segretario)
     * @param medicoId id del medico
     * @return il segretario
     */
    Optional<SegretarioEntity> findSegretarioByMedicoId(Long medicoId);

    /**
     * Ricerca il segretario a partire dall'id del paziente,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param pazienteId id del paziente
     * @return il segretario
     */
    @Query(value = """
            SELECT s FROM segretario s
            INNER JOIN paziente p
            WHERE p.id = :pazienteId""")
    Optional<SegretarioEntity> findSegretarioByPazienteId(@Param("pazienteId") Long pazienteId);

    /**
     * Ricerca il segretario a partire dall'id della prenotazione,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param prenotazioneId id della prenotazione
     * @return il segretario
     */
    @Query("""
            SELECT s FROM segretario s
            INNER JOIN prenotazione pr
            WHERE pr.id = :prenotazioneId""")
    Optional<SegretarioEntity> findSegretarioByPrenotazioneId(@Param("prenotazioneId") Long prenotazioneId);

}
