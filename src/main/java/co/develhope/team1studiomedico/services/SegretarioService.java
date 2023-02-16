package co.develhope.team1studiomedico.services;

import co.develhope.team1studiomedico.repositories.SegretarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SegretarioService {

    @Autowired
    private SegretarioRepository segretarioRepository;

}
