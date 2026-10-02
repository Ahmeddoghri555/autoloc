package tn.esprit.autoloc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    // Étape 4 : relation avec Client (plusieurs réservations pour un client)
    @ManyToOne
    private Client client;

    // Étape 4 : relation avec Vehicule (un véhicule peut être réservé plusieurs fois)
    @ManyToOne
    private Vehicule vehicule;

    // Étape 4 : une réservation génère un seul contrat (OneToOne bidirectionnel)
    // Reservation est le côté propriétaire : la clé étrangère contrat_id_contrat est dans la table reservation
    @OneToOne
    private Contrat contrat;
}
