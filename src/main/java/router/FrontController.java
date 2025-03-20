package router;

import controllers.ContactController;
import controllers.MentionsController;
import controllers.PageAccueilController;
import controllers.ICommand;
import controllers.client.CreateClientController;
import controllers.client.DeleteClientController;
import controllers.client.ListeClientController;
import controllers.client.UpdateClientController;
import controllers.prospect.CreateProspectController;
import controllers.prospect.DeleteProspectController;
import controllers.prospect.ListeProspectController;
import controllers.prospect.UpdateProspectController;
import controllers.user.LoginController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "front", value = "/front")
public class FrontController extends HttpServlet {

    /**.
     * Map des commandes menant aux controllers
     */
    private Map<String, Object> commands = new HashMap<>();
    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(FrontController.class.getName());

    /**.
     * La méthode d'initialisation
     * du controller
     */
    @Override
    public void init() {
        // Enregistrement des commandes pour les Clients
        commands.put("clients/view", new ListeClientController());
        commands.put("clients/add", new CreateClientController());
        commands.put("clients/update", new UpdateClientController());
        commands.put("clients/delete", new DeleteClientController());
        // Enregistrement des commandes pour les Prospects
        commands.put("prospects/view", new ListeProspectController());
        commands.put("prospects/add", new CreateProspectController());
        commands.put("prospects/update", new UpdateProspectController());
        commands.put("prospects/delete", new DeleteProspectController());
        // Enregistrement des commandes pour l'utilisateur
        commands.put("user/login", new LoginController());
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
        String urlSuite = "";
        try {
            // Récupération du paramètre "cmd" passé dans l'URL
            String cmd = request.getParameter("cmd");
            LOGGER.info("Valeur du paramètre cmd : " + cmd);
            ICommand com = (ICommand) commands.get(cmd);
            urlSuite = com.execute(request, response);
        } catch (Exception e) {
            LOGGER.severe("Erreur dans processRequest : " + e.getMessage());
            urlSuite = "erreur.jsp";
        } finally {
            try {
                request.getRequestDispatcher("WEB-INF/jsp/"
                        + urlSuite).forward(request, response);
            } catch (ServletException | IOException e) {
                LOGGER.severe("Erreur lors du forward : " + e.getMessage());
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
    }
}
