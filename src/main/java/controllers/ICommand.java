package controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**.
 * Interface contenant la méthode execute
 */
public interface ICommand {
    /**.
     * Méthode d'éxécution pour les controllers de page
     * @param request la requête reçu
     * @param response la réponse a renvoyer au cas ou
     * @return Le nom de la page demandé
     * @throws Exception Une exception en cas d'erreur
     */
    String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws Exception;
}
