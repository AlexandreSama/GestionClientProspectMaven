package controllers.client.dao;

import models.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ClientDAO {

    private final EntityManager entityManager;

    /**
     * Constructeur qui reçoit l'EntityManager.
     *
     * @param entityManager l'EntityManager à utiliser pour les opérations de persistance.
     */
    public ClientDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Retourne la liste de tous les clients présents dans la base de données.
     *
     * @return une liste de Client.
     */
    public List<Client> findAll() {
        // Requête JPQL sans paramètres (pas de risque d'injection)
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    /**
     * Recherche un client par son identifiant en utilisant une requête paramétrée.
     *
     * @param id l'identifiant du client recherché.
     * @return le Client trouvé ou null s'il n'existe pas.
     */
    public Client findById(int id) {
        // Utilisation d'une requête paramétrée pour sécuriser l'accès aux données
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE c.identifiantSociete = :id", Client.class);
        query.setParameter("id", id);
        List<Client> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    /**
     * Retourne la liste des clients dont le gestionnaire (User) a l'identifiant fourni.
     *
     * @param gestionnaireId l'identifiant du gestionnaire.
     * @return une liste de Client.
     */
    public List<Client> findByGestionnaire(int gestionnaireId) {
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE c.gestionnaire.identifiantUser = :userId", Client.class);
        query.setParameter("userId", gestionnaireId);
        return query.getResultList();
    }

    /**
     * Sauvegarde (crée ou met à jour) un client dans la base de données.
     *
     * Si le client n'a pas d'identifiant (null), il est persisté,
     * sinon il est fusionné (mise à jour).
     *
     * @param client le client à sauvegarder.
     */
    public void save(Client client) {
        entityManager.getTransaction().begin();
        if (client.getIdentifiant() == null) {
            entityManager.persist(client);
        } else {
            entityManager.merge(client);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * Supprime un client de la base de données.
     *
     * @param client le client à supprimer.
     */
    public void delete(Client client) {
        entityManager.getTransaction().begin();
        if (!entityManager.contains(client)) {
            client = entityManager.merge(client);
        }
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }
}
