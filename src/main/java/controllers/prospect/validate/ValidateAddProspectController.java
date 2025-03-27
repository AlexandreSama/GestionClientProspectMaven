package controllers.prospect.validate;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import models.Adresse;
import models.Prospect;
import models.types.InterestedType;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class ValidateAddProspectController implements ICommand {

    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(ValidateAddProspectController.class.getName());

    public ValidateAddProspectController(final Connection connection) {
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

        Prospect addedProspect = new Prospect(
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
                LocalDate.parse(request.getParameter("dateProspection")),
                request.getParameter("estInteresse").equals("OUI") ? InterestedType.OUI : InterestedType.NON,
                userId
        );

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Prospect>> violations = validator.validate(addedProspect);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Prospect> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                errorMessage.append(propertyPath)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("prospect", addedProspect);
            request.setAttribute("error", errorMessage.toString());
            return "prospect/createProspect.jsp";
        }

        try {
            // Désactivation de l'auto-commit pour débuter une transaction
            connection.setAutoCommit(false);

            // Ajout de l'adresse
            String sqlAddAdresse = "INSERT INTO adresse (numeroDeRue, nomDeRue, codePostal, ville) VALUES (?, ?, ?, ?)";
            int idAdresse = 0;
            try (PreparedStatement ps1 = connection.prepareStatement(sqlAddAdresse, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, addedProspect.getAdresse().getNumeroDeRue());
                ps1.setString(2, addedProspect.getAdresse().getNomDeRue());
                ps1.setString(3, addedProspect.getAdresse().getCodePostal());
                ps1.setString(4, addedProspect.getAdresse().getVille());
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
            int idSociete = 0;
            try (PreparedStatement ps2 = connection.prepareStatement(sqlAddSociete, Statement.RETURN_GENERATED_KEYS)) {
                ps2.setString(1, addedProspect.getAdresseMail());
                ps2.setString(2, addedProspect.getCommentaire());
                ps2.setString(3, addedProspect.getTelephone());
                ps2.setString(4, addedProspect.getRaisonSociale());
                ps2.setInt(5, idAdresse);
                ps2.setInt(6, addedProspect.getGestionnaire());
                ps2.executeUpdate();

                try (ResultSet rs = ps2.getGeneratedKeys()) {
                    if (rs.next()) {
                        idSociete = rs.getInt(1);
                    } else {
                        throw new SQLException("Erreur, aucun ID de société généré");
                    }
                }
            }

            // Ajout du prospect
            String sqlAddProspect = "INSERT INTO prospect (dateProspection, estInteresse, idSociete) VALUES (?, ?, ?)";
            try (PreparedStatement ps3 = connection.prepareStatement(sqlAddProspect)) {
                ps3.setString(1, addedProspect.getDateProspection().toString());
                ps3.setInt(2, addedProspect.getEstInteresse().toString().equals("OUI") ? 1 : 0);
                ps3.setInt(3, idSociete);
                ps3.executeUpdate();
            }

            // Si tout se passe bien, on valide la transaction
            connection.commit();
        } catch (SQLException e) {
            // En cas d'erreur, on annule toutes les opérations effectuées dans la transaction
            connection.rollback();
            LOGGER.severe("Erreur SQL lors de la création du prospect : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de création, veuillez réessayer plus tard.");
            return "prospect/listeProspect.jsp";
        } finally {
            // Il est important de réactiver l'auto-commit après la transaction
            connection.setAutoCommit(true);
        }

        session.removeAttribute("csrfToken");
        return "redirect:/front?cmd=prospects/view";
    }
}
