package controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**.
 * Controller servant pour la page Accueil
 * {@inheritDoc}
 */
public class PageAccueilController implements ICommand {

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
        return "index.jsp";
    }
}
