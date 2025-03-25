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

    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        String tokenFromSession = (String) session.getAttribute("csrfToken");
        String tokenFromForm = request.getParameter("csrfToken");

        // Vérification du token CSRF
        if (tokenFromSession == null || !tokenFromSession.equals(tokenFromForm)) {
            request.setAttribute("error", "Requête invalide (token CSRF non valide).");
            return "erreur.jsp";
        }

        // Récupérer l'utilisateur stocké en session (ici sous forme de List)
        List<?> userList = (List<?>) session.getAttribute("user");
        // On suppose que l'ID de l'utilisateur est stocké à l'indice 0
        Integer userId = (Integer) userList.getFirst();

        LOGGER.info("chiffreAffaire : " + request.getParameter("chiffreAffaire"));
        LOGGER.info("nbEmploye : " + request.getParameter("nbEmploye"));
        LOGGER.info("clientId : " + request.getParameter("clientId"));
        LOGGER.info("idAdresse : " + request.getParameter("idAdresse"));

        // Construction de l'objet Client mis à jour
        Client updatedClient = new Client(
                Integer.parseInt(request.getParameter("clientId")),
                new Adresse(
                        Integer.parseInt(request.getParameter("idAdresse")),
                        request.getParameter("codePostal"),
                        request.getParameter("nomRue"),
                        request.getParameter("numeroRue"),
                        request.getParameter("ville")
                ),
                request.getParameter("email"),
                "", // commentaire (vide ou à récupérer selon votre logique)
                request.getParameter("raisonSociale"),
                request.getParameter("phone"),
                Long.parseLong(request.getParameter("chiffreAffaire")),
                Integer.parseInt(request.getParameter("nbEmploye")),
                userId  // Utilisation de l'ID utilisateur récupéré
        );

        updatedClient.setIdentifiant(Integer.valueOf(request.getParameter("idSociete")));

        // Validation de l'objet updatedClient via Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Client>> violations = validator.validate(updatedClient);

        LOGGER.info("client : " + updatedClient);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Client> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                errorMessage.append(propertyPath)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("error", errorMessage.toString());
            return "client/updateClient.jsp";
        }

        try {
            // Requête pour UPDATE de l'ADRESSE
            String sqlUpdateAdresse = "UPDATE adresse SET ville = ?, codePostal = ?, nomDeRue = ?, numeroDeRue = ? WHERE idAdresse = ?";
            PreparedStatement ps1 = connection.prepareStatement(sqlUpdateAdresse);
            ps1.setString(1, updatedClient.getAdresse().getVille());
            ps1.setString(2, updatedClient.getAdresse().getCodePostal());
            ps1.setString(3, updatedClient.getAdresse().getNomDeRue());
            ps1.setString(4, updatedClient.getAdresse().getNumeroDeRue());
            ps1.setString(5, request.getParameter("idAdresse"));
            ps1.executeUpdate();
            ps1.close();

            // Requête pour UPDATE de la SOCIETE
            String sqlUpdateSociete = "UPDATE societe SET telephone = ?, adresseMail = ?, raisonSociale = ?, commentaire = ? WHERE idSociete = ?";
            PreparedStatement ps2 = connection.prepareStatement(sqlUpdateSociete);
            ps2.setString(1, updatedClient.getTelephone());
            ps2.setString(2, updatedClient.getAdresseMail());
            ps2.setString(3, updatedClient.getRaisonSociale());
            ps2.setString(4, updatedClient.getCommentaire());
            ps2.setString(5, request.getParameter("idSociete"));
            ps2.executeUpdate();
            ps2.close();

            // Requête pour UPDATE du CLIENT
            String sqlUpdateClient = "UPDATE client SET chiffreAffaire = ?, nbrEmploye = ? WHERE idClient = ?";
            PreparedStatement ps3 = connection.prepareStatement(sqlUpdateClient);
            ps3.setLong(1, updatedClient.getChiffreAffaire());
            ps3.setInt(2, updatedClient.getNbrEmploye());
            ps3.setInt(3, updatedClient.getIdentifiantClient());
            ps3.executeUpdate();
            ps3.close();
        } catch (SQLException e) {
            LOGGER.severe("Erreur lors de la mise à jour du client : " + e.getMessage());
            request.setAttribute("error", "Erreur lors de la mise à jour, veuillez réessayer.");
            return "client/listeClient.jsp";
        }

        // Supprimer le token CSRF de la session après validation
        session.removeAttribute("csrfToken");

        return "client/listeClient.jsp";
    }
}
