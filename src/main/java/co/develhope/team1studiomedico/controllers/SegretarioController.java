package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.success.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.dto.segretario.SegretarioCreateDTO;
import co.develhope.team1studiomedico.dto.segretario.SegretarioDTO;
import co.develhope.team1studiomedico.services.SegretarioService;
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
 * SegretarioController rappresenta la web API controller del Segretario,
 * espone degli endpoint circa le operazioni CRUD di SegretarioEntity,
 * elabora le response sulla base delle relative request del client
 */
@RestController
@RequestMapping("/segretario")
public class SegretarioController {

    @Autowired
    private SegretarioService segretarioService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(SegretarioController.class);

    /**
     * Crea un segretario, restituisce una response entity di status 201.
     *
     * @param segretarioCreateDTO il DTO di creazione del segretario
     * @return il DTO del segretario
     */
    @PostMapping("/create")
    public ResponseEntity createSegretario(@Valid @RequestBody SegretarioCreateDTO segretarioCreateDTO) {
        SegretarioDTO segretarioDTO = segretarioService.createSegretario(segretarioCreateDTO);
        logger.info("Un nuovo segretario con id {} è stato registrato", segretarioDTO.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("segretario.controller.create",
                null, LocaleContextHolder.getLocale()), segretarioDTO));
    }

    /**
     * Restituisce la lista dei segretari con record status ACTIVE.
     *
     * @return la lista dei segretari con record status ACTIVE
     */
    @GetMapping({"", "/"})
    public List<SegretarioDTO> getAllSegretari() {
        return segretarioService.getAllSegretari();
    }

    /**
     * Restituisce la lista dei segretari cancellati logicamente con record status DELETED.
     *
     * @return la lista dei segretari cancellati logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<SegretarioDTO> getAllDeletedSegretari() {
        return segretarioService.getAllDeletedSegretari();
    }

    /**
     * Restituisce il segretario tramite id.
     *
     * @param id  id
     * @return il DTO del segretario tramite id
     */
    @GetMapping("/{id}")
    public SegretarioDTO getSegretarioById(@PathVariable Long id) {
        return segretarioService.getSegretarioById(id);
    }

    /**
     * Update del segretario tramite id, restituisce una response entity di status 200.
     *
     * @param segretarioEdit le modifiche segretario
     * @param id              id
     * @return la response entity
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity updateSegretarioById(@Valid @RequestBody SegretarioDTO segretarioEdit, @PathVariable Long id) {
        SegretarioDTO segretarioDTO = segretarioService.updateSegretarioById(segretarioEdit, id);
        return ResponseEntity.status(200).body(new ResponseDataSuccessDTO<>(messageSource.getMessage("segretario.controller.update",
                new Object[]{id}, LocaleContextHolder.getLocale()), segretarioDTO));
    }

    /**
     * Cancella i segretari, restituisce una response entity di status 200 (soft delete).
     *
     * @return la response entity
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllSegretari() {
        segretarioService.deleteAllSegretari();
        logger.warn("Tutti i segretari sono stati cancellati");
        return ResponseEntity.status(200).body(messageSource.getMessage("segretario.controller.deleteAllSegretari",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Cancella il segretario tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id the id
     * @return la response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSegretarioById(@PathVariable Long id) {
        segretarioService.deleteSegretarioById(id);
        logger.info("Segretario con id {} è stato cancellato", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("segretario.controller.delete",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina il segretario tramite id, restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreSegretarioById(@PathVariable Long id) {
        segretarioService.restoreSegretarioById(id);
        logger.info("Segretario con id {} è stato ripristinato", id);
        return ResponseEntity.status(200).body(messageSource.getMessage("segretario.controller.restore",
                new Object[]{id}, LocaleContextHolder.getLocale()));
    }

    /**
     * Ripristina i segretari, restituisce una response entity di status 200.
     *
     * @return la response entity
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllSegretari() {
        segretarioService.restoreAllSegretari();
        logger.warn("Tutti i segretari sono stati ripristinati");
        return ResponseEntity.status(200).body(messageSource.getMessage("segretario.controller.restoreAllSegretari",
                null, LocaleContextHolder.getLocale()));
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id del medico (foreign key in segretario)
     * @param medicoId id del medico
     * @return il DTO del segretario
     */
    @GetMapping("/medico/{medicoId}")
    public SegretarioDTO getSegretarioByMedicoId(@PathVariable Long medicoId) {
        return segretarioService.getSegretarioByMedicoId(medicoId);
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id del paziente,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param pazienteId id del paziente
     * @return il DTO del segretario
     */
    @GetMapping("/paziente/{pazienteId}")
    public SegretarioDTO getSegretarioByPazienteId(@PathVariable Long pazienteId) {
        return segretarioService.getSegretarioByPazienteId(pazienteId);
    }

    /**
     * Ricerca e restituisce il segretario a partire dall'id della prenotazione,
     * le due tabelle hanno in comune l'id del medico (foreign key)
     * @param prenotazioneId id della prenotazione
     * @return il DTO del segretario
     */
    @GetMapping("/prenotazione/{prenotazioneId}")
    public SegretarioDTO getSegretarioByPrenotazioneId(@PathVariable Long prenotazioneId) {
        return segretarioService.getSegretarioByPrenotazioneId(prenotazioneId);
    }

    /**
     * Ricerca e restituisce il segretario per email
     * @param email email di ricerca
     * @return il DTO del segretario
     */
    @GetMapping("/email/{email}")
    public SegretarioDTO getSegretarioByEmail(@PathVariable String email) {
        return segretarioService.getSegretarioByEmail(email);
    }

    /**
     * Ricerca e restituisce i segretari per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista dei segretari filtrati per nome e cognome
     */
    @GetMapping("/nome/{nome}/cognome/{cognome}")
    public List<SegretarioDTO> getSegretariByNomeAndCognome(@PathVariable String nome, @PathVariable String cognome) {
        return segretarioService.getSegretariByNomeAndCognome(nome, cognome);
    }

}