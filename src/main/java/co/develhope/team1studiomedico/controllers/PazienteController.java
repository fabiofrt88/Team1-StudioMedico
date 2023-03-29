package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.paziente.PazienteCreateDTO;
import co.develhope.team1studiomedico.dto.paziente.PazienteDTO;
import co.develhope.team1studiomedico.dto.success.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.services.PazienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PazienteController rappresenta la web API controller del Paziente,
 * espone degli endpoint circa le operazioni CRUD di PazienteEntity,
 * elabora le response sulla base delle relative request del client
 */
@RestController
@RequestMapping("/paziente")
public class PazienteController {

    @Autowired
    private PazienteService pazienteService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(PazienteController.class);

    /**
     * Crea un paziente, restituisce una response entity di status 201.
     *
     * @param pazienteCreateDTO il DTO di creazione del paziente
     * @return il DTO del paziente
     */
    @PostMapping("/create")
    public ResponseEntity createPaziente(@Valid @RequestBody PazienteCreateDTO pazienteCreateDTO) {
        PazienteDTO pazienteDTO = pazienteService.createPaziente(pazienteCreateDTO);
        logger.info("Un nuovo paziente con id {} è stato registrato", pazienteDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("paziente.controller.create",
                null, LocaleContextHolder.getLocale()), pazienteDTO));
    }

    /**
     * Restituisce la lista dei pazienti con record status ACTIVE.
     *
     * @return la lista dei pazienti con record status ACTIVE
     */
    @GetMapping({"", "/"})
    public List<PazienteDTO> getAllPazienti() {
        return pazienteService.getAllPazienti();
    }

    /**
     * Restituisce la lista dei pazienti cancellati logicamente con record status DELETED.
     *
     * @return la lista dei pazienti cancellati logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<PazienteDTO> getAllDeletedPazienti() {
        return pazienteService.getAllDeletedPazienti();
    }

    /**
     * Restituisce il paziente tramite id.
     *
     * @param id  id
     * @return il DTO del paziente tramite id
     */
    @GetMapping("/{id}")
    public PazienteDTO getPazienteById(@PathVariable Long id) {
        return pazienteService.getPazienteById(id);
    }

    /**
     * Update del paziente tramite id, restituisce una response entity di status 200.
     *
     * @param pazienteEdit la modifica al paziente
     * @param id            id
     * @return la response entity di status 200
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity updatePazienteById(@Valid @RequestBody PazienteDTO pazienteEdit, @PathVariable Long id) {
        PazienteDTO pazienteDTO = pazienteService.updatePazienteById(pazienteEdit, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("paziente.controller.update",
                new Object[]{id}, LocaleContextHolder.getLocale()), pazienteDTO));
    }

    /**
     * Cancella i pazienti, restituisce una response entity di status 200 (soft delete).
     *
     * @return the response entity
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllPazienti() {
        pazienteService.deleteAllPazienti();
        logger.warn("Tutti i pazienti sono stati cancellati");
        return ResponseEntity.status(200).body(messageSource.getMessage("paziente.controller.deleteAllPazienti",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Cancella il paziente tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id  id
     * @return la response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePazienteById(@PathVariable Long id) {
        pazienteService.deletePazienteById(id);
        logger.info("Paziente con id {} è stato cancellato", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("paziente.controller.delete",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina il paziente tramite id, restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restorePazienteById(@PathVariable Long id) {
        pazienteService.restorePazienteById(id);
        logger.info("Paziente con id {} è stato ripristinato", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("paziente.controller.restore",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina i pazienti, restituisce una response entity di status 200.
     *
     * @return la response entity
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllPazienti() {
        pazienteService.restoreAllPazienti();
        logger.warn("Tutti i pazienti sono stati ripristinati");
        return ResponseEntity.status(200).body(messageSource.getMessage("paziente.controller.restoreAllPazienti",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Ricerca e restituisce i pazienti a partire dall'id del medico (foreign key medicoId in paziente)
     * @param medicoId id del medico
     * @return lista di pazienti filtrati per id medico
     */
    @GetMapping("/medico/{medicoId}")
    public List<PazienteDTO> getAllPazientiByMedicoId(@PathVariable Long medicoId) {
        return pazienteService.getAllPazientiByMedicoId(medicoId);
    }

    /**
     * Ricerca e restituisce i pazienti a partire dall'id del segretario,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param segretarioId id del segretario
     * @return lista di pazienti filtrati per id segretario
     */
    @GetMapping("/segretario/{segretarioId}")
    public List<PazienteDTO> getAllPazientiBySegretarioId(@PathVariable Long segretarioId) {
        return pazienteService.getAllPazientiBySegretarioId(segretarioId);
    }

    /**
     * Ricerca e restituisce il paziente a partire dall'id della prenotazione
     * (foreign key pazienteId in prenotazione)
     * @param prenotazioneId id della prenotazione
     * @return il DTO del paziente
     */
    @GetMapping("/prenotazione/{prenotazioneId}")
    public PazienteDTO getPazienteByPrenotazioneId(@PathVariable Long prenotazioneId) {
        return pazienteService.getPazienteByPrenotazioneId(prenotazioneId);
    }

    /**
     * Ricerca e restituisce il paziente per email
     * @param email email di ricerca
     * @return il DTO del paziente
     */
    @GetMapping("/email/{email}")
    public PazienteDTO getPazienteByEmail(@PathVariable String email) {
        return pazienteService.getPazienteByEmail(email);
    }

    /**
     * Ricerca e restituisce il paziente a partire dal codice fiscale
     * @param codiceFiscale codice fiscale di ricerca
     * @return il DTO del paziente
     */
    @GetMapping("/codice-fiscale/{codiceFiscale}")
    public PazienteDTO getPazienteByCodiceFiscale(@PathVariable String codiceFiscale) {
        return pazienteService.getPazienteByCodiceFiscale(codiceFiscale);
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista dei pazienti filtrati per nome e cognome
     */
    @GetMapping("/nome/{nome}/cognome/{cognome}")
    public List<PazienteDTO> getPazientiByNomeAndCognome(@PathVariable String nome, @PathVariable String cognome) {
        return pazienteService.getPazientiByNomeAndCognome(nome, cognome);
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome e id del medico (foreign key medicoId in paziente)
     * @param nome nome utente
     * @param cognome cognome utente
     * @param medicoId id del medico
     * @return lista di pazienti filtrati per nome, cognome, id del medico
     */
    @GetMapping("/nome/{nome}/cognome/{cognome}/medico/{medicoId}")
    public List<PazienteDTO> getPazientiByNomeAndCognomeAndMedicoId(@PathVariable String nome, @PathVariable String cognome, @PathVariable Long medicoId) {
        return pazienteService.getPazientiByNomeAndCognomeAndMedicoId(nome, cognome, medicoId);
    }

    /**
     * Ricerca e restituisce i pazienti per nome e cognome e id del segretario
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param nome nome utente
     * @param cognome cognome utente
     * @param segretarioId id del segretario
     * @return lista di pazienti filtrati per nome, cognome, id del segretario
     */
    @GetMapping("/nome/{nome}/cognome/{cognome}/segretario/{segretarioId}")
    public List<PazienteDTO> getPazientiByNomeAndCognomeAndSegretarioId(@PathVariable String nome, @PathVariable String cognome, @PathVariable Long segretarioId) {
        return pazienteService.getPazientiByNomeAndCognomeAndSegretarioId(nome, cognome, segretarioId);
    }

}
