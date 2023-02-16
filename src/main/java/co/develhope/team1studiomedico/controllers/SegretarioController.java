package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.Segretario;
import co.develhope.team1studiomedico.services.SegretarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/segretario")
public class SegretarioController {

    @Autowired
    private SegretarioService segretarioService;

    @PostMapping("/create")
    public ResponseEntity<String> createSegretario(@RequestBody Segretario segretario){
        if(segretario == null) throw new IllegalArgumentException();
        segretarioService.createSegretario(segretario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Segretario creato correttamente");
    }

    @GetMapping("/{id}")
    public Segretario getSegretarioById(@PathVariable Long id) {
        if(id == null) throw new IllegalArgumentException();
        return segretarioService.getSegretarioById(id);
    }

    @GetMapping({"", "/"})
    public List<Segretario> getSegretari(){
        return segretarioService.getSegretari();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateSegretarioById(@RequestBody Segretario segretarioEdit, @PathVariable Long id) {
        if(segretarioEdit == null || id == null) throw new IllegalArgumentException();
        segretarioService.updateSegretarioById(segretarioEdit, id);
        return ResponseEntity.status(200).body("Segretario modificato correttamente");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSegreatrioById(@PathVariable Long id) {

        if(id == null) throw new IllegalArgumentException();
        segretarioService.deleteSegretarioById(id);
        return ResponseEntity.status(200).body("Segretario cancellato correttamente");

    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSegretari() {
        segretarioService.deleteSegretari();
        return ResponseEntity.status(200).body("Segretari cancellati correttamente");
    }




}
