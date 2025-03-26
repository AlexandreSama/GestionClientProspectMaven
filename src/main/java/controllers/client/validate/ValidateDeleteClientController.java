package controllers.client.validate;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import models.Adresse;
import models.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateDeleteClientController implements ICommand {

    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ValidateDeleteClientController.class.getName());

    public ValidateDeleteClientController(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws Exception {
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
        Integer userId = (Integer) userList.getFirst();

        // Récupérer les identifiants sensibles stockés en session
        Integer sessionSocieteId = (Integer) session.getAttribute("societeId");
        Integer sessionAdresseId = (Integer) session.getAttribute("adresseId");

        if (sessionSocieteId == null || sessionAdresseId == null) {
            request.setAttribute("error", "Erreur de session, veuillez réessayer.");
            return "erreur.jsp";
        }

        // Construction de l'objet Client en se basant sur les identifiants sécurisés
        Client deletedClient = new Client(
                Integer.parseInt(request.getParameter("clientId")),
                new Adresse(
                        sessionAdresseId,
                        request.getParameter("codePostal"),
                        request.getParameter("nomRue"),
                        request.getParameter("numeroRue"),
                        request.getParameter("ville")
                ),
                request.getParameter("email"),
                "",
                request.getParameter("raisonSociale"),
                request.getParameter("phone"),
                Long.parseLong(request.getParameter("chiffreAffaire")),
                Integer.parseInt(request.getParameter("nbEmploye")),
                userId
        );
        deletedClient.setIdentifiant(sessionSocieteId);

        String sqlCheck = "SELECT s.gestionnaire FROM client c JOIN societe s ON c.idSociete = s.idSociete WHERE c.idClient = ?";
        try (PreparedStatement psCheck = connection.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, deletedClient.getIdentifiantClient());
            try (ResultSet rsCheck = psCheck.executeQuery()) {
                if (rsCheck.next()) {
                    int gestionnaire = rsCheck.getInt("gestionnaire");
                    if (gestionnaire != userId) {
                        request.setAttribute("error", "Vous n'êtes pas autorisé à supprimer ce client.");
                        return "erreur.jsp";
                    }
                } else {
                    request.setAttribute("error", "Client non trouvé.");
                    return "erreur.jsp";
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Erreur lors de la vérification de "
                    +
                    "l'autorisation. Veuillez réessayer plus tard.");
            return "erreur.jsp";
        }

        // Validation de l'objet deletedClient via Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(deletedClient);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                errorMessage.append(propertyPath)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("client", deletedClient);
            request.setAttribute("societeId", sessionSocieteId);
            request.setAttribute("adresseId", sessionAdresseId);
            request.setAttribute("error", errorMessage.toString());
            return "client/updateClient.jsp";
        }

        try {

            // Suppression du CLIENT
            String sqlDeleteClient = "DELETE FROM client WHERE idClient = ?";
            try (PreparedStatement ps3 = connection.prepareStatement(sqlDeleteClient)) {
                ps3.setInt(1, deletedClient.getIdentifiantClient());
                ps3.execute();
            }

            // Suppression de la SOCIETE
            String sqlDeleteSociete = "DELETE FROM societe WHERE idSociete = ?";
            try (PreparedStatement ps2 = connection.prepareStatement(sqlDeleteSociete)) {
                ps2.setInt(1, sessionSocieteId);
                ps2.execute();
            }

            // Suppression de l'ADRESSE
            String sqlDeleteAdresse = "DELETE FROM adresse WHERE idAdresse = ?";
            try (PreparedStatement ps1 = connection.prepareStatement(sqlDeleteAdresse)) {
                ps1.setInt(1, sessionAdresseId);
                ps1.execute();
            }
        } catch (SQLException e) {
            LOGGER.severe("Erreur SQL lors de la suppression du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la suppression, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        }

        // Suppression des identifiants sensibles et du token CSRF de la session
        session.removeAttribute("societeId");
        session.removeAttribute("adresseId");
        session.removeAttribute("csrfToken");

        return "redirect:/front?cmd=clients/view";
    }
}
