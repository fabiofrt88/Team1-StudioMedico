package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.Medico;
import co.develhope.team1studiomedico.entities.Paziente;
import co.develhope.team1studiomedico.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;


    @PostMapping("/create")
    public ResponseEntity<String> createMedico(@RequestBody Medico medico){
        if(medico == null) throw new IllegalArgumentException();
        medicoService.createMedico(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body("Medico creato correttamente");
    }

    @GetMapping({"", "/"})
    public List<Medico> getMedici(){
        return medicoService.getMedici();
    }

    @GetMapping("/{id}")
    public Medico getMedicoById(@PathVariable Long id) {
        if(id == null) throw new IllegalArgumentException();
        return medicoService.getMedicoById(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateMedicoById(@RequestBody Medico medicoEdit, @PathVariable Long id) {
        if(medicoEdit == null || id == null) throw new IllegalArgumentException();
        medicoService.updateMedicoById(medicoEdit, id);
        return ResponseEntity.status(200).body("Medico modificato correttamente");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMedici() {
        medicoService.deleteMedici();
        return ResponseEntity.status(200).body("Medici cancellati correttamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicoById(@PathVariable Long id) {
        if(id == null) throw new IllegalArgumentException();
        medicoService.deleteMedicoById(id);
        return ResponseEntity.status(200).body("Medico cancellato correttamente");
    }

}
