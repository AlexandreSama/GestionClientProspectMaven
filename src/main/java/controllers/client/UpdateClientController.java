package controllers.client;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Adresse;
import models.Client;
import models.User;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

/**.
 * Controller servant pour la page de mise a jour de client
 * {@inheritDoc}
 */
public class UpdateClientController implements ICommand {

    /**.
     * La variable pour la connexion BDD
     */
    private final Connection connection;

    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(UpdateClientController.class.getName());

    /**.
     * Le constructeur du controller
     * @param connection la variable pour la connexion BDD
     */
    public UpdateClientController(final Connection connection) {
        this.connection = connection;
    }

    /**.
     * Méthode d'éxécution du controller
     * @param request - La requête reçu
     * @param response - La réponse a renvoyer si besoin
     * @return Le nom de la page demandé
     * @throws Exception - Une exception au cas ou
     */
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws Exception {

            String clientId = request.getParameter("id");
            HttpSession session = request.getSession();
            // Récupérer l'utilisateur stocké en session (ici sous forme de List)
            List<?> userList = (List<?>) session.getAttribute("user");
            // On suppose que l'ID de l'utilisateur est stocké à l'indice 0
            Integer userId = (Integer) userList.getFirst();
            LOGGER.info("userId dans UpdateClientController : " + userId);

            try{
                String sql = "SELECT c.*, s.*, a.*"
                        +
                        "FROM client c "
                        +
                        "JOIN societe s ON c.idSociete = s.idSociete "
                        +
                        "JOIN adresse a ON s.idAdresse = a.idAdresse "
                        +
                        "WHERE c.idClient = ? "
                        +
                        "AND s.gestionnaire = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, clientId);
                ps.setString(2,
                        String.valueOf(userId));
                ResultSet rs = ps.executeQuery();


                if(rs.next()){
                    LOGGER.info("idAdresse dans UpdateClientController" + rs.getString("idAdresse"));
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
                    request.setAttribute("societeId", rs.getInt("idSociete"));
                    request.setAttribute("adresseId", rs.getInt("idAdresse"));
                    String token = generateToken();
                    session.setAttribute("csrfToken", token);
                    request.setAttribute("token", token);

                    return "client/updateClient.jsp";
                } else {
                    request.setAttribute("error",
                            "Erreur, Client non trouvé");
                    LOGGER.info("Client non trouvé avec l'id " + clientId);
                    return "client/listeClient.jsp";
                }


            } catch (SQLException e){
                request.setAttribute("error",
                        "Erreur, veuillez réessayer plus tard");
                LOGGER.info("Erreur SQL: " + e.getMessage());
                return "client/listeClient.jsp";
            }
    }

    private static String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32]; // 256 bits de sécurité
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
