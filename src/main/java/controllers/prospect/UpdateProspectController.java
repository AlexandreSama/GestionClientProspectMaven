package controllers.prospect;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**.
 * Controller servant pour la page de mise a jour de prospect
 * {@inheritDoc}
 */
public class UpdateProspectController implements ICommand {

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
        return "prospect/updateProspect.jsp";
    }
}
