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

public class ValidateUpdateClientController implements ICommand {

    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ValidateUpdateClientController.class.getName());

    public ValidateUpdateClientController(final Connection connection) {
        this.connection = connection;
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
        Integer userId = (Integer) userList.getFirst();

        // Récupérer les identifiants sensibles stockés en session
        Integer sessionSocieteId = (Integer) session.getAttribute("societeId");
        Integer sessionAdresseId = (Integer) session.getAttribute("adresseId");

        if (sessionSocieteId == null || sessionAdresseId == null) {
            request.setAttribute("error", "Erreur de session, veuillez réessayer.");
            return "erreur.jsp";
        }

        // Construction de l'objet Client en se basant sur les identifiants sécurisés
        Client updatedClient = new Client(
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
        updatedClient.setIdentifiant(sessionSocieteId);

        // Vérifier que l'utilisateur connecté est bien le gestionnaire du client
        String sqlCheck = "SELECT s.gestionnaire FROM client c JOIN societe s ON c.idSociete = s.idSociete WHERE c.idClient = ?";
        try (PreparedStatement psCheck = connection.prepareStatement(sqlCheck)) {
            psCheck.setInt(1, updatedClient.getIdentifiantClient());
            try (ResultSet rsCheck = psCheck.executeQuery()) {
                if (rsCheck.next()) {
                    int gestionnaire = rsCheck.getInt("gestionnaire");
                    if (gestionnaire != userId) {
                        request.setAttribute("error", "Vous n'êtes pas autorisé à modifier ce client.");
                        return "erreur.jsp";
                    }
                } else {
                    request.setAttribute("error", "Client non trouvé.");
                    return "erreur.jsp";
                }
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Erreur lors de la vérification de l'autorisation. Veuillez réessayer plus tard.");
            return "erreur.jsp";
        }

        // Validation de l'objet updatedClient via Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(updatedClient);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                errorMessage.append(propertyPath)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("client", updatedClient);
            request.setAttribute("societeId", sessionSocieteId);
            request.setAttribute("adresseId", sessionAdresseId);
            request.setAttribute("error", errorMessage.toString());
            return "client/updateClient.jsp";
        }

        try {
            // Démarrer la transaction
            connection.setAutoCommit(false);

            // Mise à jour de l'ADRESSE
            String sqlUpdateAdresse = "UPDATE adresse SET ville = ?, codePostal = ?, nomDeRue = ?, numeroDeRue = ? WHERE idAdresse = ?";
            try (PreparedStatement ps1 = connection.prepareStatement(sqlUpdateAdresse)) {
                ps1.setString(1, updatedClient.getAdresse().getVille());
                ps1.setString(2, updatedClient.getAdresse().getCodePostal());
                ps1.setString(3, updatedClient.getAdresse().getNomDeRue());
                ps1.setString(4, updatedClient.getAdresse().getNumeroDeRue());
                ps1.setInt(5, sessionAdresseId);
                ps1.executeUpdate();
            }

            // Mise à jour de la SOCIETE
            String sqlUpdateSociete = "UPDATE societe SET telephone = ?, adresseMail = ?, raisonSociale = ?, commentaire = ? WHERE idSociete = ?";
            try (PreparedStatement ps2 = connection.prepareStatement(sqlUpdateSociete)) {
                ps2.setString(1, updatedClient.getTelephone());
                ps2.setString(2, updatedClient.getAdresseMail());
                ps2.setString(3, updatedClient.getRaisonSociale());
                ps2.setString(4, updatedClient.getCommentaire());
                ps2.setInt(5, sessionSocieteId);
                ps2.executeUpdate();
            }

            // Mise à jour du CLIENT
            String sqlUpdateClient = "UPDATE client SET chiffreAffaire = ?, nbrEmploye = ? WHERE idClient = ?";
            try (PreparedStatement ps3 = connection.prepareStatement(sqlUpdateClient)) {
                ps3.setLong(1, updatedClient.getChiffreAffaire());
                ps3.setInt(2, updatedClient.getNbrEmploye());
                ps3.setInt(3, updatedClient.getIdentifiantClient());
                ps3.executeUpdate();
            }

            // Si tout s'est bien passé, on valide la transaction
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.severe("Erreur SQL lors de la mise à jour du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la mise à jour, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        } finally {
            connection.setAutoCommit(true);
        }

        // Suppression des identifiants sensibles et du token CSRF de la session
        session.removeAttribute("societeId");
        session.removeAttribute("adresseId");
        session.removeAttribute("csrfToken");

        return "redirect:/front?cmd=clients/view";
    }
}
