package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.entities.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
}
