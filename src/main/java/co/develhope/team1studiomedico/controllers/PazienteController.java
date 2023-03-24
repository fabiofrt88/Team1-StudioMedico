package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.MedicoDTO;
import co.develhope.team1studiomedico.dto.PazienteCreateDTO;
import co.develhope.team1studiomedico.dto.PazienteDTO;
import co.develhope.team1studiomedico.dto.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.services.PazienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Paziente creato correttamente", pazienteDTO));
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Paziente con id + " + id + " modificato correttamente", pazienteDTO));
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
        return ResponseEntity.status(200).body("Pazienti cancellati correttamente");
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
        return ResponseEntity.status(200).body("Paziente con id + " + id + " cancellato correttamente");
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
        return ResponseEntity.status(200).body("Paziente con id + " + id + " ripristinato correttamente");
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
        return ResponseEntity.status(200).body("Pazienti ripristinati correttamente");
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

}
