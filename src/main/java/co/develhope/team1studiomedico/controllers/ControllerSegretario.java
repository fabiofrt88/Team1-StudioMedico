package co.develhope.team1studiomedico.controllers;

import co.develhope.team1studiomedico.repositories.RepositoryMedico;
import co.develhope.team1studiomedico.repositories.RepositorySegretario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/segretario")
public class ControllerSegretario {

    @Autowired
    RepositorySegretario repositorySegretario;
}
