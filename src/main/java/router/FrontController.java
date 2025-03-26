package router;

import controllers.ContactController;
import controllers.MentionsController;
import controllers.PageAccueilController;
import controllers.ICommand;
import controllers.client.AddClientController;
import controllers.client.DeleteClientController;
import controllers.client.ListeClientController;
import controllers.client.UpdateClientController;
import controllers.client.validate.ValidateAddClientController;
import controllers.client.validate.ValidateUpdateClientController;
import controllers.prospect.CreateProspectController;
import controllers.prospect.DeleteProspectController;
import controllers.prospect.ListeProspectController;
import controllers.prospect.UpdateProspectController;
import controllers.user.LoginController;
import controllers.user.UserForm;
import controllers.user.UserLogout;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "front", value = "/front")
public class FrontController extends HttpServlet {

    /**.
     * Map des commandes menant aux controllers
     */
    private final Map<String, Object> commands = new HashMap<>();
    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(FrontController.class.getName());

    /**.
     * La datasource pour la connexion BDD
     */
    @Resource(name = "jdbc/ecf")
    private static DataSource datasource;

    /**.
     * La variable pour la connexion BDD
     */
    private static Connection connection;

    /**.
     * La méthode d'initialisation
     * du controller
     */
    @Override
    public void init() throws ServletException {

        try {
            connection = datasource.getConnection();
            LOGGER.info("Récupération réussi de la connexion");
        } catch (SQLException e) {
            LOGGER.info("Erreur avec la récupération "
                    + "de connexion " + e.getMessage());
            throw new ServletException("Erreur lors de la "
                    + "récupération de la connexion", e);
        }
        // Enregistrement des commandes pour les Clients
        commands.put("clients/view", new ListeClientController(connection));
        commands.put("clients/add", new AddClientController(connection));
        commands.put("clients/update", new UpdateClientController(connection));
        commands.put("clients/delete", new DeleteClientController());
        // Enregistrements des commandes de validation Client
        commands.put("clients/update/validate", new ValidateUpdateClientController(connection));
        commands.put("clients/add/validate", new ValidateAddClientController(connection));
        // Enregistrement des commandes pour les Prospects
        commands.put("prospects/view", new ListeProspectController());
        commands.put("prospects/add", new CreateProspectController());
        commands.put("prospects/update", new UpdateProspectController());
        commands.put("prospects/delete", new DeleteProspectController());
        // Enregistrement des commandes pour l'utilisateur
        commands.put("user/login", new LoginController());
        commands.put("user/logout", new UserLogout());
//        commands.put("create-user", new CreateUserController(connection));
        commands.put("valider-login", new UserForm(connection));
        // Enregistrement des commandes a part
        commands.put("contact", new ContactController());
        commands.put("mentions", new MentionsController());
        // Enregistrement par défaut (pour les requêtes sans paramètre "cmd")
        commands.put(null, new PageAccueilController());
        commands.put("index", new PageAccueilController());

    }

    /**.
     * Méthode pour gérer la requête
     * @param request la requête reçu
     * @param response la réponse a envoyer
     */
    protected void processRequest(final HttpServletRequest request,
                                  final HttpServletResponse response) {
        String navigation = "";
        try {
            // Récupération du paramètre "cmd" passé dans l'URL
            String cmd = request.getParameter("cmd");
            ICommand com = (ICommand) commands.get(cmd);
            navigation = com.execute(request, response); // On attend une String
        } catch (Exception e) {
            LOGGER.severe("Erreur dans processRequest : " + e.getMessage()
                    + " dans : " + e.getClass().getName());
            navigation = "erreur.jsp";
        } finally {
            try {
                if (navigation != null && navigation.startsWith("redirect:")) {
                    // Extraire l'URL cible après le préfixe "redirect:"
                    String redirectUrl = navigation.substring("redirect:".length());
                    response.sendRedirect(request.getContextPath() + redirectUrl);
                } else {
                    request.getRequestDispatcher("WEB-INF/jsp/" + navigation).forward(request, response);
                }
            } catch (ServletException | IOException e) {
                LOGGER.severe("Erreur lors du forward ou de la redirection : " + e.getMessage());
            }
        }
    }


    /**.
     * Méthode en cas de requête de type GET
     */
    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response) {
        processRequest(request, response);
    }

    /**.
     * Méthode en cas de requête de type POST
     */
    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response) {
        processRequest(request, response);
    }

    /**.
     * Méthode destroy pour le controller
     */
    public void destroy() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.severe("Erreur dans destruction "
                    + "de la connexion SQL : " + e.getMessage());
        }
    }
}
