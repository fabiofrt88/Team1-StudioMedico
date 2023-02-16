package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.Paziente;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    public void createPaziente(Paziente paziente) {
        paziente.setId(null);
        pazienteRepository.saveAndFlush(paziente);
    }

    public List<Paziente> getPazienti() {
        return pazienteRepository.findAll();
    }

    public Paziente getPazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) throw new NotFoundException();
        return pazienteRepository.findById(id).get();
    }

    public void updatePazienteById(Paziente pazienteEdit, Long id) {
        if(pazienteEdit == null) throw new IllegalArgumentException();
        if(!pazienteRepository.existsById(id)) throw new NotFoundException();

        Paziente paziente = pazienteRepository.findById(id).get();
        /*Paziente paziente = new Paziente();
        Optional<Paziente> pazienteOptional = pazienteRepository.findById(id);
        if(pazienteOptional.isPresent()) paziente = pazienteOptional.get();*/

        if(pazienteEdit.getNome() != null) paziente.setNome(pazienteEdit.getNome());
        if(pazienteEdit.getCognome() != null) paziente.setCognome(pazienteEdit.getCognome());
        if(pazienteEdit.getDataNascita() != null) paziente.setDataNascita(pazienteEdit.getDataNascita());
        if(pazienteEdit.getTelefono() != null) paziente.setTelefono(pazienteEdit.getTelefono());
        if(pazienteEdit.getEmail() != null) paziente.setEmail(pazienteEdit.getEmail());
        if(pazienteEdit.getCodiceFiscale() != null) paziente.setCodiceFiscale(pazienteEdit.getCodiceFiscale());

        pazienteRepository.saveAndFlush(paziente);
    }

    public void deletePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) throw new NotFoundException();
        pazienteRepository.deleteById(id);
    }

    public void deletePazienti() {
        pazienteRepository.deleteAll();
    }

}
