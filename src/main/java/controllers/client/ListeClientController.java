package controllers.client;

import controllers.ICommand;
import controllers.user.UserForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**.
 * Controller servant pour la page de liste de client
 * {@inheritDoc}
 */
public class ListeClientController implements ICommand {

    private final Connection connection;

    private static final Logger LOGGER =
            Logger.getLogger(ListeClientController.class.getName());

    public ListeClientController(final Connection connection) {
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
            try{
                String sql = "SELECT * FROM client";
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){

                }else{
                    request.setAttribute("error",
                            "Erreur, vous n'avez aucun client a votre actif");
                    LOGGER.info("Aucun client trouvé");
                    return "index.jsp";
                }
            } catch (SQLException e){
                request.setAttribute("error", "Erreur SQL: Veuillez réessayer plus tard");
                LOGGER.info("Erreur SQL: " + e.getMessage());
                return "index.jsp";
            }
//        return "client/listeClient.jsp";
    }
}
