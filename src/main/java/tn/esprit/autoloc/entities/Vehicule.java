package tn.esprit.autoloc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;

    // Étape 2 : un véhicule appartient à une seule agence (ManyToOne)
    @ManyToOne
    private Agence agence;

    // Étape 2 : un véhicule peut être réservé plusieurs fois (OneToMany <-> ManyToOne)
    @OneToMany(mappedBy = "vehicule")
    private Set<Reservation> reservations = new HashSet<>();

    // Étape 2 : un véhicule peut avoir plusieurs maintenances (OneToMany <-> ManyToOne)
    @OneToMany(mappedBy = "vehicule")
    private Set<Maintenance> maintenances = new HashSet<>();

    // Étape 2 : ManyToMany avec Equipement (Vehicule est le côté propriétaire :
    // Hibernate crée la table de jointure vehicule_equipements)
    @ManyToMany
    private Set<Equipement> equipements = new HashSet<>();
}
