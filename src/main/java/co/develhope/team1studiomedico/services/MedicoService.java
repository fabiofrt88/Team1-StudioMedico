package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.repositories.MedicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe MedicoService realizza la logica di business relativamente le operazioni di CRUD dei dati di MedicoEntity.
 * Utilizza MedicoRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller MedicoController
 */
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    private static final Logger logger = LoggerFactory.getLogger(MedicoService.class);

    /**
     * Metodo che crea il medico.
     *
     * @param medico il medico
     */
    public MedicoEntity createMedico(MedicoEntity medico) {
        try {
            logger.info("Inizio processo createMedico in MedicoService");
            medico.setId(null);
            medico.setRecordStatus(EntityStatusEnum.ACTIVE);
            return medicoRepository.saveAndFlush(medico);
        } finally {
            logger.info("Fine processo createMedico in MedicoService");
        }
    }

    /**
     * Metodo che restituisce i medici con record status ACTIVE.
     *
     * @return i medici con record status ACTIVE
     */
    public List<MedicoEntity> getAllMedici() {
        return medicoRepository.findByRecordStatus(EntityStatusEnum.ACTIVE);
    }

    /**
     * Metodo che restituisce i medici cancellati logicamente con record status DELETED.
     *
     * @return i medici cancellati logicamente con record status DELETED.
     */
    public List<MedicoEntity> getAllDeletedMedici() {
        return medicoRepository.findByRecordStatus(EntityStatusEnum.DELETED);
    }

    /**
     * Metodo che restituisce il medico tramite id.
     *
     * @param id l' id
     * @return il medico tramite id
     */
    public MedicoEntity getMedicoById(Long id) {
        /*if(!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medico non trovato");
        }
        return medicoRepository.findById(id).get();*/
        return medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medico con id " + id + " non trovato"));
    }

    /**
     * Metodo che modifica il medico.
     *
     * @param medicoEdit il medico edit
     * @param id         l'id
     */
    public MedicoEntity updateMedicoById(MedicoEntity medicoEdit, Long id) {
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

        return medicoRepository.saveAndFlush(medico);
    }

    /**
     * Metodo che cancella il medico tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deleteMedicoById(Long id) {
        try {
            logger.info("Inizio processo deleteMedicoById in MedicoService");
            if(!medicoRepository.existsById(id)) {
                throw new EntityNotFoundException("Medico non trovato");
            }
            medicoRepository.softDeleteById(id);
        } finally {
            logger.info("Fine processo deleteMedicoById in MedicoService");
        }
    }

    /**
     * Metodo che cancella tutti i medici (soft delete)
     */
    public void deleteAllMedici() {
        try {
            logger.info("Inizio processo deleteAllMedici in MedicoService");
            medicoRepository.softDelete();
        } finally {
            logger.info("Fine processo deleteAllMedici in MedicoService");
        }
    }

    /**
     * Metodo che ripristina il medico tramite id.
     *
     * @param id l'id
     */
    public void restoreMedicoById(Long id) {
        if(!medicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Medico non trovato");
        }
        medicoRepository.restoreById(id);
    }

    /**
     * Metodo che ripristina tutti i medici.
     */
    public void restoreAllMedici() {
        medicoRepository.restore();
    }

}
