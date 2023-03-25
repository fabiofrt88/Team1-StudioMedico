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
     * @param medicoCreateDTO il DTO di creazione del medico
     * @return il DTO del medico
     */
    @PostMapping("/create")
    public ResponseEntity createMedico(@Valid @RequestBody MedicoCreateDTO medicoCreateDTO) {
        MedicoDTO medicoDTO = medicoService.createMedico(medicoCreateDTO);
        logger.info("Un nuovo medico con id {} è stato registrato", medicoDTO.getId());
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
     * @return il DTO del medico tramite id
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
        return ResponseEntity.status(200).body(new ResponseDataSuccessDTO<>("Medico con id " + id + " modificato correttamente", medicoDTO));
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
        logger.info("Medico con id {} è stato cancellato", id);
        return ResponseEntity.status(200).body("Medico con id " + id + " cancellato correttamente");
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
        logger.info("Medico con id {} è stato ripristinato", id);
        return ResponseEntity.status(200).body("Medico con id " + id + " ripristinato correttamente");
    }

    /**
     * Ripristina i medici e restituisce una response entity di status 200.
     *
     * @return la response entity di status 200.
     */
    @PutMapping("/restore/all")
    public ResponseEntity<String> restoreAllMedici() {
        medicoService.restoreAllMedici();
        logger.warn("Tutti i medici sono stati ripristinati");
        return ResponseEntity.status(200).body("Medici ripristinati correttamente");
    }

    /**
     * Ricerca e restituisce il medico a partire dall'id del segretario (foreign key medicoId in segretario)
     * @param segretarioId id del segretario
     * @return il DTO del medico
     */
    @GetMapping("/segretario/{segretarioId}")
    public MedicoDTO getMedicoBySegretarioId(@PathVariable Long segretarioId) {
        return medicoService.getMedicoBySegretarioId(segretarioId);
    }

    /**
     * Ricerca e restituisce il medico a partire dall'id del paziente (foreign key medicoId in paziente)
     * @param pazienteId id del paziente
     * @return il DTO del medico
     */
    @GetMapping("/paziente/{pazienteId}")
    public MedicoDTO getMedicoByPazienteId(@PathVariable Long pazienteId) {
        return medicoService.getMedicoByPazienteId(pazienteId);
    }

    /**
     * Ricerca e restituisce il medico a partire dall'id della prenotazione
     * (foreign key medicoId in prenotazione)
     * @param prenotazioneId id della prenotazione
     * @return il DTO del medico
     */
    @GetMapping("/prenotazione/{prenotazioneId}")
    public MedicoDTO getMedicoByPrenotazioneId(@PathVariable Long prenotazioneId) {
        return medicoService.getMedicoByPrenotazioneId(prenotazioneId);
    }

    /**
     * Ricerca e restituisce il medico per email
     * @param email email di ricerca
     * @return il DTO del medico
     */
    @GetMapping("/email/{email}")
    public MedicoDTO getMedicoByEmail(@PathVariable String email) {
        return medicoService.getMedicoByEmail(email);
    }

    /**
     * Ricerca e restituisce i medici per nome e cognome
     * @param nome nome utente
     * @param cognome cognome utente
     * @return lista dei medici filtrati per nome e cognome
     */
    @GetMapping("/nome/{nome}/cognome/{cognome}")
    public List<MedicoDTO> getMediciByNomeAndCognome(@PathVariable String nome, @PathVariable String cognome) {
        return medicoService.getMediciByNomeAndCognome(nome, cognome);
    }

}
