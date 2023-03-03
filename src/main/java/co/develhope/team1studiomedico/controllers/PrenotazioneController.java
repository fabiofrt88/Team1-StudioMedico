package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
