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
 * Il controller Segretario ha la responsabilità di gestire le operazioni CRUD di Segretario
 */
@RestController
@RequestMapping("/segretario")
public class SegretarioController {

    @Autowired
    private SegretarioService segretarioService;

    Logger logger = LoggerFactory.getLogger(SegretarioController.class);


    /**
     * Crea segretario response entity.
     *
     * @param segretario il segretario
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createSegretario(@RequestBody SegretarioEntity segretario){
        if(segretario == null) {
            throw new IllegalArgumentException();
        }
        segretarioService.createSegretario(segretario);
        logger.info("Un nuovo Segretatio è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body("Segretario creato correttamente");
    }

    /**
     * Ottiene il segretario tramite id.
     *
     * @param id  id
     * @return il segretario tramite id
     */
    @GetMapping("/{id}")
    public SegretarioEntity getSegretarioById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return segretarioService.getSegretarioById(id);
    }

    /**
     * Ottiene i segretari list.
     *
     * @return la list
     */
    @GetMapping({"", "/"})
    public List<SegretarioEntity> getSegretari(){
        return segretarioService.getSegretari();
    }

    /**
     * Update segretario by id response entity.
     *
     * @param segretarioEdit le modifiche segretario
     * @param id              id
     * @return la response entity
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateSegretarioById(@RequestBody SegretarioEntity segretarioEdit, @PathVariable Long id) {
        if(segretarioEdit == null || id == null) {
            throw new IllegalArgumentException();
        }
        segretarioService.updateSegretarioById(segretarioEdit, id);
        return ResponseEntity.status(200).body("Segretario modificato correttamente");
    }

    /**
     * Ripristina segretario tramite id response entity.
     *
     * @param id  id
     * @return la response entity
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreSegretarioById(@PathVariable Long id){
        if(id == null) {
            throw new IllegalArgumentException();
        }
        segretarioService.restoreSegretarioById(id);
        return ResponseEntity.status(200).body("Segretario ripristinato correttamente");
    }

    /**
     * Rirpistina i medici response entity.
     *
     * @return la response entity
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreMedici(){
        segretarioService.restoreAllSegretari();
        return ResponseEntity.status(200).body("Segretari ripristinati correttamente");
    }

    /**
     * Cancella il segretario tramite id e da una response entity di status 200.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSegretarioById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException();
        }
        segretarioService.deleteSegretarioById(id);
        logger.info("Un Segretario è stato cancellato");
        return ResponseEntity.status(200).body("Segretario cancellato correttamente");
    }

    /**
     * Cancella i segretari e da una response entity di status 200.
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