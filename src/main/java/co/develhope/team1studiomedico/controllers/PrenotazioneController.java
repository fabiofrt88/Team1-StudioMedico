package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.PrenotazioneEntity;
import co.develhope.team1studiomedico.entities.PrenotazioneStatusEnum;
import co.develhope.team1studiomedico.services.PrenotazioneService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param prenotazione il prenotazione
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPrenotazione(@RequestBody PrenotazioneEntity prenotazione){
        if(prenotazione == null) {
            throw new IllegalArgumentException("Bad Request - Error request body");
        }
        /*if(!prenotazioneService.countPrenotazioniByDay(prenotazione.getDataPrenotazione())) {
            throw new IllegalArgumentException("Data non disponibile");
        }*/
        prenotazione.setStatoPrenotazione(PrenotazioneStatusEnum.PENDING);
        prenotazioneService.createPrenotazione(prenotazione);
        logger.info("Una nuova prenotazione è stata registrata");
        return ResponseEntity.status(HttpStatus.CREATED).body("Prenotazione creata correttamente");
    }

    /**
     * Restituisce la lista delle prenotazioni con record status ACTIVE.
     *
     * @return la lista delle prenotazioni con record status ACTIVE.
     */
    @GetMapping({"", "/"})
    public List<PrenotazioneEntity> getAllPrenotazioni(){
        return prenotazioneService.getAllPrenotazioni();
    }

    /**
     * Restituisce la lista delle prenotazioni cancellate logicamente con record status DELETED.
     *
     * @return la lista delle prenotazioni cancellate logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<PrenotazioneEntity> getAllDeletedPrenotazioni(){
        return prenotazioneService.getAllDeletedPrenotazioni();
    }

    /**
     * Restituisce la prenotazione tramite id
     *
     * @param id  id
     * @return la prenotazione tramite id
     */
    @GetMapping("/{id}")
    public PrenotazioneEntity getPrenotazioneById(@PathVariable Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
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
    public ResponseEntity<String> updatePrenotazioneById(@RequestBody PrenotazioneEntity prenotazioneEdit, @PathVariable Long id) {
        if(prenotazioneEdit == null || id == null) {
            throw new IllegalArgumentException("Bad Request - Error request body");
        }
        prenotazioneService.updatePrenotazioneById(prenotazioneEdit, id);
        return ResponseEntity.status(200).body("Prenotazione modificata correttamente");
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
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
        prenotazioneService.deletePrenotazioneById(id);
        logger.info("Una prenotazione è stata cancellata");
        return ResponseEntity.status(200).body("Prenotazione cancellata correttamente");
    }

    /**
     * Ripristina la prenotazione tramite id e restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restorePrenotazioneById(@PathVariable Long id){
        if(id == null) {
            throw new IllegalArgumentException("Bad Request - Error id request param");
        }
        prenotazioneService.restorePrenotazioneById(id);
        return ResponseEntity.status(200).body("Prenotazione ripristinata correttamente");
    }

    /**
     * Ripristina tutte le prenotazioni e restituisce una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllPrenotazioni(){
        prenotazioneService.restoreAllPrenotazioni();
        return ResponseEntity.status(200).body("Prenotazioni ripristinate correttamente");
    }

}
