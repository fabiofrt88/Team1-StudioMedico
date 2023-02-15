package co.develhope.team1studiomedico.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;



}
