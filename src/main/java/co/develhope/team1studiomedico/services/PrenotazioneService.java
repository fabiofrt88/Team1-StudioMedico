package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * La classe PrenotazioneService e dove si effettuano le operazioni di logica di businness che verranno richiamate
 * dal CRUD del PrenotazioneController.
 */
@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

}
