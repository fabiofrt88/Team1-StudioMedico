package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Il PrenotazioneController ha la responsabilità di gestire le operazioni CRUD di Prenotazione.
 */
@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

}
