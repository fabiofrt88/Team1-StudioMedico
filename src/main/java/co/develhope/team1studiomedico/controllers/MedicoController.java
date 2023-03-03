package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.services.MedicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Il MedicoController ha la responsabilità di gestire le operazioni CRUD di Medico.
 */
@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    Logger logger = LoggerFactory.getLogger(MedicoController.class);



    /**
     * Crea il medico response entity.
     *
     * @param medico il medico
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createMedico(@RequestBody MedicoEntity medico){
        if(medico == null) {
            throw new IllegalArgumentException();
        }
        medicoService.createMedico(medico);
        logger.info("Un nuovo Medico è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body("Medico creato correttamente");
    }

    /**
     * Ottiene i Medico list.
     *
     * @return la list
     */
    @GetMapping({"", "/"})
    public List<MedicoEntity> getMedici(){
        return medicoService.getMedici();
    }

    /**
     * Ottiene il medico tramite id
     *
     * @param id  id
     * @return il medico tramite id
     */
    @GetMapping("/{id}")
    public MedicoEntity getMedicoById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException();
        }
        return medicoService.getMedicoById(id);
    }

    /**
     * Update il medico tramite id e da una response entity di status 200.
     *
     * @param medicoEdit il medico edit
     * @param id          id
     * @return la response entity di status 200.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateMedicoById(@RequestBody MedicoEntity medicoEdit, @PathVariable Long id) {
        if(medicoEdit == null || id == null) {
            throw new IllegalArgumentException();
        }
        medicoService.updateMedicoById(medicoEdit, id);
        return ResponseEntity.status(200).body("Medico modificato correttamente");
    }

    /**
     * Ripristina il medico tramite id e da una response entity di status 200.
     *
     * @param id  id
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreMedicoById(@PathVariable Long id){
        if(id == null) {
            throw new IllegalArgumentException();
        }
        medicoService.restoreMedicoById(id);
        return ResponseEntity.status(200).body("Medico ripristinato correttamente");
    }

    /**
     * Ripristina i medici e da una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreMedici(){
        medicoService.restoreAllMedici();
        return ResponseEntity.status(200).body("Medici ripristinati correttamente");
    }

    /**
     * Cancella i medici e da una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteMedici() {
        medicoService.deleteMedici();
        logger.warn("Tutti i Medici sono stati cancellati");
        return ResponseEntity.status(200).body("Medici cancellati correttamente");
    }

    /**
     * Cancella il medico tramite id e da una response entity di status 200.
     *
     * @param id  id
     * @return la response entity di status 200
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicoById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException();
        }
        medicoService.deleteMedicoById(id);
        logger.info("Un Medico è stato cancellato");
        return ResponseEntity.status(200).body("Medico cancellato correttamente");
    }

}
