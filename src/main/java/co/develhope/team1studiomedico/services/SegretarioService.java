package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.SegretarioCreateDTO;
import co.develhope.team1studiomedico.dto.SegretarioDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.SegretarioEntity;
import co.develhope.team1studiomedico.exceptions.EntityStatusException;
import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(SegretarioService.class);

    /**
     * Metodo che crea il segretario.
     *
     * @param segretarioCreateDTO il DTO di creazione del segretario
     * @return il DTO del segretario
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
     * @return il DTO del segretario tramite id
     */
    public SegretarioDTO getSegretarioById(Long id) {
        SegretarioEntity segretario = segretarioRepository.findById(id)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(segretario);
    }

    /**
     * Metodo che modifica il segretario tramite id.
     *
     * @param segretarioEdit il segretario edit
     * @param id             l'id
     * @return il DTO del segretario
     */
    public SegretarioDTO updateSegretarioById(@NotNull SegretarioDTO segretarioEdit, Long id) {
        SegretarioEntity segretario = segretarioRepository.findById(id)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));

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
            SegretarioEntity segretario = segretarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(segretario.getRecordStatus().equals(EntityStatusEnum.DELETED)) {
                throw new EntityStatusException(messageSource.getMessage("error.segretario.status.deleted.exception",
                        null, LocaleContextHolder.getLocale()));
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
        try {
            logger.info("Inizio processo restoreSegretarioById in SegretarioService");
            SegretarioEntity segretario = segretarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(segretario.getRecordStatus().equals(EntityStatusEnum.ACTIVE)) {
                throw new EntityStatusException(messageSource.getMessage("error.segretario.status.active.exception",
                        null, LocaleContextHolder.getLocale()));
            }
            segretarioRepository.restoreById(id);
        } finally {
            logger.info("Fine processo restoreSegretarioById in SegretarioService");
        }
    }

    /**
     * Metodo che ripristina tutti i segretari.
     */
    public void restoreAllSegretari() {
        try {
            logger.info("Inizio processo restoreAllSegretari in SegretarioService");
            segretarioRepository.restore();
        } finally {
            logger.info("Fine processo restoreAllSegretari in SegretarioService");
        }
    }

    /**
     * Metodo che converte un oggetto SegretarioCreateDTO in un oggetto SegretarioEntity
     * @param segretarioCreateDTO il DTO di creazione del segretario
     * @return il segretario
     */
    public SegretarioEntity convertToEntity(@NotNull SegretarioCreateDTO segretarioCreateDTO) {
        return modelMapper.map(segretarioCreateDTO, SegretarioEntity.class);
    }

    /**
     * Metodo che converte un oggetto SegretarioDTO in un oggetto SegretarioEntity
     * @param segretarioDTO il DTO del segretario
     * @return il segretario
     */
    public SegretarioEntity convertToEntity(@NotNull SegretarioDTO segretarioDTO) {
        return modelMapper.map(segretarioDTO, SegretarioEntity.class);
    }

    /**
     * Metodo che converte un oggetto SegretarioEntity in un oggetto SegretarioDTO
     * @param segretario il segretario
     * @return il DTO del segretario
     */
    public SegretarioDTO convertToDTO(@NotNull SegretarioEntity segretario) {
        return modelMapper.map(segretario, SegretarioDTO.class);
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id del medico (foreign key in segretario)
     * @param medicoId id del medico
     * @return il DTO del segretario
     */
    public SegretarioDTO getSegretarioByMedicoId(Long medicoId) {
        SegretarioEntity segretario = segretarioRepository.findSegretarioByMedicoId(medicoId)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(segretario);
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id del paziente,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param pazienteId id del paziente
     * @return il DTO del segretario
     */
    public SegretarioDTO getSegretarioByPazienteId(Long pazienteId) {
        SegretarioEntity segretario = segretarioRepository.findSegretarioByPazienteId(pazienteId)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(segretario);
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id della prenotazione,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param prenotazioneId id della prenotazione
     * @return il DTO del segretario
     */
    public SegretarioDTO getSegretarioByPrenotazioneId(Long prenotazioneId) {
        SegretarioEntity segretario = segretarioRepository.findSegretarioByPrenotazioneId(prenotazioneId)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(segretario);
    }

    /**
     * Ricerca e restituisce il segretario per email
     * @param email email di ricerca
     * @return il DTO del segretario
     */
    public SegretarioDTO getSegretarioByEmail(String email) {
        SegretarioEntity segretario = segretarioRepository.findByEmail(email)
                .filter(segretarioEntity -> segretarioEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.segretario.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(segretario);
    }

    /**
     * Ricerca e restituisce i segretari per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista dei segretari filtrati per nome e cognome
     */
    public List<SegretarioDTO> getSegretariByNomeAndCognome(String nome, String cognome) {
        return segretarioRepository.searchByNomeAndCognome(nome, cognome)
                .stream()
                .filter(segretario -> segretario.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}