package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.MedicoEntity;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;


    public void createMedico(MedicoEntity medico) {
        medico.setId(null);
        medico.setStatus(EntityStatusEnum.ACTIVE);
        medicoRepository.saveAndFlush(medico);
    }

    public List<MedicoEntity> getMedici() {
        return medicoRepository.findAll();
    }

    public MedicoEntity getMedicoById(Long id) {
        if(!medicoRepository.existsById(id)) throw new NotFoundException("Medico non trovato");
        return medicoRepository.findById(id).get();
    }

    public void updateMedicoById(MedicoEntity medicoEdit, Long id) {
        if(medicoEdit == null) throw new IllegalArgumentException();
        if(!medicoRepository.existsById(id)) throw new NotFoundException("Medico non trovato");

        MedicoEntity medico = medicoRepository.findById(id).get();

        if(medicoEdit.getNome() != null) medico.setNome(medicoEdit.getNome());
        if(medicoEdit.getCognome() != null) medico.setCognome(medicoEdit.getCognome());
        if(medicoEdit.getTelefono() != null) medico.setTelefono(medicoEdit.getTelefono());
        if(medicoEdit.getEmail() != null) medico.setEmail(medicoEdit.getEmail());

        medicoRepository.saveAndFlush(medico);
    }

    public void restoreMedicoById(Long id){
        if (!medicoRepository.existsById(id))throw new NotFoundException("Medico non trovato");
        medicoRepository.restoreById(id);
    }

    public void restoreAllMedici(){
        medicoRepository.restore();
    }


    public void deleteMedicoById(Long id) {
        if(!medicoRepository.existsById(id)) throw new NotFoundException("Medico non trovato");
        medicoRepository.softDeleteById(id);
    }

    public void deleteMedici() {
        medicoRepository.softDelete();
    }



}
