package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.entities.Segretario;
import co.develhope.team1studiomedico.exceptions.NotFoundException;
import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SegretarioService {

    @Autowired
    private SegretarioRepository segretarioRepository;

    public void createSegretario(Segretario segretario) {

        segretario.setId(null);
        segretarioRepository.saveAndFlush(segretario);

    }

    public Segretario getSegretarioById(Long id) {

        if (!segretarioRepository.existsById(id)) throw new NotFoundException();
        return segretarioRepository.findById(id).get();

    }

    public List<Segretario> getSegretari() {

        return segretarioRepository.findAll();

    }

    public void updateSegretarioById(Segretario segretarioEdit, Long id) {

        if (segretarioEdit == null) throw new IllegalArgumentException();
        if (!segretarioRepository.existsById(id)) throw new NotFoundException();

        Segretario segretario = segretarioRepository.findById(id).get();

        if (segretarioEdit.getNome() != null) segretario.setNome(segretarioEdit.getNome());
        if (segretarioEdit.getCognome() != null) segretario.setCognome(segretarioEdit.getCognome());
        if (segretarioEdit.getTelefono() != null) segretario.setTelefono(segretarioEdit.getTelefono());
        if (segretarioEdit.getEmail() != null) segretario.setEmail(segretarioEdit.getEmail());

        segretarioRepository.saveAndFlush(segretario);

    }

    public void deleteSegretarioById(Long id) {

        if(!segretarioRepository.existsById(id)) throw new NotFoundException();
        segretarioRepository.deleteById(id);

    }

    public void deleteSegretari() {

        segretarioRepository.deleteAll();

    }



}