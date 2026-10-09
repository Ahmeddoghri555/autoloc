package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.entities.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
