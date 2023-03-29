package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.PrenotazioneCreateDTO;
import co.develhope.team1studiomedico.dto.PrenotazioneDTO;
import co.develhope.team1studiomedico.dto.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;
import co.develhope.team1studiomedico.services.PrenotazioneService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * PrenotazioneController rappresenta la web API controller delle Prenotazioni,
 * espone degli endpoint circa le operazioni CRUD di PrenotazioneEntity,
 * elabora le response sulla base delle relative request del client
 */
@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneController.class);

    /**
     * Crea un prenotazione, restituisce una response entity di status 201.
     *
     * @param prenotazioneCreateDTO il DTO di creazione della prenotazione
     * @return il DTO della prenotazione
     */
    @PostMapping("/create")
    public ResponseEntity createPrenotazione(@Valid @RequestBody PrenotazioneCreateDTO prenotazioneCreateDTO) {
        PrenotazioneDTO prenotazioneDTO = prenotazioneService.createPrenotazione(prenotazioneCreateDTO);
        logger.info("Una nuova prenotazione con id {} è stata registrata", prenotazioneDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("prenotazione.controller.create",
                null, LocaleContextHolder.getLocale()), prenotazioneDTO));
    }

    /**
     * Restituisce la lista delle prenotazioni con record status ACTIVE.
     *
     * @return la lista delle prenotazioni con record status ACTIVE.
     */
    @GetMapping({"", "/"})
    public List<PrenotazioneDTO> getAllPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    /**
     * Restituisce la lista delle prenotazioni cancellate logicamente con record status DELETED.
     *
     * @return la lista delle prenotazioni cancellate logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<PrenotazioneDTO> getAllDeletedPrenotazioni() {
        return prenotazioneService.getAllDeletedPrenotazioni();
    }

    /**
     * Restituisce la prenotazione tramite id
     *
     * @param id  id
     * @return il DTO della prenotazione tramite id
     */
    @GetMapping("/{id}")
    public PrenotazioneDTO getPrenotazioneById(@PathVariable Long id) {
        return prenotazioneService.getPrenotazioneById(id);
    }

    /**
     * Update della prenotazione tramite id, restituisce una response entity di status 200.
     *
     * @param prenotazioneEdit la prenotazione edit
     * @param id id
     * @return la response entity di status 200.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity updatePrenotazioneById(@Valid @RequestBody PrenotazioneDTO prenotazioneEdit, @PathVariable Long id) {
        PrenotazioneDTO prenotazioneDTO = prenotazioneService.updatePrenotazioneById(prenotazioneEdit, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("prenotazione.controller.update",
                new Object[]{id}, LocaleContextHolder.getLocale()), prenotazioneDTO));
    }

    /**
     * Cancella le prenotazioni, restituisce una response entity di status 200 (soft delete).
     *
     * @return la response entity di status 200.
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllPrenotazioni() {
        prenotazioneService.deleteAllPrenotazioni();
        logger.warn("Tutte le prenotazioni sono state cancellate");
        return ResponseEntity.status(200).body(messageSource.getMessage("prenotazione.controller.deleteAllPrenotazioni",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Cancella la prenotazione tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id  id
     * @return la response entity di status 200
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePrenotazioneById(@PathVariable Long id) {
        prenotazioneService.deletePrenotazioneById(id);
        logger.info("Prenotazione con id {} è stata cancellata", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("prenotazione.controller.delete",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina la prenotazione tramite id e restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restorePrenotazioneById(@PathVariable Long id){
        prenotazioneService.restorePrenotazioneById(id);
        logger.info("Prenotazione con id {} è stata ripristinata", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("prenotazione.controller.restore",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina tutte le prenotazioni e restituisce una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllPrenotazioni() {
        prenotazioneService.restoreAllPrenotazioni();
        logger.warn("Tutte le prenotazioni sono state ripristinate");
        return ResponseEntity.status(200).body(messageSource.getMessage("prenotazione.controller.restoreAllPrenotazioni",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata
     * @param dataPrenotazione data di prenotazione
     * @return il numero delle prenotazioni della data considerata
     */
    @GetMapping("/count/data/{dataPrenotazione}")
    public Map<String, Integer> countPrenotazioniByDataPrenotazione(@PathVariable LocalDate dataPrenotazione) {
        return Map.ofEntries(entry("count", prenotazioneService.countPrenotazioniByDataPrenotazione(dataPrenotazione)));
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return il numero delle prenotazioni della data considerata collegate all'id del medico
     */
    @GetMapping("/count/data/{dataPrenotazione}/medico/{medicoId}")
    public Map<String, Integer> countPrenotazioniByDataPrenotazioneAndMedicoId(@PathVariable LocalDate dataPrenotazione, @PathVariable Long medicoId) {
        return Map.ofEntries(entry("count", prenotazioneService.countPrenotazioniByDataPrenotazioneAndMedicoId(dataPrenotazione, medicoId)));
    }

    /**
     * Restituisce il numero delle prenotazioni della data considerata collegate all'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return il numero delle prenotazioni della data considerata collegate all'id del segretario
     */
    @GetMapping("/count/data/{dataPrenotazione}/segretario/{segretarioId}")
    public Map<String, Integer> countPrenotazioniByDataPrenotazioneAndSegretarioId(@PathVariable LocalDate dataPrenotazione, @PathVariable Long segretarioId) {
        return Map.ofEntries(entry("count", prenotazioneService.countPrenotazioniByDataPrenotazioneAndSegretarioId(dataPrenotazione, segretarioId)));
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del medico (foreign key medicoId in prenotazione)
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per id medico
     */
    @GetMapping("/medico/{medicoId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByMedicoId(@PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByMedicoId(medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del paziente (foreign key pazienteId in prenotazione)
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per id paziente
     */
    @GetMapping("/paziente/{pazienteId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByPazienteId(@PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniByPazienteId(pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per id segretario
     */
    @GetMapping("/segretario/{segretarioId}")
    public List<PrenotazioneDTO> getAllPrenotazioniBySegretarioId(@PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniBySegretarioId(segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione
     * @param dataPrenotazione data di prenotazione
     * @return lista delle prenotazioni filtrate per data di prenotazione
     */
    @GetMapping("/data/{dataPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazione(@PathVariable LocalDate dataPrenotazione) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazione(dataPrenotazione);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data e dall'ora della prenotazione
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @return lista delle prenotazioni filtrate per data e ora della prenotazione
     */
    @GetMapping("/data/{dataPrenotazione}/ora/{oraPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazione(@PathVariable LocalDate dataPrenotazione, @PathVariable LocalTime oraPrenotazione) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndOraPrenotazione(dataPrenotazione, oraPrenotazione);
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate
     * @param startDate data inizio
     * @param endDate data fine
     * @return lista delle prenotazioni nell'intervallo di due date considerate
     */
    @GetMapping("/data/{startDate}/{endDate}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazione(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return prenotazioneService.getAllPrenotazioniBetweenDatePrenotazione(startDate, endDate);
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione
     * @param statoPrenotazione stato della prenotazione
     * @return lista delle prenotazioni filtrate per stato prenotazione
     */
    @GetMapping("/stato/{statoPrenotazione}")
    public List<PrenotazioneDTO> findPrenotazioniByStatoPrenotazione(@PathVariable PrenotazioneStatusEnum statoPrenotazione) {
        return prenotazioneService.getAllPrenotazioniByStatoPrenotazione(statoPrenotazione);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del medico
     */
    @GetMapping("/medico/{medicoId}/data/{dataPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndMedicoId(@PathVariable LocalDate dataPrenotazione, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndMedicoId(dataPrenotazione, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del segretario
     */
    @GetMapping("/segretario/{segretarioId}/data/{dataPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndSegretarioId(@PathVariable LocalDate dataPrenotazione, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndSegretarioId(dataPrenotazione, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data di prenotazione e dall'id del paziente
     * @param dataPrenotazione data di prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per data di prenotazione e id del paziente
     */
    @GetMapping("/paziente/{pazienteId}/data/{dataPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndPazienteId(@PathVariable LocalDate dataPrenotazione, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndPazienteId(dataPrenotazione, pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del medico
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del medico
     */
    @GetMapping("/medico/{medicoId}/data/{dataPrenotazione}/ora/{oraPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(@PathVariable LocalDate dataPrenotazione, @PathVariable LocalTime oraPrenotazione, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndMedicoId(dataPrenotazione, oraPrenotazione, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni a partire dalla data, dall'ora della prenotazione e dall'id del segretario
     * @param dataPrenotazione data di prenotazione
     * @param oraPrenotazione ora della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per data, ora della prenotazione e id del segretario
     */
    @GetMapping("/segretario/{segretarioId}/data/{dataPrenotazione}/ora/{oraPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndSegretarioId(@PathVariable LocalDate dataPrenotazione, @PathVariable LocalTime oraPrenotazione, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniByDataPrenotazioneAndOraPrenotazioneAndSegretarioId(dataPrenotazione, oraPrenotazione, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del medico
     * @param startDate data inizio
     * @param endDate data fine
     * @param medicoId id del medico
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del medico
     */
    @GetMapping("/medico/{medicoId}/data/{startDate}/{endDate}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndMedicoId(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniBetweenDatePrenotazioneAndMedicoId(startDate, endDate, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del segretario
     * @param startDate data inizio
     * @param endDate data fine
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del segretario
     */
    @GetMapping("/segretario/{segretarioId}/data/{startDate}/{endDate}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndSegretarioId(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniBetweenDatePrenotazioneAndSegretarioId(startDate, endDate, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni nell'intervallo di due date considerate e id del paziente
     * @param startDate data inizio
     * @param endDate data fine
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni nell'intervallo di due date considerate e id del paziente
     */
    @GetMapping("/paziente/{pazienteId}/data/{startDate}/{endDate}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenDatePrenotazioneAndPazienteId(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniBetweenDatePrenotazioneAndPazienteId(startDate, endDate, pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del medico
     * @param statoPrenotazione stato della prenotazione
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del medico
     */
    @GetMapping("/medico/{medicoId}/stato/{statoPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndMedicoId(@PathVariable PrenotazioneStatusEnum statoPrenotazione, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByStatoPrenotazioneAndMedicoId(statoPrenotazione, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del segretario
     * @param statoPrenotazione stato della prenotazione
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del segretario
     */
    @GetMapping("/segretario/{segretarioId}/stato/{statoPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndSegretarioId(@PathVariable PrenotazioneStatusEnum statoPrenotazione, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniByStatoPrenotazioneAndSegretarioId(statoPrenotazione, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per stato prenotazione e id del paziente
     * @param statoPrenotazione stato della prenotazione
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per stato prenotazione e id del paziente
     */
    @GetMapping("/paziente/{pazienteId}/stato/{statoPrenotazione}")
    public List<PrenotazioneDTO> getAllPrenotazioniByStatoPrenotazioneAndPazienteId(@PathVariable PrenotazioneStatusEnum statoPrenotazione, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniByStatoPrenotazioneAndPazienteId(statoPrenotazione, pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year)
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per anno (year)
     */
    @GetMapping("/year/{year}")
    public List<PrenotazioneDTO> getAllPrenotazioniByYear(@PathVariable Integer year) {
        return prenotazioneService.getAllPrenotazioniByYear(year);
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del medico
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per anno (year) e id del medico
     */
    @GetMapping("/year/{year}/medico/{medicoId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndMedicoId(@PathVariable Integer year, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByYearAndMedicoId(year, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del segretario
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per anno (year) e id del segretario
     */
    @GetMapping("/year/{year}/segretario/{segretarioId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndSegretarioId(@PathVariable Integer year, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniByYearAndSegretarioId(year, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per anno (year) e id del paziente
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per anno (year) e id del paziente
     */
    @GetMapping("/year/{year}/paziente/{pazienteId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByYearAndPazienteId(@PathVariable Integer year, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniByYearAndPazienteId(year, pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year)
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @return lista delle prenotazioni filtrate per mese (month) e anno (year)
     */
    @GetMapping("/month/{month}/year/{year}")
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYear(@PathVariable Integer month, @PathVariable Integer year) {
        return prenotazioneService.getAllPrenotazioniByMonthAndYear(month, year);
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del medico
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param medicoId id del medico
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del medico
     */
    @GetMapping("/month/{month}/year/{year}/medico/{medicoId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndMedicoId(@PathVariable Integer month, @PathVariable Integer year, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniByMonthAndYearAndMedicoId(month, year, medicoId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del segretario
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param segretarioId id del segretario
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del segretario
     */
    @GetMapping("/month/{month}/year/{year}/segretario/{segretarioId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndSegretarioId(@PathVariable Integer month, @PathVariable Integer year, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniByMonthAndYearAndSegretarioId(month, year, segretarioId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month), anno (year) e id del paziente
     * @param month mese (month) di ricerca
     * @param year anno (year) di ricerca
     * @param pazienteId id del paziente
     * @return lista delle prenotazioni filtrate per mese (month), anno (year) e id del paziente
     */
    @GetMapping("/month/{month}/year/{year}/paziente/{pazienteId}")
    public List<PrenotazioneDTO> getAllPrenotazioniByMonthAndYearAndPazienteId(@PathVariable Integer month, @PathVariable Integer year, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniByMonthAndYearAndPazienteId(month, year, pazienteId);
    }

    /**
     * Ricerca e restituisce le prenotazioni per mese (month) e anno (year) nell'intervallo di due mesi e due anni considerati
     * @param fromMonth mese (month) inizio
     * @param toMonth mese (month) fine
     * @param fromYear anno (year) inizio
     * @param toYear anno (year) fine
     * @return lista delle prenotazioni filtrate nell'intervallo di due mesi e due anni considerati
     */
    @GetMapping("/month/{fromMonth}/{toMonth}/year/{fromYear}/{toYear}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYears(@PathVariable Integer fromMonth, @PathVariable Integer toMonth, @PathVariable Integer fromYear, @PathVariable Integer toYear) {
        return prenotazioneService.getAllPrenotazioniBetweenMonthsAndYears(fromMonth, toMonth, fromYear, toYear);
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
    @GetMapping("/month/{fromMonth}/{toMonth}/year/{fromYear}/{toYear}/medico/{medicoId}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndMedicoId(@PathVariable Integer fromMonth, @PathVariable Integer toMonth, @PathVariable Integer fromYear, @PathVariable Integer toYear, @PathVariable Long medicoId) {
        return prenotazioneService.getAllPrenotazioniBetweenMonthsAndYearsAndMedicoId(fromMonth, toMonth, fromYear, toYear, medicoId);
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
    @GetMapping("/month/{fromMonth}/{toMonth}/year/{fromYear}/{toYear}/segretario/{segretarioId}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndSegretarioId(@PathVariable Integer fromMonth, @PathVariable Integer toMonth, @PathVariable Integer fromYear, @PathVariable Integer toYear, @PathVariable Long segretarioId) {
        return prenotazioneService.getAllPrenotazioniBetweenMonthsAndYearsAndSegretarioId(fromMonth, toMonth, fromYear, toYear, segretarioId);
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
    @GetMapping("/month/{fromMonth}/{toMonth}/year/{fromYear}/{toYear}/paziente/{pazienteId}")
    public List<PrenotazioneDTO> getAllPrenotazioniBetweenMonthsAndYearsAndPazienteId(@PathVariable Integer fromMonth, @PathVariable Integer toMonth, @PathVariable Integer fromYear, @PathVariable Integer toYear, @PathVariable Long pazienteId) {
        return prenotazioneService.getAllPrenotazioniBetweenMonthsAndYearsAndPazienteId(fromMonth, toMonth, fromYear, toYear, pazienteId);
    }

}
