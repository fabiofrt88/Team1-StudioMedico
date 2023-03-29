package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.paziente.PazienteCreateDTO;
import co.develhope.team1studiomedico.dto.paziente.PazienteDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.exceptions.EntityStatusException;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
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
 * La classe PazienteService realizza la logica di business relativamente le operazioni di CRUD dei dati di PazienteEntity.
 * Utilizza PazienteRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PazienteController
 */
@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(PazienteService.class);

    /**
     * Metodo che crea il paziente.
     *
     * @param pazienteCreateDTO il DTO di creazione del paziente
     * @return il DTO del paziente
     */
    @Transactional
    public PazienteDTO createPaziente(@NotNull PazienteCreateDTO pazienteCreateDTO) {
        try {
            logger.info("Inizio processo createPaziente in PazienteService");
            PazienteEntity paziente = convertToEntity(pazienteCreateDTO);
            paziente.setId(null);
            paziente.setRecordStatus(EntityStatusEnum.ACTIVE);
            paziente = pazienteRepository.saveAndFlush(paziente);
            entityManager.refresh(paziente);
            return convertToDTO(paziente);
        } finally {
            logger.info("Fine processo createPaziente in PazienteService");
        }
    }

    /**
     * Metodo che restituisce i pazienti con record status ACTIVE.
     *
     * @return i pazienti con record status ACTIVE
     */
    public List<PazienteDTO> getAllPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.ACTIVE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce i pazienti cancellati logicamente con record status DELETED.
     *
     * @return i pazienti cancellati logicamente con record status DELETED.
     */
    public List<PazienteDTO> getAllDeletedPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.DELETED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce il paziente tramite id.
     *
     * @param id l' id
     * @return il DTO del paziente tramite id
     */
    public PazienteDTO getPazienteById(Long id) {
        PazienteEntity paziente = pazienteRepository.findById(id)
                .filter(pazienteEntity -> pazienteEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(paziente);
    }

    /**
     * Metodo che modifica il paziente tramite id.
     *
     * @param pazienteEdit the paziente edit
     * @param id           the id
     * @return il DTO del paziente
     */
    public PazienteDTO updatePazienteById(@NotNull PazienteDTO pazienteEdit, Long id) {
        PazienteEntity paziente = pazienteRepository.findById(id)
                .filter(pazienteEntity -> pazienteEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                        null, LocaleContextHolder.getLocale())));

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

        return convertToDTO(pazienteRepository.saveAndFlush(paziente));
    }

    /**
     * Metodo che cancella il paziente tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deletePazienteById(Long id) {
        try {
            logger.info("Inizio processo deletePazienteById in PazienteService");
            PazienteEntity paziente = pazienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(paziente.getRecordStatus().equals(EntityStatusEnum.DELETED)) {
                throw new EntityStatusException(messageSource.getMessage("error.paziente.status.deleted.exception",
                        null, LocaleContextHolder.getLocale()));
            }
            pazienteRepository.softDeleteById(id);
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
        try {
            logger.info("Inizio processo restorePazienteById in PazienteService");
            PazienteEntity paziente = pazienteRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE)) {
                throw new EntityStatusException(messageSource.getMessage("error.paziente.status.active.exception",
                        null, LocaleContextHolder.getLocale()));
            }
            pazienteRepository.restoreById(id);
        } finally {
            logger.info("Fine processo restorePazienteById in PazienteService");
        }
    }

    /**
     * Metodo che ripristina i pazienti.
     */
    public void restoreAllPazienti() {
        try {
            logger.info("Inizio processo restoreAllPazienti in PazienteService");
            pazienteRepository.restore();
        } finally {
            logger.info("Fine processo restoreAllPazienti in PazienteService");
        }
    }

    /**
     * Metodo che converte un oggetto PazienteCreateDTO in un oggetto PazienteEntity
     * @param pazienteCreateDTO il DTO di creazione del paziente
     * @return il paziente
     */
    public PazienteEntity convertToEntity(@NotNull PazienteCreateDTO pazienteCreateDTO) {
        return modelMapper.map(pazienteCreateDTO, PazienteEntity.class);
    }

    /**
     * Metodo che converte un oggetto PazienteDTO in un oggetto PazienteEntity
     * @param pazienteDTO il DTO del paziente
     * @return il paziente
     */
    public PazienteEntity convertToEntity(@NotNull PazienteDTO pazienteDTO) {
        return modelMapper.map(pazienteDTO, PazienteEntity.class);
    }

    /**
     * Metodo che converte un oggetto PazienteEntity in un oggetto PazienteDTO
     * @param paziente il paziente
     * @return il DTO del paziente
     */
    public PazienteDTO convertToDTO(@NotNull PazienteEntity paziente) {
        return modelMapper.map(paziente, PazienteDTO.class);
    }

    /**
     * Ricerca e restituisce i pazienti a partire dall'id del medico (foreign key medicoId in paziente)
     * @param medicoId id del medico
     * @return lista di pazienti filtrati per id medico
     */
    public List<PazienteDTO> getAllPazientiByMedicoId(Long medicoId) {
        return pazienteRepository.findPazientiByMedicoId(medicoId)
                .stream()
                .filter(paziente -> paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce i pazienti a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista di pazienti filtrati per id segretario
     */
    public List<PazienteDTO> getAllPazientiBySegretarioId(Long segretarioId) {
        return pazienteRepository.findPazientiBySegretarioId(segretarioId)
                .stream()
                .filter(paziente -> paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce il paziente a partire dall'id della prenotazione
     * (foreign key pazienteId in prenotazione)
     * @param prenotazioneId id della prenotazione
     * @return il DTO del paziente
     */
    public PazienteDTO getPazienteByPrenotazioneId(Long prenotazioneId) {
        PazienteEntity paziente = pazienteRepository.findPazienteByPrenotazioneId(prenotazioneId)
                .filter(pazienteEntity -> pazienteEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(paziente);
    }

    /**
     * Ricerca e restituisce il paziente per email
     * @param email email di ricerca
     * @return il DTO del paziente
     */
    public PazienteDTO getPazienteByEmail(String email) {
        PazienteEntity paziente = pazienteRepository.findByEmail(email)
                .filter(pazienteEntity -> pazienteEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(paziente);
    }

    /**
     * Ricerca e restituisce il paziente a partire dal codice fiscale
     * @param codiceFiscale codice fiscale di ricerca
     * @return il DTO del paziente
     */
    public PazienteDTO getPazienteByCodiceFiscale(String codiceFiscale) {
        PazienteEntity paziente = pazienteRepository.findPazienteByCodiceFiscale(codiceFiscale)
                .filter(pazienteEntity -> pazienteEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.paziente.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(paziente);
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista dei pazienti filtrati per nome e cognome
     */
    public List<PazienteDTO> getPazientiByNomeAndCognome(String nome, String cognome) {
        return pazienteRepository.searchByNomeAndCognome(nome, cognome)
                .stream()
                .filter(paziente -> paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome e id del medico (foreign key medicoId in paziente)
     * @param nome nome utente
     * @param cognome cognome utente
     * @param medicoId id del medico
     * @return lista di pazienti filtrati per nome, cognome, id del medico
     */
    public List<PazienteDTO> getPazientiByNomeAndCognomeAndMedicoId(String nome, String cognome, Long medicoId) {
        return pazienteRepository.searchPazientiByNomeAndCognomeAndMedicoId(nome, cognome, medicoId)
                .stream()
                .filter(paziente -> paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome e id del segretario
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param nome nome utente
     * @param cognome cognome utente
     * @param segretarioId id del segretario
     * @return lista di pazienti filtrati per nome, cognome, id del segretario
     */
    public List<PazienteDTO> getPazientiByNomeAndCognomeAndSegretarioId(String nome, String cognome, Long segretarioId) {
        return pazienteRepository.searchPazientiByNomeAndCognomeAndSegretarioId(nome, cognome, segretarioId)
                .stream()
                .filter(paziente -> paziente.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
