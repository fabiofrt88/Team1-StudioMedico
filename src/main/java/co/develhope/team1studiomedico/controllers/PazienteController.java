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
     * @param paziente il paziente
     * @return la response entity
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPaziente(@RequestBody PazienteEntity paziente) {
        pazienteService.createPaziente(paziente);
        logger.info("Un nuovo paziente è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body("Paziente creato correttamente");
    }

    /**
     * Restituisce la lista dei pazienti con record status ACTIVE.
     *
     * @return la lista dei pazienti con record status ACTIVE
     */
    @GetMapping({"", "/"})
    public List<PazienteEntity> getAllPazienti() {
        return pazienteService.getAllPazienti();
    }

    /**
     * Restituisce la lista dei pazienti cancellati logicamente con record status DELETED.
     *
     * @return la lista dei pazienti cancellati logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<PazienteEntity> getAllDeletedPazienti() {
        return pazienteService.getAllDeletedPazienti();
    }

    /**
     * Restituisce il paziente tramite id.
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
    public ResponseEntity<String> updatePazienteById(@RequestBody PazienteEntity pazienteEdit, @PathVariable Long id) {
        pazienteService.updatePazienteById(pazienteEdit, id);
        return ResponseEntity.status(200).body("Paziente modificato correttamente");
    }

}
