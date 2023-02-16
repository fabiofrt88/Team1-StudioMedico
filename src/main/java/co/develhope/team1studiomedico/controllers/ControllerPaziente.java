package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.entities.Paziente;
import co.develhope.team1studiomedico.repositories.RepositoryPaziente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paziente")
public class ControllerPaziente {

    @Autowired
    private RepositoryPaziente repositoryPaziente;

    //creazione di un singolo paziente tramite un JSON
    @PostMapping("/crearepaziente")
    public Paziente paziente(@RequestBody Paziente paziente){
        return repositoryPaziente.saveAndFlush(paziente);
    }

    //metodo che ritorna una lista contenente tutti i pazienti
    @GetMapping("/getpazienti")
    public List<Paziente> getPazienti(){
        return repositoryPaziente.findAll();
    }



}
