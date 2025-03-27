package controllers.prospect;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**.
 * Controller servant pour la page de liste de prospect
 * {@inheritDoc}
 */
public class ListeProspectController implements ICommand {

    /**.
     * La variable pour la connexion BDD
     */
    private final Connection connection;

    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(ListeProspectController.class.getName());

    /**.
     * Le constructeur du controller
     * @param connection la variable pour la connexion BDD
     */
    public ListeProspectController(final Connection connection) {
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
        try {
            HttpSession session = request.getSession();
            // Récupérer l'utilisateur stocké en session (ici sous forme de List)
            List<?> userList = (List<?>) session.getAttribute("user");
            // On suppose que l'ID de l'utilisateur est stocké à l'indice 0
            Integer userId = (Integer) userList.getFirst();

            String sql = "SELECT p.*, s.*, a.* "
                    +
                    "FROM prospect p "
                    +
                    "JOIN societe s ON p.idSociete = s.idSociete "
                    +
                    "JOIN adresse a ON s.idAdresse = a.idAdresse "
                    +
                    "WHERE s.gestionnaire = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, String.valueOf(userId));
            ResultSet rs = ps.executeQuery();

            // Utilisation d'une Map pour regrouper
            // les données prospects par idProspect
            Map<Integer, Map<String, Object>> prospectMap = new HashMap<>();
            while (rs.next()) {
                int idProspect = rs.getInt("idProspect");
                Map<String, Object> prospectData = prospectMap.get(idProspect);
                if (prospectData == null) {

                    String dateStr = rs.getString("dateProspection");
                    LocalDate date = LocalDate.parse(dateStr); // Parsing depuis "yyyy-MM-dd"
                    DateTimeFormatter frenchFormatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");

                    prospectData = new HashMap<>();
                    // Données du prospect
                    prospectData.put("idProspect", idProspect);
                    prospectData.put("estInteresse", "1".equals(rs.getString("estInteresse")) ? "OUI" : "NON");
                    prospectData.put("dateProspection", date.format(frenchFormatter));
                    // Données de la société
                    prospectData.put("idSociete",
                            rs.getInt("idSociete"));
                    prospectData.put("raisonSociale",
                            rs.getString("raisonSociale"));
                    prospectData.put("telephone",
                            rs.getString("telephone"));
                    prospectData.put("adresseMail",
                            rs.getString("adresseMail"));
                    prospectData.put("commentaire",
                            rs.getString("commentaire"));
                    // Données de l'adresse
                    prospectData.put("idAdresse",
                            rs.getInt("idAdresse"));
                    prospectData.put("numeroDeRue",
                            rs.getString("numeroDeRue"));
                    prospectData.put("codePostal",
                            rs.getString("codePostal"));
                    prospectData.put("ville",
                            rs.getString("ville"));
                    prospectData.put("nomDeRue",
                            rs.getString("nomDeRue"));
                    prospectMap.put(idProspect, prospectData);

                }
            }

            List<Map<String, Object>> prospects =
                    new ArrayList<>(prospectMap.values());

            if (prospects.isEmpty()) {

                request.setAttribute("error", "Erreur,"
                        + "vous n'avez aucun prospect à votre actif");
                LOGGER.info("Aucun prospect trouvé");
                return "prospect/listeProspect.jsp";
            }

            // Envoi de la liste des prospects a la vue
            request.setAttribute("prospects", prospects);
        } catch (SQLException e){
            request.setAttribute("error",
                    "Erreur SQL: Veuillez réessayer plus tard");
            LOGGER.info("Erreur SQL: " + e.getMessage());
            return "index.jsp";
        }
        return "prospect/listeProspect.jsp";
    }
}
