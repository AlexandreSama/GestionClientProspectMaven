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
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateUpdateClientController implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(ValidateUpdateClientController.class.getName());

    public ValidateUpdateClientController(final EntityManager em) {
        this.em = em;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String tokenFromSession = (String) session.getAttribute("csrfToken");
        String tokenFromForm = request.getParameter("csrfToken");

        // Vérification du token CSRF
        if (tokenFromSession == null || !tokenFromSession.equals(tokenFromForm)) {
            request.setAttribute("error", "Requête invalide (token CSRF non valide).");
            return "erreur.jsp";
        }

        // Récupération de l'utilisateur connecté depuis la session
        List<?> userList = (List<?>) session.getAttribute("user");
        if (userList == null || userList.isEmpty()) {
            request.setAttribute("error", "Utilisateur non connecté.");
            return "erreur.jsp";
        }
        Integer userId = (Integer) userList.getFirst();

        // Récupération de l'identifiant du client à mettre à jour depuis le paramètre "clientId"
        String clientIdParam = request.getParameter("clientId");
        if (clientIdParam == null) {
            request.setAttribute("error", "Client non spécifié.");
            return "erreur.jsp";
        }
        int clientId = Integer.parseInt(clientIdParam);

        // Utilisation du DAO pour récupérer le client existant
        ClientDAO clientDAO = new ClientDAO(em);
        Client existingClient = clientDAO.findById(clientId);
        if (existingClient == null) {
            request.setAttribute("error", "Client non trouvé.");
            return "erreur.jsp";
        }
        // Vérification que le client appartient bien à l'utilisateur connecté
        if (!existingClient.getGestionnaire().getIdentifiantUser().equals(userId)) {
            request.setAttribute("error", "Vous n'êtes pas autorisé à modifier ce client.");
            return "erreur.jsp";
        }

        // Mise à jour des champs de l'adresse
        Adresse adresse = existingClient.getAdresse();
        adresse.setCodePostal(request.getParameter("codePostal"));
        adresse.setNomDeRue(request.getParameter("nomRue"));
        adresse.setNumeroDeRue(request.getParameter("numeroRue"));
        adresse.setVille(request.getParameter("ville"));

        // Mise à jour des champs de la société (hérités par Client)
        existingClient.setAdresseMail(request.getParameter("email"));
        // Par exemple, le commentaire est laissé vide ou mis à jour selon le besoin
        existingClient.setCommentaire("");
        existingClient.setRaisonSociale(request.getParameter("raisonSociale"));
        existingClient.setTelephone(request.getParameter("phone"));

        // Mise à jour des champs spécifiques au Client
        existingClient.setChiffreAffaire(Long.parseLong(request.getParameter("chiffreAffaire")));
        existingClient.setNbrEmploye(Integer.parseInt(request.getParameter("nbEmploye")));

        // Validation de l'entité mise à jour via Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(existingClient);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                errorMessage.append(violation.getPropertyPath())
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("client", existingClient);
            request.setAttribute("error", errorMessage.toString());
            return "client/updateClient.jsp";
        }

        // Sauvegarde de l'entité mise à jour via le DAO (la transaction est gérée dans le DAO)
        try {
            clientDAO.save(existingClient);
        } catch (Exception e) {
            LOGGER.severe("Erreur lors de la mise à jour du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la mise à jour, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        }

        // Suppression des identifiants sensibles et du token CSRF de la session
        session.removeAttribute("societeId");
        session.removeAttribute("adresseId");
        session.removeAttribute("csrfToken");

        return "redirect:/front?cmd=clients/view";
    }
}
