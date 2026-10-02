package tn.esprit.autoloc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    private String nom;
    private String ville;
    private String adresse;
    private String telephone;

    // Étape 1 : une agence possède plusieurs employés (OneToMany <-> ManyToOne)
    @OneToMany(mappedBy = "agence")
    private Set<Employe> employes = new HashSet<>();

    // Étape 1 : une agence possède plusieurs véhicules (OneToMany <-> ManyToOne)
    @OneToMany(mappedBy = "agence")
    private Set<Vehicule> vehicules = new HashSet<>();
}
