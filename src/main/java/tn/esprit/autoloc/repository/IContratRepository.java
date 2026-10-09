package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.entities.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
