package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import co.develhope.team1studiomedico.repositories.PrenotazioneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe PrenotazioneService realizza la logica di business relativamente le operazioni di CRUD dei dati di PrenotazioneEntity.
 * Utilizza PrenotazioneRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PrenotazioneController
 */
@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneService.class);

    /**
     * Metodo che crea la prenotazione.
     *
     * @param prenotazione la prenotazione
     */
    public PrenotazioneEntity createPrenotazione(PrenotazioneEntity prenotazione) {
        try {
            logger.info("Inizio processo createPrenotazione in PrenotazioneService");
            prenotazione.setId(null);
            prenotazione.setRecordStatus(EntityStatusEnum.ACTIVE);
            //prenotazione.setStatoPrenotazione(PrenotazioneStatusEnum.PENDING);
            return prenotazioneRepository.saveAndFlush(prenotazione);
        } finally {
            logger.info("Fine processo createPrenotazione in PrenotazioneService");
        }
    }

    /**
     * Metodo che restituisce le prenotazioni con record status ACTIVE.
     *
     * @return le prenotazioni con record status ACTIVE
     */
    public List<PrenotazioneEntity> getAllPrenotazioni() {
        return prenotazioneRepository.findByRecordStatus(EntityStatusEnum.ACTIVE);
    }

    /**
     * Metodo che restituisce le prenotazioni cancellate logicamente con record status DELETED.
     *
     * @return le prenotazioni cancellate logicamente con record status DELETED.
     */
    public List<PrenotazioneEntity> getAllDeletedPrenotazioni() {
        return prenotazioneRepository.findByRecordStatus(EntityStatusEnum.DELETED);
    }

    /**
     * Metodo che restituisce la prenotazione tramite id.
     *
     * @param id l' id
     * @return la prenotazione tramite id
     */
    public PrenotazioneEntity getPrenotazioneById(Long id) {
        if(!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        return prenotazioneRepository.findById(id).get();
    }

    /**
     * Metodo che modifica la prenotazione.
     *
     * @param prenotazioneEdit la prenotazione edit
     * @param id         l'id
     */
    public PrenotazioneEntity updatePrenotazioneById(PrenotazioneEntity prenotazioneEdit, Long id) {
        if(!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }

        PrenotazioneEntity prenotazione = prenotazioneRepository.findById(id).get();

        if(prenotazioneEdit.getDataPrenotazione() != null) {
            prenotazione.setDataPrenotazione(prenotazioneEdit.getDataPrenotazione());
        }
        if(prenotazioneEdit.getOraPrenotazione() != null) {
            prenotazione.setOraPrenotazione(prenotazioneEdit.getOraPrenotazione());
        }
        if(prenotazioneEdit.getStatoPrenotazione() != null) {
            prenotazione.setStatoPrenotazione(prenotazioneEdit.getStatoPrenotazione());
        }

        return prenotazioneRepository.saveAndFlush(prenotazione);
    }

    /**
     * Metodo che cancella la prenotazione tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deletePrenotazioneById(Long id) {
        try {
            logger.info("Inizio processo deletePrenotazioneById in PrenotazioneService");
            if(!prenotazioneRepository.existsById(id)) {
                throw new EntityNotFoundException("Prenotazione non trovata");
            }
            prenotazioneRepository.softDeleteById(id);
        } finally {
            logger.info("Fine processo deletePrenotazioneById in PrenotazioneService");
        }
    }

    /**
     * Metodo che cancella tutte le prenotazioni (soft delete)
     */
    public void deleteAllPrenotazioni() {
        try {
            logger.info("Inizio processo deleteAllPrenotazioni in PrenotazioneService");
            prenotazioneRepository.softDelete();
        } finally {
            logger.info("Fine processo deleteAllPrenotazioni in PrenotazioneService");
        }
    }

    /**
     * Metodo che ripristina la prenotazione tramite id.
     *
     * @param id l'id
     */
    public void restorePrenotazioneById(Long id) {
        if(!prenotazioneRepository.existsById(id)) {
            throw new EntityNotFoundException("Prenotazione non trovata");
        }
        prenotazioneRepository.restoreById(id);
    }

    /**
     * Metodo che ripristina tutti le prenotazioni.
     */
    public void restoreAllPrenotazioni() {
        prenotazioneRepository.restore();
    }

}
