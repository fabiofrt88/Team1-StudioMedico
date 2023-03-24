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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Prenotazione creata correttamente", prenotazioneDTO));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Prenotazione con id + " + id + " modificata correttamente", prenotazioneDTO));
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
        return ResponseEntity.status(200).body("Prenotazioni cancellate correttamente");
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
        return ResponseEntity.status(200).body("Prenotazione con id + " + id + " cancellata correttamente");
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
        return ResponseEntity.status(200).body("Prenotazione con id + " + id + " ripristinata correttamente");
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
        return ResponseEntity.status(200).body("Prenotazioni ripristinate correttamente");
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

}
