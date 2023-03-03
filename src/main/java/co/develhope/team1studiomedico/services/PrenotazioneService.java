package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La classe PrenotazioneService realizza la logica di business relativamente le operazioni di CRUD dei dati di PrenotazioneEntity.
 * Utilizza PrenotazioneRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PrenotazioneController
 */
@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

}
