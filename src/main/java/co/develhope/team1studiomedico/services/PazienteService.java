package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    public void createPaziente(PazienteEntity paziente) {
        paziente.setId(null);
        paziente.setStatus(EntityStatusEnum.ACTIVE);
        pazienteRepository.saveAndFlush(paziente);
    }

    public List<PazienteEntity> getPazienti() {
        return pazienteRepository.findAll();
    }

    public PazienteEntity getPazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
        }
        return pazienteRepository.findById(id).get();
    }

    public void updatePazienteById(PazienteEntity pazienteEdit, Long id) {
        if(pazienteEdit == null) {
            throw new IllegalArgumentException();
        }
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
        }

        PazienteEntity paziente = pazienteRepository.findById(id).get();
        /*Paziente paziente = new Paziente();
        Optional<Paziente> pazienteOptional = pazienteRepository.findById(id);
        if(pazienteOptional.isPresent()) paziente = pazienteOptional.get();*/

        if(pazienteEdit.getNome() != null) {
            paziente.setNome(pazienteEdit.getNome());
        }
        if(pazienteEdit.getCognome() != null) {
            paziente.setCognome(pazienteEdit.getCognome());
        }
        if(pazienteEdit.getDataNascita() != null) {
            paziente.setDataNascita(pazienteEdit.getDataNascita());
        }
        if(pazienteEdit.getTelefono() != null) {
            paziente.setTelefono(pazienteEdit.getTelefono());
        }
        if(pazienteEdit.getEmail() != null) {
            paziente.setEmail(pazienteEdit.getEmail());
        }
        if(pazienteEdit.getCodiceFiscale() != null) {
            paziente.setCodiceFiscale(pazienteEdit.getCodiceFiscale());
        }

        pazienteRepository.saveAndFlush(paziente);
    }

    public void restorePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new NotFoundException("Paziente non trovato");
        }
        pazienteRepository.restoreById(id);
        //pazienteRepository.changeStatusById(EntityStatusEnum.ACTIVE, id);
    }

    public void restorePazienti() {
        pazienteRepository.restore();
    }

    public void deletePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) throw new NotFoundException("Paziente non trovato");
        /*PazienteEntity paziente = pazienteRepository.findById(id).get();
        paziente.setStatus(EntityStatusEnum.DELETED);
        pazienteRepository.saveAndFlush(paziente);*/
        pazienteRepository.softDeleteById(id);
        //pazienteRepository.changeStatusById(EntityStatusEnum.DELETED, id);
    }

    public void deletePazienti() {
        pazienteRepository.softDelete();
    }

}
