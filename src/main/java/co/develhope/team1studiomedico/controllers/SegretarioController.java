package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.SegretarioEntity;
import co.develhope.team1studiomedico.services.SegretarioService;
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

    Logger logger = LoggerFactory.getLogger(SegretarioController.class);

    /**
     * Crea un segretario, restituisce una response entity di status 201.
     *
     * @param segretario il segretario
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createSegretario(@RequestBody SegretarioEntity segretario){
        if(segretario == null) {
            throw new IllegalArgumentException("Bad Request - Error request body");
        }
        segretarioService.createSegretario(segretario);
        logger.info("Un nuovo Segretario è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body("Segretario creato correttamente");
    }

    /**
     * Restituisce la lista dei segretari.
     *
     * @return la list
     */
    @GetMapping({"", "/"})
    public List<SegretarioEntity> getSegretari(){
        return segretarioService.getSegretari();
    }

    /**
     * Restituisce il segretario tramite id.
     *
     * @param id  id
     * @return il segretario tramite id
     */
    @GetMapping("/{id}")
    public SegretarioEntity getSegretarioById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
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
    public ResponseEntity<String> updateSegretarioById(@RequestBody SegretarioEntity segretarioEdit, @PathVariable Long id) {
        if(segretarioEdit == null || id == null) {
            throw new IllegalArgumentException("Bad Request - Error request body");
        }
        segretarioService.updateSegretarioById(segretarioEdit, id);
        return ResponseEntity.status(200).body("Segretario modificato correttamente");
    }

    /**
     * Ripristina il segretario tramite id, restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreSegretarioById(@PathVariable Long id){
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
        segretarioService.restoreSegretarioById(id);
        return ResponseEntity.status(200).body("Segretario ripristinato correttamente");
    }

    /**
     * Ripristina i medici response entity, restituisce una response entity di status 200.
     *
     * @return la response entity
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreMedici(){
        segretarioService.restoreAllSegretari();
        return ResponseEntity.status(200).body("Segretari ripristinati correttamente");
    }

    /**
     * Cancella il segretario tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSegretarioById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
        segretarioService.deleteSegretarioById(id);
        logger.info("Un Segretario è stato cancellato");
        return ResponseEntity.status(200).body("Segretario cancellato correttamente");
    }

    /**
     * Cancella i segretari, restituisce una response entity di status 200 (soft delete).
     *
     * @return the response entity
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteSegretari() {
        segretarioService.deleteSegretari();
        logger.warn("Tutti i Segretari sono stati cancellati");
        return ResponseEntity.status(200).body("Segretari cancellati correttamente");
    }

}