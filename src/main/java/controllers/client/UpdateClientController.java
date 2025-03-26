package controllers.client;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Adresse;
import models.Client;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

public class UpdateClientController implements ICommand {

    private final Connection connection;
    private static final Logger LOGGER = Logger.getLogger(UpdateClientController.class.getName());

    public UpdateClientController(final Connection connection) {
        this.connection = connection;
    }

    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String clientId = request.getParameter("id");
        HttpSession session = request.getSession();
        // Récupération de l'utilisateur connecté depuis la session
        List<?> userList = (List<?>) session.getAttribute("user");
        Integer userId = (Integer) userList.getFirst();

        try {
            String sql = "SELECT c.*, s.*, a.* " +
                    "FROM client c " +
                    "JOIN societe s ON c.idSociete = s.idSociete " +
                    "JOIN adresse a ON s.idAdresse = a.idAdresse " +
                    "WHERE c.idClient = ? " +
                    "AND s.gestionnaire = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, clientId);
            ps.setString(2, String.valueOf(userId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Client client = new Client(
                        rs.getInt("idClient"),
                        new Adresse(
                                rs.getInt("idAdresse"),
                                rs.getString("codePostal"),
                                rs.getString("nomDeRue"),
                                rs.getString("numeroDeRue"),
                                rs.getString("ville")
                        ),
                        rs.getString("adresseMail"),
                        rs.getString("commentaire"),
                        rs.getString("raisonSociale"),
                        rs.getString("telephone"),
                        rs.getLong("chiffreAffaire"),
                        rs.getInt("nbrEmploye"),
                        userId
                );

                request.setAttribute("client", client);

                // Récupérer et stocker en session les
                // identifiants sensibles issus de la BDD
                int societeId = rs.getInt("idSociete");
                int adresseId = rs.getInt("idAdresse");
                session.setAttribute("societeId", societeId);
                session.setAttribute("adresseId", adresseId);

                // Génération et stockage du token CSRF
                String token = generateToken();
                session.setAttribute("csrfToken", token);
                request.setAttribute("token", token);

                return "client/updateClient.jsp";
            } else {
                request.setAttribute("error", "Erreur, Client non trouvé");
                LOGGER.info("Client non trouvé avec l'id " + clientId);
                return "client/listeClient.jsp";
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Erreur, veuillez réessayer plus tard");
            LOGGER.info("Erreur SQL: " + e.getMessage());
            return "client/listeClient.jsp";
        }
    }

    private static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
