package router;

import controllers.misc.ContactController;
import controllers.misc.MentionsController;
import controllers.PageAccueilController;
import controllers.ICommand;
import controllers.client.show.AddClientController;
import controllers.client.show.DeleteClientController;
import controllers.client.show.ListeClientController;
import controllers.client.show.UpdateClientController;
import controllers.client.validate.ValidateAddClientController;
import controllers.client.validate.ValidateDeleteClientController;
import controllers.client.validate.ValidateUpdateClientController;
import controllers.user.show.LoginController;
import controllers.user.show.RegisterController;
import controllers.user.validate.UserInscriptionForm;
import controllers.user.validate.UserLoginForm;
import controllers.user.validate.UserLogout;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "front", value = "/front")
public class FrontController extends HttpServlet {

    /** Map des commandes menant aux controllers */
    private final Map<String, Object> commands = new HashMap<>();
    /** Le classique LOGGER */
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());

    private EntityManagerFactory emf;

    /** Méthode d'initialisation du controller */
    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("ecfsql");
        EntityManager em = emf.createEntityManager();

        // Enregistrement des commandes pour les Clients
        commands.put("clients/view", new ListeClientController(em));
        commands.put("clients/add", new AddClientController());
        commands.put("clients/update", new UpdateClientController(em));
        commands.put("clients/delete", new DeleteClientController(em));
        // Enregistrements des commandes de validation Client
        commands.put("clients/update/validate", new ValidateUpdateClientController(em));
        commands.put("clients/add/validate", new ValidateAddClientController(em));
        commands.put("clients/delete/validate", new ValidateDeleteClientController(em));
        // Enregistrement des commandes pour les Prospects
//        commands.put("prospects/view", new ListeProspectController(connection));
//        commands.put("prospects/add", new AddProspectController());
//        commands.put("prospects/update", new UpdateProspectController());
//        commands.put("prospects/delete", new DeleteProspectController());
        // Enregistrements des commandes de validation Prospect
//        commands.put("prospects/update/validate", new ValidateUpdateProspectController());
//        commands.put("prospects/add/validate", new ValidateAddProspectController(connection));
//        commands.put("prospects/delete/validate", new ValidateDeleteProspectController());
        // Enregistrement des commandes pour l'utilisateur
        commands.put("user/login", new LoginController());
        commands.put("user/valider-login", new UserLoginForm(em));
        commands.put("user/logout", new UserLogout());
        commands.put("user/inscription", new RegisterController());
        commands.put("user/valider-inscription", new UserInscriptionForm(em));

        // Enregistrement des commandes à part
        commands.put("contact", new ContactController());
        commands.put("mentions", new MentionsController());
        // Enregistrement par défaut (pour les requêtes sans paramètre "cmd")
        commands.put(null, new PageAccueilController());
        commands.put("index", new PageAccueilController());
    }

    /** Méthode pour gérer la requête */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) {
        String navigation = "";
        try {
            // Récupération du paramètre "cmd" passé dans l'URL
            String cmd = request.getParameter("cmd");
            ICommand com = (ICommand) commands.get(cmd);
            navigation = com.execute(request, response); // On attend une String
        } catch (Exception e) {
            // Utilisation de LOGGER.log pour inclure la stack trace
            LOGGER.log(Level.SEVERE, "Erreur dans processRequest", e);
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
                LOGGER.log(Level.SEVERE, "Erreur lors du forward ou de la redirection", e);
            }
        }
    }

    /** Méthode en cas de requête de type GET */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        processRequest(request, response);
    }

    /** Méthode en cas de requête de type POST */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) {
        processRequest(request, response);
    }

    /** Méthode destroy pour le controller */
    @Override
    public void destroy() {
        emf.close();
    }
}
