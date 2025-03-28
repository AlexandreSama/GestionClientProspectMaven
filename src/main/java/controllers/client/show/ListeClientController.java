package controllers.client.show;

import controllers.ICommand;
import controllers.client.dao.ClientDAO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Client;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller servant pour la page de liste de client.
 */
public class ListeClientController implements ICommand {

    /**
     * La variable pour la connexion BDD.
     */
    private final EntityManager em;

    /**
     * Le logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ListeClientController.class.getName());

    /**
     * Constructeur du controller.
     *
     * @param em l'EntityManager à utiliser.
     */
    public ListeClientController(final EntityManager em) {
        this.em = em;
    }

    /**
     * Méthode d'exécution du controller.
     *
     * @param request  La requête reçue.
     * @param response La réponse à renvoyer si besoin.
     * @return Le nom de la page demandée.
     * @throws Exception en cas d'erreur.
     */
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) throws Exception {
        try {

            HttpSession session = request.getSession();
            // Récupérer l'utilisateur stocké en session (ici sous forme de List)
            List<?> userList = (List<?>) session.getAttribute("user");
            // On suppose que l'ID de l'utilisateur est stocké à l'indice 0
            Integer userId = (Integer) userList.getFirst();

            // Utilisation du DAO pour récupérer les clients du gestionnaire connecté.
            ClientDAO clientDAO = new ClientDAO(em);
            List<Client> clientList = clientDAO.findByGestionnaire(userId);

            if (clientList == null || clientList.isEmpty()) {
                request.setAttribute("error", "Erreur, vous n'avez aucun client à votre actif");
                LOGGER.info("Aucun client trouvé");
                return "client/listeClient.jsp";
            }

            // Envoi de la liste des clients à la vue.
            request.setAttribute("clients", clientList);
        } catch (Exception e) {
            request.setAttribute("error", "Erreur: Veuillez réessayer plus tard");
            LOGGER.info("Erreur: " + e.getMessage());
            return "index.jsp";
        }
        return "client/listeClient.jsp";
    }
}
