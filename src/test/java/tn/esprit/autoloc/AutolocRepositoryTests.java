package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.entities.Agence;
import tn.esprit.autoloc.entities.CategorieVehicule;
import tn.esprit.autoloc.entities.Client;
import tn.esprit.autoloc.entities.Employe;
import tn.esprit.autoloc.entities.Equipement;
import tn.esprit.autoloc.entities.ModePaiement;
import tn.esprit.autoloc.entities.Paiement;
import tn.esprit.autoloc.entities.RoleEmploye;
import tn.esprit.autoloc.entities.StatutVehicule;
import tn.esprit.autoloc.entities.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AutolocRepositoryTests {

    private final IAgenceRepository agenceRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IEmployeRepository employeRepository;
    private final IEquipementRepository equipementRepository;
    private final IPaiementRepository paiementRepository;

    // Injection par constructeur : @Autowired est obligatoire pour que Spring fournisse les repositories
    @Autowired
    AutolocRepositoryTests(IAgenceRepository agenceRepository,
                           IClientRepository clientRepository,
                           IVehiculeRepository vehiculeRepository,
                           IEmployeRepository employeRepository,
                           IEquipementRepository equipementRepository,
                           IPaiementRepository paiementRepository) {
        this.agenceRepository = agenceRepository;
        this.clientRepository = clientRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.employeRepository = employeRepository;
        this.equipementRepository = equipementRepository;
        this.paiementRepository = paiementRepository;
    }

    // ------------------------------------------------------------------
    // Partie 2 : AgenceRepository
    // ------------------------------------------------------------------
    @Test
    void testAgenceRepository() {
        long avant = agenceRepository.count();

        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("12 Avenue Habib Bourguiba");
        agence.setTelephone("71000000");

        // save() : INSERT, l'id est attribue par MySQL
        Agence saved = agenceRepository.save(agence);
        System.out.println("Agence enregistree : " + saved.getIdAgence() + " - " + saved.getNom());
        assertNotNull(saved.getIdAgence());

        // findAll()
        List<Agence> agences = agenceRepository.findAll();
        System.out.println("Liste des agences : " + agences.size());
        agences.forEach(a -> System.out.println("  -> " + a.getIdAgence() + " " + a.getNom()));
        assertFalse(agences.isEmpty());

        // findById()
        Optional<Agence> trouvee = agenceRepository.findById(saved.getIdAgence());
        System.out.println("findById : " + trouvee.map(Agence::getNom).orElse("introuvable"));
        assertTrue(trouvee.isPresent());
        assertEquals("Tunis", trouvee.get().getVille());

        // existsById()
        assertTrue(agenceRepository.existsById(saved.getIdAgence()));

        // count()
        long apres = agenceRepository.count();
        System.out.println("Nombre d'agences : " + apres);
        assertEquals(avant + 1, apres);

        // deleteById() puis verification
        agenceRepository.deleteById(saved.getIdAgence());
        assertFalse(agenceRepository.existsById(saved.getIdAgence()));
        assertEquals(avant, agenceRepository.count());
        System.out.println("Agence supprimee : " + !agenceRepository.existsById(saved.getIdAgence()));
    }

    // ------------------------------------------------------------------
    // Partie 3 : ClientRepository
    // ------------------------------------------------------------------
    @Test
    void testClientRepository() {
        long avant = clientRepository.count();

        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Sami");
        c1.setEmail("sami.benali@example.com");
        c1.setTelephone("20111111");
        c1.setNumPermis("P-1001");
        c1.setDateInscription(LocalDate.now());

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Amel");
        c2.setEmail("amel.trabelsi@example.com");
        c2.setTelephone("20222222");
        c2.setNumPermis("P-1002");
        c2.setDateInscription(LocalDate.now());

        Client s1 = clientRepository.save(c1);
        Client s2 = clientRepository.save(c2);
        System.out.println("Clients enregistres : " + s1.getIdClient() + " et " + s2.getIdClient());
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        // findAll()
        List<Client> clients = clientRepository.findAll();
        System.out.println("Liste des clients : " + clients.size());
        clients.forEach(c -> System.out.println("  -> " + c.getIdClient() + " " + c.getNom() + " " + c.getPrenom()));
        assertTrue(clients.size() >= 2);

        // findById()
        Optional<Client> trouve = clientRepository.findById(s1.getIdClient());
        System.out.println("findById : " + trouve.map(Client::getNom).orElse("introuvable"));
        assertTrue(trouve.isPresent());
        assertEquals("Ben Ali", trouve.get().getNom());

        // existsById() : id existant puis id inexistant
        assertTrue(clientRepository.existsById(s1.getIdClient()));
        assertFalse(clientRepository.existsById(-1L));
        System.out.println("existsById(-1) = " + clientRepository.existsById(-1L));

        // count()
        long apres = clientRepository.count();
        System.out.println("Nombre de clients : " + apres);
        assertEquals(avant + 2, apres);

        // Nettoyage pour pouvoir relancer le test sans accumuler les lignes
        clientRepository.deleteAllById(List.of(s1.getIdClient(), s2.getIdClient()));
        assertEquals(avant, clientRepository.count());
    }

    // ------------------------------------------------------------------
    // Partie 4 : VehiculeRepository
    // ------------------------------------------------------------------
    @Test
    void testVehiculeRepository() {
        long avant = vehiculeRepository.count();

        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Renault");
        v.setModele("Clio");
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal("50.00"));
        v.setStatut(StatutVehicule.DISPONIBLE);

        Vehicule saved = vehiculeRepository.save(v);
        System.out.println("Vehicule enregistre : " + saved.getIdVehicule() + " - " + saved.getMarque() + " " + saved.getModele());
        assertNotNull(saved.getIdVehicule());

        // findAll() et count()
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("Liste des vehicules : " + vehicules.size());
        vehicules.forEach(x -> System.out.println("  -> " + x.getIdVehicule() + " " + x.getImmatriculation()));
        assertEquals(avant + 1, vehiculeRepository.count());
        System.out.println("Nombre de vehicules : " + vehiculeRepository.count());

        // Modification : save() avec un id renseigne = merge (SELECT puis UPDATE)
        saved.setStatut(StatutVehicule.LOUE);
        saved.setTarifJournalier(new BigDecimal("55.00"));
        Vehicule modifie = vehiculeRepository.save(saved);
        System.out.println("Vehicule modifie : statut = " + modifie.getStatut() + ", tarif = " + modifie.getTarifJournalier());

        Vehicule relu = vehiculeRepository.findById(saved.getIdVehicule()).orElseThrow();
        assertEquals(StatutVehicule.LOUE, relu.getStatut());
        assertEquals(0, new BigDecimal("55.00").compareTo(relu.getTarifJournalier()));
        assertEquals(saved.getIdVehicule(), relu.getIdVehicule());

        // deleteById()
        vehiculeRepository.deleteById(saved.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(saved.getIdVehicule()));
        assertEquals(avant, vehiculeRepository.count());
        System.out.println("Vehicule supprime.");
    }

    // ------------------------------------------------------------------
    // Partie 5 : exercice autonome (Employe, Equipement, Paiement)
    // ------------------------------------------------------------------
    @Test
    void testEmployeRepository() {
        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Nour");
        e.setRole(RoleEmploye.AGENT);

        Employe saved = employeRepository.save(e);
        System.out.println("Employe enregistre : " + saved.getIdEmploye());
        assertNotNull(saved.getIdEmploye());

        List<Employe> employes = employeRepository.findAll();
        System.out.println("Liste des employes : " + employes.size());
        assertFalse(employes.isEmpty());

        assertTrue(employeRepository.existsById(saved.getIdEmploye()));

        employeRepository.deleteById(saved.getIdEmploye());
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
        System.out.println("Employe supprime.");
    }

    @Test
    void testEquipementRepository() {
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementRepository.save(eq);
        System.out.println("Equipement enregistre : " + saved.getIdEquipement() + " - " + saved.getLibelle());
        assertNotNull(saved.getIdEquipement());

        Optional<Equipement> trouve = equipementRepository.findById(saved.getIdEquipement());
        assertTrue(trouve.isPresent());
        assertEquals("GPS", trouve.get().getLibelle());
        System.out.println("findById : " + trouve.get().getLibelle());

        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));

        equipementRepository.deleteById(saved.getIdEquipement());
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
        System.out.println("Equipement supprime.");
    }

    @Test
    void testPaiementRepository() {
        Paiement p = new Paiement();
        p.setMontant(new BigDecimal("120.00"));
        p.setDatePaiement(LocalDate.now());
        p.setModePaiement(ModePaiement.CARTE);

        Paiement saved = paiementRepository.save(p);
        System.out.println("Paiement enregistre : " + saved.getIdPaiement() + " - " + saved.getMontant());
        assertNotNull(saved.getIdPaiement());

        List<Paiement> paiements = paiementRepository.findAll();
        System.out.println("Liste des paiements : " + paiements.size());
        assertFalse(paiements.isEmpty());

        assertTrue(paiementRepository.existsById(saved.getIdPaiement()));

        paiementRepository.deleteById(saved.getIdPaiement());
        assertFalse(paiementRepository.existsById(saved.getIdPaiement()));
        System.out.println("Paiement supprime.");
    }
}