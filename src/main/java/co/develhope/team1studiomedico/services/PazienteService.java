package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * La classe PazienteService e dove si effettuano le operazioni di logica di businness che verranno richiamate
 * dal CRUD del PazienteController.
 */
@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    Logger logger = LoggerFactory.getLogger(PazienteService.class);


    /**
     * Funzione che crea il paziente.
     *
     * @param paziente il paziente
     */
    public void createPaziente(PazienteEntity paziente) {
        try {
            logger.info("Inizio processo createPaziente in Service");
            paziente.setId(null);
            paziente.setStatus(EntityStatusEnum.ACTIVE);
            pazienteRepository.saveAndFlush(paziente);
        }finally {
            logger.info("Fine processo createPaziente in Service");
        }
    }

    /**
     * Funzione che ottiene i pazienti.
     *
     * @return i pazienti
     */
    public List<PazienteEntity> getPazienti() {
        return pazienteRepository.findAll();
    }

    /**
     * Funzione che ottiene il paziente tramite id.
     *
     * @param id the id
     * @return the paziente by id
     */
    public PazienteEntity getPazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
        }
        return pazienteRepository.findById(id).get();
    }

    /**
     * Funzione che modifica il paziente tramite id.
     *
     * @param pazienteEdit the paziente edit
     * @param id           the id
     */
    public void updatePazienteById(PazienteEntity pazienteEdit, Long id) {
        if(pazienteEdit == null) {
            throw new IllegalArgumentException();
        }
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
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

        pazienteRepository.saveAndFlush(paziente);
    }

    /**
     * Funzione che ripristina il paziente tramite id.
     *
     * @param id l'id
     */
    public void restorePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
        }
        pazienteRepository.restoreById(id);
        //pazienteRepository.changeStatusById(EntityStatusEnum.ACTIVE, id);
    }

    /**
     * Funzione che ripristina i pazienti.
     */
    public void restorePazienti() {
        pazienteRepository.restore();
    }

    /**
     * Funzione che cancella il paziente tramite id.
     *
     * @param id l'id
     */
    public void deletePazienteById(Long id) {
        try {
            logger.info("Inizio processo deletePazienteById in Service");
            if(!pazienteRepository.existsById(id)) throw new NotFoundException("Paziente non trovato");
        /*PazienteEntity paziente = pazienteRepository.findById(id).get();
        paziente.setStatus(EntityStatusEnum.DELETED);
        pazienteRepository.saveAndFlush(paziente);*/
            pazienteRepository.softDeleteById(id);
            //pazienteRepository.changeStatusById(EntityStatusEnum.DELETED, id);
        }finally {
            logger.info("Fine processo deletePazienteById in Service");
        }
    }

    /**
     * Funzione che cancella i pazienti.
     */
    public void deletePazienti() {
        try {
            logger.info("Inizio processo deletePazienti in Service");
            pazienteRepository.softDelete();
        }finally {
            logger.info("Fine processo deletePazienti in Service");
        }
    }

}
