package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.dto.SegretarioCreateDTO;
import co.develhope.team1studiomedico.dto.SegretarioDTO;
import co.develhope.team1studiomedico.services.PazienteService;
import co.develhope.team1studiomedico.services.SegretarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PazienteService pazienteService;

    private static final Logger logger = LoggerFactory.getLogger(SegretarioController.class);

    /**
     * Crea un segretario, restituisce una response entity di status 201.
     *
     * @param segretario il DTO di creazione del segretario
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity createSegretario(@Valid @RequestBody SegretarioCreateDTO segretario) {
        SegretarioDTO segretarioDTO = segretarioService.createSegretario(segretario);
        logger.info("Un nuovo segretario è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Segretario creato correttamente", segretarioDTO));
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
     * @return il segretario tramite id
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
        return ResponseEntity.status(200).body(new ResponseDataSuccessDTO<>("Segretario modificato correttamente", segretarioDTO));
    }

    /**
     * Cancella il segretario tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSegretarioById(@PathVariable Long id) {
        segretarioService.deleteSegretarioById(id);
        logger.info("Un segretario è stato cancellato");
        return ResponseEntity.status(200).body("Segretario cancellato correttamente");
    }

    /**
     * Cancella i segretari, restituisce una response entity di status 200 (soft delete).
     *
     * @return the response entity
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllSegretari() {
        segretarioService.deleteAllSegretari();
        logger.warn("Tutti i segretari sono stati cancellati");
        return ResponseEntity.status(200).body("Segretari cancellati correttamente");
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
        return ResponseEntity.status(200).body("Segretario ripristinato correttamente");
    }

    /**
     * Ripristina i medici response entity, restituisce una response entity di status 200.
     *
     * @return la response entity
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllSegretari() {
        segretarioService.restoreAllSegretari();
        return ResponseEntity.status(200).body("Segretari ripristinati correttamente");
    }

    //Metodi Paziente

    /**
     * Cancella i pazienti, restituisce una response entity di status 200 (soft delete).
     *
     * @return the response entity
     */
    @DeleteMapping("/paziente/delete/all")
    public ResponseEntity<String> deleteAllPazienti() {
        pazienteService.deleteAllPazienti();
        logger.warn("Tutti i pazienti sono stati cancellati");
        return ResponseEntity.status(200).body("Pazienti cancellati correttamente");
    }

    /**
     * Cancella il paziente tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/paziente/delete/{id}")
    public ResponseEntity<String> deletePazienteById(@PathVariable Long id) {
        pazienteService.deletePazienteById(id);
        logger.info("Un paziente è stato cancellato");
        return ResponseEntity.status(200).body("Paziente cancellato correttamente");
    }

    /**
     * Ripristina il paziente tramite id, restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity
     */
    @PutMapping("/paziente/restore/{id}")
    public ResponseEntity<String> restorePazienteById(@PathVariable Long id) {
        pazienteService.restorePazienteById(id);
        return ResponseEntity.status(200).body("Paziente ripristinato correttamente");
    }

    /**
     * Ripristina i pazienti, restituisce una response entity di status 200.
     *
     * @return la response entity
     */
    @PutMapping("/paziente/restore/all")
    public ResponseEntity<String> restoreAllPazienti() {
        pazienteService.restoreAllPazienti();
        return ResponseEntity.status(200).body("Pazienti ripristinati correttamente");
    }

}