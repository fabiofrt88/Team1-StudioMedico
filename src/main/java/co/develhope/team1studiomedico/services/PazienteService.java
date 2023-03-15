package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.dto.PazienteCreateDTO;
import co.develhope.team1studiomedico.dto.PazienteDTO;
import co.develhope.team1studiomedico.entities.EntityStatusEnum;
import co.develhope.team1studiomedico.entities.PazienteEntity;
import co.develhope.team1studiomedico.repositories.PazienteRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe PazienteService realizza la logica di business relativamente le operazioni di CRUD dei dati di PazienteEntity.
 * Utilizza PazienteRepository (mediante dependency injection), i metodi del service verranno richiamati
 * nel relativo controller PazienteController
 */
@Service
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(PazienteService.class);

    /**
     * Metodo che crea il paziente.
     *
     * @param pazienteCreateDTO il DTO di creazione del paziente
     */
    public PazienteDTO createPaziente(PazienteCreateDTO pazienteCreateDTO) {
        try {
            logger.info("Inizio processo createPaziente in PazienteService");
            PazienteEntity paziente = convertToEntity(pazienteCreateDTO);
            paziente.setId(null);
            paziente.setRecordStatus(EntityStatusEnum.ACTIVE);
            return convertToDTO(pazienteRepository.saveAndFlush(paziente));
        } finally {
            logger.info("Fine processo createPaziente in PazienteService");
        }
    }

    /**
     * Metodo che restituisce i pazienti con record status ACTIVE.
     *
     * @return i pazienti con record status ACTIVE
     */
    public List<PazienteDTO> getAllPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.ACTIVE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce i pazienti cancellati logicamente con record status DELETED.
     *
     * @return i pazienti cancellati logicamente con record status DELETED.
     */
    public List<PazienteDTO> getAllDeletedPazienti() {
        return pazienteRepository.findByRecordStatus(EntityStatusEnum.DELETED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce il paziente tramite id.
     *
     * @param id the id
     * @return the paziente by id
     */
    public PazienteDTO getPazienteById(Long id) {
        PazienteEntity paziente = pazienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paziente non trovato"));
        return convertToDTO(paziente);
    }

    /**
     * Metodo che modifica il paziente tramite id.
     *
     * @param pazienteEdit the paziente edit
     * @param id           the id
     */
    public PazienteDTO updatePazienteById(PazienteDTO pazienteEdit, Long id) {
        PazienteEntity paziente = pazienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paziente non trovato"));

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

        return convertToDTO(pazienteRepository.saveAndFlush(paziente));
    }

    /**
     * Metodo che cancella il paziente tramite id (soft delete).
     *
     * @param id l'id
     */
    public void deletePazienteById(Long id) {
        try {
            logger.info("Inizio processo deletePazienteById in PazienteService");
            if(!pazienteRepository.existsById(id)) {
                throw new EntityNotFoundException("Paziente non trovato");
            }
            pazienteRepository.softDeleteById(id);
        } finally {
            logger.info("Fine processo deletePazienteById in PazienteService");
        }
    }

    /**
     * Metodo che cancella i pazienti (soft delete).
     */
    public void deleteAllPazienti() {
        try {
            logger.info("Inizio processo deleteAllPazienti in PazienteService");
            pazienteRepository.softDelete();
        } finally {
            logger.info("Fine processo deleteAllPazienti in PazienteService");
        }
    }

    /**
     * Metodo che ripristina il paziente tramite id.
     *
     * @param id l'id
     */
    public void restorePazienteById(Long id) {
        if(!pazienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Paziente non trovato");
        }
        pazienteRepository.restoreById(id);
    }

    /**
     * Metodo che ripristina i pazienti.
     */
    public void restoreAllPazienti() {
        pazienteRepository.restore();
    }

    public PazienteEntity convertToEntity(PazienteCreateDTO pazienteCreateDTO) {
        return modelMapper.map(pazienteCreateDTO, PazienteEntity.class);
    }

    public PazienteEntity convertToEntity(PazienteDTO pazienteDTO) {
        return modelMapper.map(pazienteDTO, PazienteEntity.class);
    }

    public PazienteDTO convertToDTO(PazienteEntity paziente) {
        return modelMapper.map(paziente, PazienteDTO.class);
    }

}
