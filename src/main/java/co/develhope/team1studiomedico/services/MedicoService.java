package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * La classe MedicoService realizza la logica di business relativamente le operazioni di CRUD dei dati di MedicoEntity.
 * Utilizza MedicoRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller MedicoController
 */
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    Logger logger = LoggerFactory.getLogger(MedicoService.class);

    /**
     * Metodo che crea il medico.
     *
     * @param medico il medico
     */
    public void createMedico(MedicoEntity medico) {
        try {
            logger.info("Inizio processo createMedico in Service");
            medico.setId(null);
            medico.setStatus(EntityStatusEnum.ACTIVE);
            medicoRepository.saveAndFlush(medico);
        }finally {
            logger.info("Fine processo createMedico in Service");
        }
    }

    /**
     * Metodo che restituisce i medici.
     *
     * @return i medici
     */
    public List<MedicoEntity> getMedici() {
        return medicoRepository.findAll();
    }

    /**
     * Metodo che restituisce il medico tramite id.
     *
     * @param id l' id
     * @return il medico tramite id
     */
    public MedicoEntity getMedicoById(Long id) {
        if(!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medico non trovato");
        }
        return medicoRepository.findById(id).get();
    }

    /**
     * Metodo che modifica il medico.
     *
     * @param medicoEdit il medico edit
     * @param id         l'id
     */
    public void updateMedicoById(MedicoEntity medicoEdit, Long id) {
        if(medicoEdit == null) {
            throw new IllegalArgumentException();
        }
        if(!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medico non trovato");
        }

        MedicoEntity medico = medicoRepository.findById(id).get();

        if(medicoEdit.getNome() != null) {
            medico.setNome(medicoEdit.getNome());
        }
        if(medicoEdit.getCognome() != null) {
            medico.setCognome(medicoEdit.getCognome());
        }
        if(medicoEdit.getTelefono() != null) {
            medico.setTelefono(medicoEdit.getTelefono());
        }
        if(medicoEdit.getEmail() != null) {
            medico.setEmail(medicoEdit.getEmail());
        }

        medicoRepository.saveAndFlush(medico);
    }

    /**
     * Metodo che ripristina il medico tramite id.
     *
     * @param id l'id
     */
    public void restoreMedicoById(Long id){
        if(!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medico non trovato");
        }
        medicoRepository.restoreById(id);
    }

    /**
     * Metodo che ripristina tutti i medici.
     */
    public void restoreAllMedici(){
        medicoRepository.restore();
    }

    /**
     * Metodo che cancella il medico tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deleteMedicoById(Long id) {
        try {
            logger.info("Inizio processo deleteMedicoById in Service");
            if(!medicoRepository.existsById(id)) {
                throw new NotFoundException("Medico non trovato");
            }
            medicoRepository.softDeleteById(id);
        }finally {
            logger.info("Fine processo deleteMedicoById in Service");
        }
    }

    /**
     * Metodo che cancella tutti i medici (soft delete)
     */
    public void deleteMedici() {
        try {
            logger.info("Inizio processo deleteMedici in Service");
            medicoRepository.softDelete();
        }finally {
            logger.info("Fine processo deleteMedici in Service");
        }
    }

}
