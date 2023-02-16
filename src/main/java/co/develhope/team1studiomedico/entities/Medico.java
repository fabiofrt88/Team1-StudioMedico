package co.develhope.team1studiomedico.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "medico")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;

    @Column(unique = true)
    private String email;

    private String telefono;

    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "medico",fetch = FetchType.LAZY)
    private List<Paziente> pazienti;


    public Medico(){}

    public Medico(Long id, String nome, String cognome, String email, String telefono, List<Prenotazione> prenotazioni, List<Paziente> pazienti) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.prenotazioni = prenotazioni;
        this.pazienti = pazienti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public List<Paziente> getPazienti() {
        return pazienti;
    }

    public void setPazienti(List<Paziente> pazienti) {
        this.pazienti = pazienti;
    }
}
