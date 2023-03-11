package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe PazienteService realizza la logica di business relativamente le operazioni di CRUD dei dati di PazienteEntity.
 * Utilizza PazienteRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PazienteController
 */
@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(PazienteService.class);

    /**
     * Metodo che crea il paziente.
     *
     * @param paziente il paziente
     */
    public PazienteEntity createPaziente(PazienteEntity paziente) {
        try {
            logger.info("Inizio processo createPaziente in PazienteService");
            paziente.setId(null);
            paziente.setRecordStatus(EntityStatusEnum.ACTIVE);
            return pazienteRepository.saveAndFlush(paziente);
        } finally {
            logger.info("Fine processo createPaziente in PazienteService");
        }
    }

    /**
     * Metodo che restituisce i pazienti con record status ACTIVE.
     *
     * @return i pazienti con record status ACTIVE
     */
    public List<PazienteEntity> getAllPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.ACTIVE);
    }

    /**
     * Metodo che restituisce i pazienti cancellati logicamente con record status DELETED.
     *
     * @return i pazienti cancellati logicamente con record status DELETED.
     */
    public List<PazienteEntity> getAllDeletedPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.DELETED);
    }

    /**
     * Metodo che restituisce il paziente tramite id.
     *
     * @param id the id
     * @return the paziente by id
     */
    public PazienteEntity getPazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paziente non trovato");
        }
        return pazienteRepository.findById(id).get();
    }

    /**
     * Metodo che modifica il paziente tramite id.
     *
     * @param pazienteEdit the paziente edit
     * @param id           the id
     */
    public PazienteEntity updatePazienteById(PazienteEntity pazienteEdit, Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paziente non trovato");
        }

        PazienteEntity paziente = pazienteRepository.findById(id).get();
        /*Paziente paziente = new Paziente();
        Optional<Paziente> pazienteOptional = pazienteRepository.findById(id);
        if(pazienteOptional.isPresent()) paziente = pazienteOptional.get();*/

        if(pazienteEdit.getNome() != null) {
            paziente.setNome(pazienteEdit.getNome());
        }
        if(pazienteEdit.getCognome() != null) {
            paziente.setCognome(pazienteEdit.getCognome());
        }
        if(pazienteEdit.getDataNascita() != null) {
            paziente.setDataNascita(pazienteEdit.getDataNascita());
        }
        if(pazienteEdit.getTelefono() != null) {
            paziente.setTelefono(pazienteEdit.getTelefono());
        }
        if(pazienteEdit.getEmail() != null) {
            paziente.setEmail(pazienteEdit.getEmail());
        }
        if(pazienteEdit.getCodiceFiscale() != null) {
            paziente.setCodiceFiscale(pazienteEdit.getCodiceFiscale());
        }

        return pazienteRepository.saveAndFlush(paziente);
    }

    /**
     * Metodo che cancella il paziente tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deletePazienteById(Long id) {
        try {
            logger.info("Inizio processo deletePazienteById in PazienteService");
            if(!pazienteRepository.existsById(id)) throw new EntityNotFoundException("Paziente non trovato");
                /*PazienteEntity paziente = pazienteRepository.findById(id).get();
                paziente.setStatus(EntityStatusEnum.DELETED);
                pazienteRepository.saveAndFlush(paziente);*/
                pazienteRepository.softDeleteById(id);
                //pazienteRepository.changeStatusById(EntityStatusEnum.DELETED, id);
        } finally {
            logger.info("Fine processo deletePazienteById in PazienteService");
        }
    }

    /**
     * Metodo che cancella i pazienti (soft delete).
     */
    public void deleteAllPazienti() {
        try {
            logger.info("Inizio processo deleteAllPazienti in PazienteService");
            pazienteRepository.softDelete();
        } finally {
            logger.info("Fine processo deleteAllPazienti in PazienteService");
        }
    }

    /**
     * Metodo che ripristina il paziente tramite id.
     *
     * @param id l'id
     */
    public void restorePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paziente non trovato");
        }
        pazienteRepository.restoreById(id);
        //pazienteRepository.changeStatusById(EntityStatusEnum.ACTIVE, id);
    }

    /**
     * Metodo che ripristina i pazienti.
     */
    public void restoreAllPazienti() {
        pazienteRepository.restore();
    }

}
