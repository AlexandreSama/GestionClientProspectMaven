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
import models.Adresse;
import models.Client;
import models.User;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateAddClientController implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(ValidateAddClientController.class.getName());

    public ValidateAddClientController(final EntityManager em) {
        this.em = em;
    }

    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String tokenFromSession = (String) session.getAttribute("csrfToken");
        String tokenFromForm = request.getParameter("csrfToken");

        // Vérification du token CSRF
        if (tokenFromSession == null || !tokenFromSession.equals(tokenFromForm)) {
            request.setAttribute("error", "Requête invalide (token CSRF non valide).");
            return "erreur.jsp";
        }

        // Récupération des informations utilisateur depuis la session (seulement id et pseudo)
        // On suppose que l'attribut "user" est une List où l'indice 0 correspond à l'id et l'indice 1 au pseudo
        List<?> userList = (List<?>) session.getAttribute("user");
        if (userList == null || userList.size() < 2) {
            request.setAttribute("error", "Utilisateur non connecté ou informations incomplètes.");
            return "login.jsp";
        }
        Integer userId = (Integer) userList.get(0);
        String username = (String) userList.get(1);
        // Reconstitution d'un objet User minimal
        User connectedUser = new User(userId, "", username);

        // Création de l'adresse à partir des paramètres du formulaire
        Adresse adresse = new Adresse(
                request.getParameter("codePostal"),
                request.getParameter("nomRue"),
                request.getParameter("numeroRue"),
                request.getParameter("ville")
        );

        // Création du client. Le constructeur de Client attend désormais un objet User complet.
        Client addedClient = new Client(
                adresse,
                request.getParameter("email"),
                "",  // Commentaire vide (à adapter si besoin)
                request.getParameter("raisonSociale"),
                request.getParameter("phone"),
                Long.parseLong(request.getParameter("chiffreAffaire")),
                Integer.parseInt(request.getParameter("nbEmploye")),
                connectedUser
        );

        // Validation des contraintes (Bean Validation)
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(addedClient);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("client", addedClient);
            request.setAttribute("error", errorMessage.toString());
            return "client/createClient.jsp";
        }

        // Utilisation du DAO pour sauvegarder le client dans la base de données.
        try {
            ClientDAO clientDAO = new ClientDAO(em);
            clientDAO.save(addedClient);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la création du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de création, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        }

        // Suppression du token CSRF de la session
        session.removeAttribute("csrfToken");
        return "redirect:/front?cmd=clients/view";
    }
}
