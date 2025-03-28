package controllers.client.show;

import controllers.ICommand;
import controllers.client.dao.ClientDAO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Client;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class UpdateClientController implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(UpdateClientController.class.getName());

    /**
     * Constructeur qui reçoit l'EntityManager.
     *
     * @param em l'EntityManager utilisé pour la persistance.
     */
    public UpdateClientController(final EntityManager em) {
        this.em = em;
    }

    /**
     * Méthode d'exécution du controller.
     * Récupère le client correspondant à l'id passé en paramètre (si le client appartient
     * à l'utilisateur connecté) et prépare les informations pour la page de modification.
     *
     * @param request  La requête HTTP.
     * @param response La réponse HTTP.
     * @return Le nom de la vue à afficher.
     * @throws Exception en cas d'erreur.
     */
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            // Récupération de l'id du client à modifier
            String clientIdParam = request.getParameter("id");
            int clientId = Integer.parseInt(clientIdParam);

            HttpSession session = request.getSession();
            // Récupération de l'utilisateur connecté depuis la session
            // On suppose ici que l'id de l'utilisateur est stocké dans la session en première position
            List<?> userList = (List<?>) session.getAttribute("user");
            Integer userId = (Integer) userList.getFirst();

            // Utilisation du DAO pour récupérer le client par son id
            ClientDAO clientDAO = new ClientDAO(em);
            Client client = clientDAO.findById(clientId);

            // Vérification que le client existe et appartient à l'utilisateur connecté
            if (client == null || !client.getGestionnaire().getIdentifiantUser().equals(userId)) {
                request.setAttribute("error", "Erreur, Client non trouvé ou accès refusé");
                LOGGER.info("Client non trouvé ou accès refusé pour l'id " + clientId);
                return "client/listeClient.jsp";
            }

            // Stocker en session certains identifiants utiles (par exemple, l'id de la société et de l'adresse)
            session.setAttribute("societeId", client.getIdentifiant());
            session.setAttribute("adresseId", client.getAdresse().getIdentifiant());

            // Génération et stockage du token CSRF
            String token = generateToken();
            session.setAttribute("csrfToken", token);
            request.setAttribute("token", token);

            // Mise à disposition du client pour la vue de mise à jour
            request.setAttribute("client", client);

            return "client/updateClient.jsp";
        } catch (Exception e) {
            request.setAttribute("error", "Erreur, veuillez réessayer plus tard");
            LOGGER.info("Erreur lors de la récupération du client: " + e.getMessage());
            return "client/listeClient.jsp";
        }
    }

    /**
     * Génère un token CSRF sécurisé.
     *
     * @return le token CSRF encodé en Base64.
     */
    private static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
