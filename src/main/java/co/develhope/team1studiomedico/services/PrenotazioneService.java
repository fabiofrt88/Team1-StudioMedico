package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.PrenotazioneCreateDTO;
import co.develhope.team1studiomedico.dto.PrenotazioneDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;
import co.develhope.team1studiomedico.exceptions.EntityStatusException;
import co.develhope.team1studiomedico.repositories.PrenotazioneRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe PrenotazioneService realizza la logica di business relativamente le operazioni di CRUD dei dati di PrenotazioneEntity.
 * Utilizza PrenotazioneRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PrenotazioneController
 */
@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneService.class);

    /**
     * Metodo che crea la prenotazione.
     *
     * @param prenotazioneCreateDTO il DTO di creazione della prenotazione
     * @return il DTO della prenotazione
     */
    @Transactional
    public PrenotazioneDTO createPrenotazione(@NotNull PrenotazioneCreateDTO prenotazioneCreateDTO) {
        try {
            logger.info("Inizio processo createPrenotazione in PrenotazioneService");
            PrenotazioneEntity prenotazione = convertToEntity(prenotazioneCreateDTO);
            prenotazione.setId(null);
            prenotazione.setRecordStatus(EntityStatusEnum.ACTIVE);
            prenotazione.setStatoPrenotazione(PrenotazioneStatusEnum.PENDING);
            prenotazione = prenotazioneRepository.saveAndFlush(prenotazione);
            entityManager.refresh(prenotazione);
            return convertToDTO(prenotazione);
        } finally {
            logger.info("Fine processo createPrenotazione in PrenotazioneService");
        }
    }

    /**
     * Metodo che restituisce le prenotazioni con record status ACTIVE.
     *
     * @return le prenotazioni con record status ACTIVE
     */
    public List<PrenotazioneDTO> getAllPrenotazioni() {
        return prenotazioneRepository.findByRecordStatus(EntityStatusEnum.ACTIVE, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce le prenotazioni cancellate logicamente con record status DELETED.
     *
     * @return le prenotazioni cancellate logicamente con record status DELETED.
     */
    public List<PrenotazioneDTO> getAllDeletedPrenotazioni() {
        return prenotazioneRepository.findByRecordStatus(EntityStatusEnum.DELETED, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce la prenotazione tramite id.
     *
     * @param id l' id
     * @return il DTO della prenotazione tramite id
     */
    public PrenotazioneDTO getPrenotazioneById(Long id) {
        PrenotazioneEntity prenotazione = prenotazioneRepository.findById(id)
                .filter(prenotazioneEntity -> prenotazioneEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.prenotazione.notFound.exception",
                        null, LocaleContextHolder.getLocale())));
        return convertToDTO(prenotazione);

    }

    /**
     * Metodo che modifica la prenotazione.
     *
     * @param prenotazioneEdit il DTO prenotazione edit
     * @param id         l'id
     * @return il DTO della prenotazione
     */
    public PrenotazioneDTO updatePrenotazioneById(@NotNull PrenotazioneDTO prenotazioneEdit, Long id) {
        PrenotazioneEntity prenotazione = prenotazioneRepository.findById(id)
                .filter(prenotazioneEntity -> prenotazioneEntity.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.prenotazione.notFound.exception",
                        null, LocaleContextHolder.getLocale())));

        if(prenotazioneEdit.getDataPrenotazione() != null) {
            prenotazione.setDataPrenotazione(prenotazioneEdit.getDataPrenotazione());
        }
        if(prenotazioneEdit.getOraPrenotazione() != null) {
            prenotazione.setOraPrenotazione(prenotazioneEdit.getOraPrenotazione());
        }
        if(prenotazioneEdit.getStatoPrenotazione() != null) {
            prenotazione.setStatoPrenotazione(prenotazioneEdit.getStatoPrenotazione());
        }

        return convertToDTO(prenotazioneRepository.saveAndFlush(prenotazione));
    }

    /**
     * Metodo che cancella la prenotazione tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deletePrenotazioneById(Long id) {
        try {
            logger.info("Inizio processo deletePrenotazioneById in PrenotazioneService");
            PrenotazioneEntity prenotazione = prenotazioneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.prenotazione.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(prenotazione.getRecordStatus().equals(EntityStatusEnum.DELETED)) {
                throw new EntityStatusException(messageSource.getMessage("error.prenotazione.status.deleted.exception",
                        null, LocaleContextHolder.getLocale()));
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
        try {
            logger.info("Inizio processo restorePrenotazioneById in PrenotazioneService");
            PrenotazioneEntity prenotazione = prenotazioneRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.prenotazione.notFound.exception",
                            null, LocaleContextHolder.getLocale())));

            if(prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE)) {
                throw new EntityStatusException(messageSource.getMessage("error.prenotazione.status.active.exception",
                        null, LocaleContextHolder.getLocale()));
            }
            prenotazioneRepository.restoreById(id);
        } finally {
            logger.info("Fine processo restorePrenotazioneById in PrenotazioneService");
        }
    }

    /**
     * Metodo che ripristina tutti le prenotazioni.
     */
    public void restoreAllPrenotazioni() {
        try {
            logger.info("Inizio processo restoreAllPrenotazioni in PrenotazioneService");
            prenotazioneRepository.restore();
        } finally {
            logger.info("Fine processo restoreAllPrenotazioni in PrenotazioneService");
        }
    }

    /**
     * Metodo che converte un oggetto PrenotazioneCreateDTO in un oggetto PrenotazioneEntity
     * @param prenotazioneCreateDTO il DTO di creazione della prenotazione
     * @return la prenotazione
     */
    public PrenotazioneEntity convertToEntity(@NotNull PrenotazioneCreateDTO prenotazioneCreateDTO) {
        return modelMapper.map(prenotazioneCreateDTO, PrenotazioneEntity.class);
    }

    /**
     * Metodo che converte un oggetto PrenotazioneDTO in un oggetto PrenotazioneEntity
     * @param prenotazioneDTO il DTO della prenotazione
     * @return la prenotazione
     */
    public PrenotazioneEntity convertToEntity(@NotNull PrenotazioneDTO prenotazioneDTO) {
        return modelMapper.map(prenotazioneDTO, PrenotazioneEntity.class);
    }

    /**
     * Metodo che converte un oggetto PrenotazioneEntity in un oggetto PrenotazioneDTO
     * @param prenotazione la prenotazione
     * @return il DTO della prenotazione
     */
    public PrenotazioneDTO convertToDTO(@NotNull PrenotazioneEntity prenotazione) {
        return modelMapper.map(prenotazione, PrenotazioneDTO.class);
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata
     * @param dataPrenotazione data di prenotazione
     * @return il numero delle prenotazioni della data considerata
     */
    public Integer countPrenotazioniByDataPrenotazione(LocalDate dataPrenotazione) {
        return prenotazioneRepository.countPrenotazioniByDataPrenotazione(dataPrenotazione);
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return il numero delle prenotazioni della data considerata collegate all'id del medico
     */
    public Integer countPrenotazioniByDataPrenotazioneAndMedicoId(LocalDate dataPrenotazione, Long medicoId) {
        return prenotazioneRepository.countPrenotazioniByDataPrenotazioneAndMedicoId(dataPrenotazione, medicoId);
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return il numero delle prenotazioni della data considerata collegate all'id del segretario
     */
    public Integer countPrenotazioniByDataPrenotazioneAndSegretarioId(LocalDate dataPrenotazione, Long segretarioId) {
        return prenotazioneRepository.countPrenotazioniByDataPrenotazioneAndSegretarioId(dataPrenotazione, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del medico (foreign key medicoId in prenotazione)
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per id medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByMedicoId(Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByMedicoId(medicoId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del paziente (foreign key pazienteId in prenotazione)
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per id paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByPazienteId(Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniByPazienteId(pazienteId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per id segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBySegretarioId(Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniBySegretarioId(segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione
     * @param dataPrenotazione data di prenotazione
     * @return lista delle prenotazioni filtrate per data di prenotazione
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazione(LocalDate dataPrenotazione) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazione(dataPrenotazione, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data e dall'ora della prenotazione
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @return lista delle prenotazioni filtrate per data e ora della prenotazione
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazione(LocalDate dataPrenotazione, LocalTime oraPrenotazione) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndOraPrenotazione(dataPrenotazione, oraPrenotazione, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate
     * @param startDate data inizio
     * @param endDate data fine
     * @return lista delle prenotazioni nell'intervallo di due date considerate
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazione(LocalDate startDate, LocalDate endDate) {
        return prenotazioneRepository.findPrenotazioniBetweenDatePrenotazione(startDate, endDate)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione
     * @param statoPrenotazione stato della prenotazione
     * @return lista delle prenotazioni filtrate per stato prenotazione
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazione(PrenotazioneStatusEnum statoPrenotazione) {
        return prenotazioneRepository.findPrenotazioniByStatoPrenotazione(statoPrenotazione, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndMedicoId(LocalDate dataPrenotazione, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndMedicoId(dataPrenotazione, medicoId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del paziente
     * @param dataPrenotazione data di prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndPazienteId(LocalDate dataPrenotazione, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndPazienteId(dataPrenotazione, pazienteId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndSegretarioId(LocalDate dataPrenotazione, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndSegretarioId(dataPrenotazione, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(dataPrenotazione, oraPrenotazione, medicoId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndSegretarioId(LocalDate dataPrenotazione, LocalTime oraPrenotazione, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndSegretarioId(dataPrenotazione, oraPrenotazione, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del medico
     * @param startDate data inizio
     * @param endDate data fine
     * @param medicoId id del medico
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndMedicoId(LocalDate startDate, LocalDate endDate, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniBetweenDatePrenotazioneAndMedicoId(startDate, endDate, medicoId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del segretario
     * @param startDate data inizio
     * @param endDate data fine
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndSegretarioId(LocalDate startDate, LocalDate endDate, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniBetweenDatePrenotazioneAndSegretarioId(startDate, endDate, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del paziente
     * @param startDate data inizio
     * @param endDate data fine
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndPazienteId(LocalDate startDate, LocalDate endDate, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniBetweenDatePrenotazioneAndPazienteId(startDate, endDate, pazienteId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del medico
     * @param statoPrenotazione stato della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndMedicoId(PrenotazioneStatusEnum statoPrenotazione, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByStatoPrenotazioneAndMedicoId(statoPrenotazione, medicoId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del segretario
     * @param statoPrenotazione stato della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndSegretarioId(PrenotazioneStatusEnum statoPrenotazione, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniByStatoPrenotazioneAndSegretarioId(statoPrenotazione, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del paziente
     * @param statoPrenotazione stato della prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndPazienteId(PrenotazioneStatusEnum statoPrenotazione, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniByStatoPrenotazioneAndPazienteId(statoPrenotazione, pazienteId, Sort.by("dataPrenotazione", "oraPrenotazione").ascending())
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year)
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per anno (year)
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByYear(Integer year) {
        return prenotazioneRepository.findPrenotazioniByYear(year)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del medico
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per anno (year) e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndMedicoId(Integer year, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByYearAndMedicoId(year, medicoId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del segretario
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per anno (year) e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndSegretarioId(Integer year, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniByYearAndSegretarioId(year, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del paziente
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per anno (year) e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndPazienteId(Integer year, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniByYearAndPazienteId(year, pazienteId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year)
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per mese (month) e anno (year)
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYear(Integer month, Integer year) {
        return prenotazioneRepository.findPrenotazioniByMonthAndYear(month, year)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del medico
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndMedicoId(Integer month, Integer year, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniByMonthAndYearAndMedicoId(month, year, medicoId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del segretario
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndSegretarioId(Integer month, Integer year, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniByMonthAndYearAndSegretarioId(month, year, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del paziente
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndPazienteId(Integer month, Integer year, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniByMonthAndYearAndPazienteId(month, year, pazienteId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYears(Integer fromMonth, Integer toMonth, Integer fromYear, Integer toYear) {
        return prenotazioneRepository.findPrenotazioniBetweenMonthsAndYears(fromMonth, toMonth, fromYear, toYear)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del medico
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del medico
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndMedicoId(Integer fromMonth, Integer toMonth, Integer fromYear, Integer toYear, Long medicoId) {
        return prenotazioneRepository.findPrenotazioniBetweenMonthsAndYearsAndMedicoId(fromMonth, toMonth, fromYear, toYear, medicoId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del segretario
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del segretario
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndSegretarioId(Integer fromMonth, Integer toMonth, Integer fromYear, Integer toYear, Long segretarioId) {
        return prenotazioneRepository.findPrenotazioniBetweenMonthsAndYearsAndSegretarioId(fromMonth, toMonth, fromYear, toYear, segretarioId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati, e id del paziente
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati, e id del paziente
     */
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndPazienteId(Integer fromMonth, Integer toMonth, Integer fromYear, Integer toYear, Long pazienteId) {
        return prenotazioneRepository.findPrenotazioniBetweenMonthsAndYearsAndPazienteId(fromMonth, toMonth, fromYear, toYear, pazienteId)
                .stream()
                .filter(prenotazione -> prenotazione.getRecordStatus().equals(EntityStatusEnum.ACTIVE))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
