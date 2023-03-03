package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.SegretarioEntity;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * La classe SegretarioService e dove si effettuano le operazioni di logica di businness che verranno richiamate
 * dal CRUD del SegretarioController.
 */
@Service
public class SegretarioService {

    @Autowired
    private SegretarioRepository segretarioRepository;

    Logger logger = LoggerFactory.getLogger(SegretarioService.class);


    /**
     * Funzione che crea il segretario.
     *
     * @param segretario il segretario
     */
    public void createSegretario(SegretarioEntity segretario) {
        try {
            logger.info("Inizio processo createSegretario in Service");
            segretario.setId(null);
            segretario.setStatus(EntityStatusEnum.ACTIVE);
            segretarioRepository.saveAndFlush(segretario);
        }finally {
            logger.info("Fine processo createSegretario in Service");
        }
    }

    /**
     * Funzione che ottiene il segretario tramite id.
     *
     * @param id l' id
     * @return il segretario tramite id
     */
    public SegretarioEntity getSegretarioById(Long id) {
        if(!segretarioRepository.existsById(id)) {
            throw new NotFoundException("Segretario non trovato");
        }
        return segretarioRepository.findById(id).get();
    }

    /**
     * Funzione che ottiene i segretari.
     *
     * @return i segretari
     */
    public List<SegretarioEntity> getSegretari() {
        return segretarioRepository.findAll();
    }

    /**
     * Funzione che modifica il segretario tramite id.
     *
     * @param segretarioEdit il segretario edit
     * @param id             l'id
     */
    public void updateSegretarioById(SegretarioEntity segretarioEdit, Long id) {
        if(segretarioEdit == null) {
            throw new IllegalArgumentException();
        }
        if(!segretarioRepository.existsById(id)) {
            throw new NotFoundException("Segretario non trovato");
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

        segretarioRepository.saveAndFlush(segretario);
    }

    /**
     * Funzione che ripristina il segretario tramite id.
     *
     * @param id l'id
     */
    public void restoreSegretarioById(Long id){
        if(!segretarioRepository.existsById(id)) {
            throw new NotFoundException("Segretario non trovato");
        }
        segretarioRepository.restoreById(id);
    }

    /**
     * Funzione che ripristina tutti i segretari.
     */
    public void restoreAllSegretari(){
        segretarioRepository.restore();
    }

    /**
     * Funzione che cancella il segretario tramite id.
     *
     * @param id l'id
     */
    public void deleteSegretarioById(Long id) {
        try {
            logger.info("Inizio processo deleteSegretarioById in Service");
            if(!segretarioRepository.existsById(id)) {
                throw new NotFoundException("Segretario non trovato");
            }
            segretarioRepository.deleteById(id);
        }finally {
            logger.info("Fine processo deleteSegretarioById in Service");
        }
    }

    /**
     * Funzione che cancella tutti i segretari.
     */
    public void deleteSegretari() {
        try {
            logger.info("Inizio processo deleteSegretari in Service");
            segretarioRepository.deleteAll();
        }finally {
            logger.info("Fine processo deleteSegretari in Service");
        }
    }

}