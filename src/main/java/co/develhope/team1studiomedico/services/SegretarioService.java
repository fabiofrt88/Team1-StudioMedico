package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.entities.SegretarioEntity;
import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * La classe SegretarioService realizza la logica di business relativamente le operazioni di CRUD dei dati di SegretarioEntity.
 * Utilizza SegretarioRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller SegretarioController
 */
@Service
public class SegretarioService {

    @Autowired
    private SegretarioRepository segretarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(SegretarioService.class);


    /**
     * Metodo che crea il segretario.
     *
     * @param segretario il segretario
     */
    public SegretarioEntity createSegretario(SegretarioEntity segretario) {
        try {
            logger.info("Inizio processo createSegretario in SegretarioService");
            segretario.setId(null);
            segretario.setRecordStatus(EntityStatusEnum.ACTIVE);
            return segretarioRepository.saveAndFlush(segretario);
        } finally {
            logger.info("Fine processo createSegretario in SegretarioService");
        }
    }

    /**
     * Metodo che restituisce i segretari con record status ACTIVE.
     *
     * @return i segretari con record status ACTIVE
     */
    public List<SegretarioEntity> getAllSegretari() {
        return segretarioRepository.findByRecordStatus(EntityStatusEnum.ACTIVE);
    }

    /**
     * Metodo che restituisce i segretari cancellati logicamente con record status DELETED.
     *
     * @return i segretari cancellati logicamente con record status DELETED.
     */
    public List<SegretarioEntity> getAllDeletedSegretari() {
        return segretarioRepository.findByRecordStatus(EntityStatusEnum.DELETED);
    }

    /**
     * Metodo che restituisce il segretario tramite id.
     *
     * @param id l' id
     * @return il segretario tramite id
     */
    public SegretarioEntity getSegretarioById(Long id) {
        if(!segretarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Segretario non trovato");
        }
        return segretarioRepository.findById(id).get();
    }

    /**
     * Metodo che modifica il segretario tramite id.
     *
     * @param segretarioEdit il segretario edit
     * @param id             l'id
     */
    public SegretarioEntity updateSegretarioById(SegretarioEntity segretarioEdit, Long id) {
        if(!segretarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Segretario non trovato");
        }

        SegretarioEntity segretario = segretarioRepository.findById(id).get();

        if(segretarioEdit.getNome() != null) {
            segretario.setNome(segretarioEdit.getNome());
        }
        if(segretarioEdit.getCognome() != null) {
            segretario.setCognome(segretarioEdit.getCognome());
        }
        if(segretarioEdit.getTelefono() != null) {
            segretario.setTelefono(segretarioEdit.getTelefono());
        }
        if(segretarioEdit.getEmail() != null) {
            segretario.setEmail(segretarioEdit.getEmail());
        }

        return segretarioRepository.saveAndFlush(segretario);
    }

    /**
     * Metodo che cancella il segretario tramite id (soft delete)
     *
     * @param id l'id
     */
    public void deleteSegretarioById(Long id) {
        try {
            logger.info("Inizio processo deleteSegretarioById in SegretarioService");
            if(!segretarioRepository.existsById(id)) {
                throw new EntityNotFoundException("Segretario non trovato");
            }
            segretarioRepository.softDeleteById(id);
        } finally {
            logger.info("Fine processo deleteSegretarioById in SegretarioService");
        }
    }

    /**
     * Metodo che cancella tutti i segretari (soft delete)
     */
    public void deleteAllSegretari() {
        try {
            logger.info("Inizio processo deleteAllSegretari in SegretarioService");
            segretarioRepository.softDelete();
        } finally {
            logger.info("Fine processo deleteAllSegretari in SegretarioService");
        }
    }

    /**
     * Metodo che ripristina il segretario tramite id.
     *
     * @param id l'id
     */
    public void restoreSegretarioById(Long id) {
        if(!segretarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Segretario non trovato");
        }
        segretarioRepository.restoreById(id);
    }

    /**
     * Metodo che ripristina tutti i segretari.
     */
    public void restoreAllSegretari() {
        segretarioRepository.restore();
    }

}