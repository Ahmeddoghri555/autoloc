package tn.esprit.autoloc.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrat;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;

    // Étape 5 : relation inverse avec Reservation (OneToOne, mappedBy = nom de l'attribut dans Reservation)
    @OneToOne(mappedBy = "contrat")
    private Reservation reservation;

    // Étape 5 : un contrat peut avoir plusieurs paiements (OneToMany <-> ManyToOne)
    @OneToMany(mappedBy = "contrat")
    private Set<Paiement> paiements = new HashSet<>();
}
