package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.MedicoCreateDTO;
import co.develhope.team1studiomedico.dto.MedicoDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.repositories.MedicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(MedicoService.class);

    /**
     * Metodo che crea il medico.
     *
     * @param medicoCreateDTO il DTO di creazione del medico
     */
    @Transactional
    public MedicoDTO createMedico(@NotNull MedicoCreateDTO medicoCreateDTO) {
        try {
            logger.info("Inizio processo createMedico in MedicoService");
            MedicoEntity medico = convertToEntity(medicoCreateDTO);
            medico.setId(null);
            medico.setRecordStatus(EntityStatusEnum.ACTIVE);
            medico = medicoRepository.saveAndFlush(medico);
            entityManager.refresh(medico);
            return convertToDTO(medico);
        } finally {
            logger.info("Fine processo createMedico in MedicoService");
        }
    }

    /**
     * Metodo che restituisce i medici con record status ACTIVE.
     *
     * @return i medici con record status ACTIVE
     */
    public List<MedicoDTO> getAllMedici() {
        return medicoRepository.findByRecordStatus(EntityStatusEnum.ACTIVE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce i medici cancellati logicamente con record status DELETED.
     *
     * @return i medici cancellati logicamente con record status DELETED.
     */
    public List<MedicoDTO> getAllDeletedMedici() {
        return medicoRepository.findByRecordStatus(EntityStatusEnum.DELETED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce il medico tramite id.
     *
     * @param id l' id
     * @return il medico tramite id
     */
    public MedicoDTO getMedicoById(Long id) {
         MedicoEntity medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medico non trovato"));
         return convertToDTO(medico);
    }

    /**
     * Metodo che modifica il medico.
     *
     * @param medicoEdit il DTO medico edit
     * @param id         l'id
     */
    public MedicoDTO updateMedicoById(@NotNull MedicoDTO medicoEdit, Long id) {
        MedicoEntity medico = medicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medico non trovato"));

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

        return convertToDTO(medicoRepository.saveAndFlush(medico));
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

    public MedicoEntity convertToEntity(@NotNull MedicoCreateDTO medicoCreateDTO) {
        return modelMapper.map(medicoCreateDTO, MedicoEntity.class);
    }

    public MedicoEntity convertToEntity(@NotNull MedicoDTO medicoDTO) {
        return modelMapper.map(medicoDTO, MedicoEntity.class);
    }

    public MedicoDTO convertToDTO(@NotNull MedicoEntity medico) {
        return modelMapper.map(medico, MedicoDTO.class);
    }

}
