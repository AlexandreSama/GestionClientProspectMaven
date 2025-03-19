package router;

import controllers.ContactController;
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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet(name = "Accueil", value = "/")
public class FrontController extends HttpServlet {
    // Utilisation des generics pour préciser la clé (String) et la valeur (ICommand)
    private Map<String, Object> commands = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());

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
        // Enregistrement des commandes a part
        commands.put("contact", new ContactController());
        // Enregistrement par défaut (pour les requêtes sans paramètre "cmd")
        commands.put(null, new PageAccueilController());
        commands.put("index", new PageAccueilController());

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
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
                request.getRequestDispatcher("WEB-INF/JSP/" + urlSuite).forward(request, response);
//                request.getRequestDispatcher(urlSuite).forward(request, response);
            } catch (ServletException | IOException e) {
                LOGGER.severe("Erreur lors du forward : " + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }
}
