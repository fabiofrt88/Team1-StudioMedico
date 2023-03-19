package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.SegretarioCreateDTO;
import co.develhope.team1studiomedico.dto.SegretarioDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.SegretarioEntity;
import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe SegretarioService realizza la logica di business relativamente le operazioni di CRUD dei dati di SegretarioEntity.
 * Utilizza SegretarioRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller SegretarioController
 */
@Service
public class SegretarioService {

    @Autowired
    private SegretarioRepository segretarioRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(SegretarioService.class);

    /**
     * Metodo che crea il segretario.
     *
     * @param segretarioCreateDTO il DTO di creazione del segretario
     */
    @Transactional
    public SegretarioDTO createSegretario(@NotNull SegretarioCreateDTO segretarioCreateDTO) {
        try {
            logger.info("Inizio processo createSegretario in SegretarioService");
            SegretarioEntity segretario = convertToEntity(segretarioCreateDTO);
            segretario.setId(null);
            segretario.setRecordStatus(EntityStatusEnum.ACTIVE);
            segretario = segretarioRepository.saveAndFlush(segretario);
            entityManager.refresh(segretario);
            return convertToDTO(segretario);
        } finally {
            logger.info("Fine processo createSegretario in SegretarioService");
        }
    }

    /**
     * Metodo che restituisce i segretari con record status ACTIVE.
     *
     * @return i segretari con record status ACTIVE
     */
    public List<SegretarioDTO> getAllSegretari() {
        return segretarioRepository.findByRecordStatus(EntityStatusEnum.ACTIVE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce i segretari cancellati logicamente con record status DELETED.
     *
     * @return i segretari cancellati logicamente con record status DELETED.
     */
    public List<SegretarioDTO> getAllDeletedSegretari() {
        return segretarioRepository.findByRecordStatus(EntityStatusEnum.DELETED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce il segretario tramite id.
     *
     * @param id l' id
     * @return il segretario tramite id
     */
    public SegretarioDTO getSegretarioById(Long id) {
        SegretarioEntity segretario = segretarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Segretario non trovato"));
        return convertToDTO(segretario);
    }

    /**
     * Metodo che modifica il segretario tramite id.
     *
     * @param segretarioEdit il segretario edit
     * @param id             l'id
     */
    public SegretarioDTO updateSegretarioById(@NotNull SegretarioDTO segretarioEdit, Long id) {
        SegretarioEntity segretario = segretarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Segretario non trovato"));

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

        return convertToDTO(segretarioRepository.saveAndFlush(segretario));
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

    public SegretarioEntity convertToEntity(@NotNull SegretarioCreateDTO segretarioCreateDTO) {
        return modelMapper.map(segretarioCreateDTO, SegretarioEntity.class);
    }

    public SegretarioEntity convertToEntity(@NotNull SegretarioDTO segretarioDTO) {
        return modelMapper.map(segretarioDTO, SegretarioEntity.class);
    }

    public SegretarioDTO convertToDTO(@NotNull SegretarioEntity segretario) {
        return modelMapper.map(segretario, SegretarioDTO.class);
    }

}