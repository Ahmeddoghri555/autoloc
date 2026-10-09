# Notes Atelier 3 - Couche Repository (Spring Data JPA)

## Choix d'interface

Les neuf interfaces étendent `JpaRepository<Entité, Long>` : c'est l'interface complète pour une application JPA. Elle réunit le CRUD, les méthodes qui renvoient des `List`, le tri, la pagination et les méthodes propres à JPA (`flush`, `saveAndFlush`, `getReferenceById`...). Toutes les clés primaires sont de type `Long`.

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, findAll renvoie une List, saveAndFlush disponible. |
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, listes, tri, pagination et flush. |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD complet, listes, tri, pagination et flush. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD complet, listes, tri, pagination et flush. |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD complet, listes, tri, pagination et flush. |
| IClientRepository | JpaRepository<Client, Long> | CRUD complet, listes, tri, pagination et flush. |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD complet, listes, tri, pagination et flush. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Permet de lire les paiements. La création et le retrait d'un paiement passent par le Contrat (composition). |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD complet, listes, tri, pagination et flush. |

**Remarque :** les suppressions en lot (`deleteAllInBatch`, `deleteAllByIdInBatch`) contournent le contexte de persistance. La cascade et l'orphanRemoval ne s'appliquent donc pas : à éviter sur les contrats.

## Anomalies SonarQube for IDE

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
|---|---|---|
| Employe.java (9,7) : Remove this unused import 'java.util' | java:S1128 : un import jamais utilisé alourdit le code et peut tromper le lecteur (code mort). | Suppression de `import java.util.*;` |
| Maintenance.java (10,7) : Remove this unused import 'java.util' | java:S1128 : même règle. | Suppression de `import java.util.*;` |
| Paiement.java (11,7) : Remove this unused import 'java.util' | java:S1128 : même règle. | Suppression de `import java.util.*;` |
| Reservation.java (10,7) : Remove this unused import 'java.util' | java:S1128 : même règle. | Suppression de `import java.util.*;` |

## Vérification

Au démarrage de l'application, les logs indiquent : `Found 9 JPA repository interfaces.`

## Atelier 3 - Part 2 : tests des JpaRepository

Classe : `src/test/java/tn/esprit/autoloc/AutolocRepositoryTests.java`

- `testAgenceRepository` : save, findAll, findById, existsById, count, deleteById.
- `testClientRepository` : deux clients, findAll, findById, existsById (id existant et inexistant), count.
- `testVehiculeRepository` : save, findAll, count, modification puis save, deleteById.
- Exercice autonome : `testEmployeRepository`, `testEquipementRepository`, `testPaiementRepository`.
