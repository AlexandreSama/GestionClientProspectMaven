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

import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateAddClientController implements ICommand {

    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ValidateAddClientController.class.getName());

    public ValidateAddClientController(final Connection connection) {
        this.connection = connection;
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

        // Récupération de l'utilisateur connecté depuis la session
        List<?> userList = (List<?>) session.getAttribute("user");
        Integer userId = (Integer) userList.getFirst();

        Client addedClient = new Client(
                new Adresse(
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

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(addedClient);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                errorMessage.append(propertyPath)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("client", addedClient);
            request.setAttribute("error", errorMessage.toString());
            return "client/createClient.jsp";
        }

        // Démarrage de la transaction
        try {
            connection.setAutoCommit(false);

            // Ajout de l'adresse
            String sqlAddAdresse = "INSERT INTO adresse (numeroDeRue, nomDeRue, codePostal, ville) VALUES (?, ?, ?, ?)";
            int idAdresse;
            try (PreparedStatement ps1 = connection.prepareStatement(sqlAddAdresse, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, addedClient.getAdresse().getNumeroDeRue());
                ps1.setString(2, addedClient.getAdresse().getNomDeRue());
                ps1.setString(3, addedClient.getAdresse().getCodePostal());
                ps1.setString(4, addedClient.getAdresse().getVille());
                ps1.executeUpdate();

                try (ResultSet rs = ps1.getGeneratedKeys()) {
                    if (rs.next()) {
                        idAdresse = rs.getInt(1);
                    } else {
                        throw new SQLException("Erreur, aucun ID d'adresse généré");
                    }
                }
            }

            // Ajout de la société
            String sqlAddSociete = "INSERT INTO societe (adresseMail, commentaire, telephone, raisonSociale, idAdresse, gestionnaire) VALUES (?, ?, ?, ?, ?, ?)";
            int idSociete;
            try (PreparedStatement ps2 = connection.prepareStatement(sqlAddSociete, Statement.RETURN_GENERATED_KEYS)) {
                ps2.setString(1, addedClient.getAdresseMail());
                ps2.setString(2, addedClient.getCommentaire());
                ps2.setString(3, addedClient.getTelephone());
                ps2.setString(4, addedClient.getRaisonSociale());
                ps2.setInt(5, idAdresse);
                ps2.setInt(6, addedClient.getGestionnaire());
                ps2.executeUpdate();

                try (ResultSet rs = ps2.getGeneratedKeys()) {
                    if (rs.next()) {
                        idSociete = rs.getInt(1);
                    } else {
                        throw new SQLException("Erreur, aucun ID de société généré");
                    }
                }
            }

            // Ajout du client
            String sqlAddClient = "INSERT INTO client (chiffreAffaire, nbrEmploye, idSociete) VALUES (?, ?, ?)";
            try (PreparedStatement ps3 = connection.prepareStatement(sqlAddClient)) {
                ps3.setLong(1, addedClient.getChiffreAffaire());
                ps3.setLong(2, addedClient.getNbrEmploye());
                ps3.setInt(3, idSociete);
                ps3.executeUpdate();
            }

            // Tout s'est bien passé : commit de la transaction
            connection.commit();
        } catch (SQLException e) {
            // En cas d'erreur, rollback de la transaction
            connection.rollback();
            LOGGER.severe("Erreur SQL lors de la création du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de création, veuillez réessayer plus tard.");
            return "client/listeClient.jsp";
        } finally {
            // Réactivation de l'auto-commit
            connection.setAutoCommit(true);
        }

        session.removeAttribute("csrfToken");
        return "redirect:/front?cmd=clients/view";
    }
}
