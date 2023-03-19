package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.dto.MedicoCreateDTO;
import co.develhope.team1studiomedico.dto.MedicoDTO;
import co.develhope.team1studiomedico.dto.ResponseDataSuccessDTO;
import co.develhope.team1studiomedico.services.MedicoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MedicoController rappresenta la web API controller del Medico,
 * espone degli endpoint circa le operazioni CRUD di MedicoEntity,
 * elabora le response sulla base delle relative request del client
 */
@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    private static final Logger logger = LoggerFactory.getLogger(MedicoController.class);

    /**
     * Crea un medico, restituisce una response entity di status 201.
     *
     * @param medico il DTO di creazione del medico
     * @return la response entity
     */

    @PostMapping("/create")
    public ResponseEntity createMedico(@Valid @RequestBody MedicoCreateDTO medico) {
        MedicoDTO medicoDTO = medicoService.createMedico(medico);
        logger.info("Un nuovo medico è stato registrato");
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDataSuccessDTO<>("Medico creato correttamente", medicoDTO));
    }

    /**
     * Restituisce la lista dei medici con record status ACTIVE.
     *
     * @return la lista dei medici con record status ACTIVE
     */
    @GetMapping({"", "/"})
    public List<MedicoDTO> getAllMedici() {
        return medicoService.getAllMedici();
    }

    /**
     * Restituisce la lista dei medici cancellati logicamente con record status DELETED.
     *
     * @return la lista dei medici cancellati logicamente con record status DELETED.
     */
    @GetMapping("/deleted")
    public List<MedicoDTO> getAllDeletedMedici() {
        return medicoService.getAllDeletedMedici();
    }

    /**
     * Restituisce il medico tramite id
     *
     * @param id  id
     * @return il medico tramite id
     */
    @GetMapping("/{id}")
    public MedicoDTO getMedicoById(@PathVariable Long id) {
        return medicoService.getMedicoById(id);
    }

    /**
     * Update del medico tramite id, restituisce una response entity di status 200.
     *
     * @param medicoEdit il medico edit
     * @param id id
     * @return la response entity di status 200.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity updateMedicoById(@Valid @RequestBody MedicoDTO medicoEdit, @PathVariable Long id) {
        MedicoDTO medicoDTO = medicoService.updateMedicoById(medicoEdit, id);
        return ResponseEntity.status(200).body(new ResponseDataSuccessDTO<>("Medico modificato correttamente", medicoDTO));
    }

    /**
     * Cancella i medici, restituisce una response entity di status 200 (soft delete).
     *
     * @return la response entity di status 200.
     */
    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAllMedici() {
        medicoService.deleteAllMedici();
        logger.warn("Tutti i medici sono stati cancellati");
        return ResponseEntity.status(200).body("Medici cancellati correttamente");
    }

    /**
     * Cancella il medico tramite id, restituisce una response entity di status 200 (soft delete).
     *
     * @param id  id
     * @return la response entity di status 200
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicoById(@PathVariable Long id) {
        medicoService.deleteMedicoById(id);
        logger.info("Un medico è stato cancellato");
        return ResponseEntity.status(200).body("Medico cancellato correttamente");
    }

    /**
     * Ripristina il medico tramite id e restituisce una response entity di status 200.
     *
     * @param id  id
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/{id}")
    public ResponseEntity<String> restoreMedicoById(@PathVariable Long id) {
        medicoService.restoreMedicoById(id);
        return ResponseEntity.status(200).body("Medico ripristinato correttamente");
    }

    /**
     * Ripristina i medici e restituisce una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllMedici() {
        medicoService.restoreAllMedici();
        return ResponseEntity.status(200).body("Medici ripristinati correttamente");
    }

}
