package controllers.client.validate;

import controllers.ICommand;
import controllers.client.dao.ClientDAO;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import models.Client;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateDeleteClientController implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(ValidateDeleteClientController.class.getName());

    public ValidateDeleteClientController(final EntityManager em) {
        this.em = em;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String tokenFromSession = (String) session.getAttribute("csrfToken");
        String tokenFromForm = request.getParameter("csrfToken");

        // Vérification du token CSRF
        if (tokenFromSession == null || !tokenFromSession.equals(tokenFromForm)) {
            request.setAttribute("error", "Requête invalide (token CSRF non valide).");
            return "erreur.jsp";
        }

        // Récupération des informations de l'utilisateur connecté depuis la session
        // On suppose que l'attribut "user" est une List avec l'id en indice 0
        List<?> userList = (List<?>) session.getAttribute("user");
        if (userList == null || userList.isEmpty()) {
            request.setAttribute("error", "Utilisateur non connecté.");
            return "erreur.jsp";
        }
        Integer userId = (Integer) userList.getFirst();

        // Récupération de l'id du client à supprimer depuis le paramètre "clientId"
        String clientIdParam = request.getParameter("clientId");
        if (clientIdParam == null) {
            request.setAttribute("error", "Client non spécifié.");
            return "erreur.jsp";
        }
        int clientId = Integer.parseInt(clientIdParam);

        // Utilisation du DAO pour récupérer le client
        ClientDAO clientDAO = new ClientDAO(em);
        Client client = clientDAO.findById(clientId);
        if (client == null) {
            request.setAttribute("error", "Client non trouvé.");
            return "erreur.jsp";
        }

        // Vérification que le client appartient bien à l'utilisateur connecté
        if (!client.getGestionnaire().getIdentifiantUser().equals(userId)) {
            request.setAttribute("error", "Vous n'êtes pas autorisé à supprimer ce client.");
            return "erreur.jsp";
        }

        // Validation de l'objet client (optionnelle pour la suppression)
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("error", errorMessage.toString());
            return "client/updateClient.jsp"; // ou une page d'erreur appropriée
        }

        // Suppression du client via le DAO
        try {
            clientDAO.delete(client);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la suppression du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la suppression, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        }

        // Suppression du token CSRF de la session
        session.removeAttribute("csrfToken");

        return "redirect:/front?cmd=clients/view";
    }
}
