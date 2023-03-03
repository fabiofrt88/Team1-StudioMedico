package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.services.PazienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Il PazienteController ha la responsabilità di gestire le operazioni CRUD di Paziente.
 */
@RestController
@RequestMapping("/paziente")
public class PazienteController {

    @Autowired
    private PazienteService pazienteService;

    Logger logger = LoggerFactory.getLogger(PazienteController.class);


    /**
     * Crea paziente response entity.
     *
     * @param paziente il paziente
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPaziente(@RequestBody PazienteEntity paziente){
        if(paziente == null) {
            throw new IllegalArgumentException();
        }
        pazienteService.createPaziente(paziente);
        logger.info("Un nuovo Paziente è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body("Paziente creato correttamente");
    }

    /**
     * Ottiene i pazienti list.
     *
     * @return la list
     */
    @GetMapping({"", "/"})
    public List<PazienteEntity> getPazienti(){
        return pazienteService.getPazienti();
    }

    /**
     * Ottiene il paziente tramite id.
     *
     * @param id  id
     * @return il paziente tramite id
     */
    @GetMapping("/{id}")
    public PazienteEntity getPazienteById(@PathVariable Long id) {
        /*Optional<Paziente> paziente = pazienteService.getPazienteById(id);
        if(paziente.isPresent()) {
            return paziente.get();
        }
        return new Paziente();*/
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return pazienteService.getPazienteById(id);
    }

    /**
     * Update il paziente tramite id response entity.
     *
     * @param pazienteEdit la modifica al paziente
     * @param id            id
     * @return la response entity di status 200
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updatePazienteById(@RequestBody PazienteEntity pazienteEdit, @PathVariable Long id) {
        if(pazienteEdit == null || id == null) {
            throw new IllegalArgumentException();
        }
        pazienteService.updatePazienteById(pazienteEdit, id);
        return ResponseEntity.status(200).body("Paziente modificato correttamente");
    }

    /*@PutMapping("/restore/{id}")
    public ResponseEntity<String> restorePazienteById(@PathVariable Long id) {
        if(id == null) throw new IllegalArgumentException();
        pazienteService.restorePazienteById(id);
        return ResponseEntity.status(200).body("Paziente ripristinato correttamente");
    }

    @PutMapping("paziente/restore/all")
    public ResponseEntity<String> restorePazienti() {
        pazienteService.restorePazienti();
        return ResponseEntity.status(200).body("Pazienti ripristinati correttamente");
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deletePazienti() {
        pazienteService.deletePazienti();
        return ResponseEntity.status(200).body("Pazienti cancellati correttamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePazienteById(@PathVariable Long id) {
        if(id == null) throw new IllegalArgumentException();
        pazienteService.deletePazienteById(id);
        return ResponseEntity.status(200).body("Paziente cancellato correttamente");
    }*/

}
