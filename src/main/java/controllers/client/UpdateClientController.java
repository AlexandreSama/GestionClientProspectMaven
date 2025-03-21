package controllers.client;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Adresse;
import models.Client;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            User user = (User) session.getAttribute("user_id");

            try{
                String sql = "SELECT c.*, s.*, a.*"
                        +
                        "FROM client c "
                        +
                        "JOIN societe s ON c.idSociete = s.idSociete "
                        +
                        "JOIN adresse a ON s.idAdresse = a.idAdresse "
                        +
                        "WHERE c.idClient = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, clientId);
                ResultSet rs = ps.executeQuery();

                if(rs.next()){
                    Client client = new Client(
                            rs.getInt("idClient"),
                            new Adresse(
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
                            user
                    );

                    request.setAttribute("client", client);
                }
            } catch (SQLException e){

            }
        return "client/updateClient.jsp";
    }
}
