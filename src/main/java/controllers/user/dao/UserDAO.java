package controllers.user.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.User;
import java.util.List;

public class UserDAO {

    private final EntityManager entityManager;

    /**
     * Constructeur recevant l'EntityManager à utiliser pour les opérations de persistance.
     *
     * @param entityManager l'EntityManager
     */
    public UserDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Recherche un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur recherché.
     * @return l'utilisateur trouvé ou null s'il n'existe pas.
     */
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Recherche un utilisateur par son pseudo.
     *
     * @param username le pseudo de l'utilisateur recherché.
     * @return l'utilisateur trouvé ou null s'il n'existe pas.
     */
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    /**
     * Sauvegarde (crée ou met à jour) un utilisateur.
     * Si l'utilisateur n'a pas d'identifiant (null), il est persisté, sinon il est fusionné.
     *
     * @param user l'utilisateur à sauvegarder.
     */
    public void save(User user) {
        entityManager.getTransaction().begin();
        if (user.getIdentifiantUser() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        entityManager.getTransaction().commit();
    }
}
