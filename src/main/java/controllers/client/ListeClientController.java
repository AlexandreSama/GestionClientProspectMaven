package controllers.client;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        try {
            HttpSession session = request.getSession();
            String userId = session.getAttribute("user_id").toString();
            // Obliger d'utiliser un LEFT JOIN sur contrat pour ne pas exclure les clients sans contrat
            String sql = "SELECT c.*, s.*, a.*, co.idContrat, co.libelle, co.montant " +
                    "FROM client c " +
                    "JOIN societe s ON c.idSociete = s.idSociete " +
                    "JOIN adresse a ON s.idAdresse = a.idAdresse " +
                    "LEFT JOIN contrat co ON c.idClient = co.idClient " +
                    "WHERE s.gestionnaire = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            // On utilise une Map pour regrouper les données clients par idClient
            Map<Integer, Map<String, Object>> clientMap = new HashMap<>();
            while(rs.next()){
                int idClient = rs.getInt("idClient");
                Map<String, Object> clientData = clientMap.get(idClient);
                if (clientData == null) {
                    clientData = new HashMap<>();
                    // Données du client
                    clientData.put("idClient", idClient);
                    clientData.put("chiffreAffaire", rs.getString("chiffreAffaire"));
                    clientData.put("nbrEmploye", rs.getString("nbrEmploye"));
                    // Données de la société
                    clientData.put("idSociete", rs.getInt("idSociete"));
                    clientData.put("raisonSociale", rs.getString("raisonSociale"));
                    clientData.put("telephone", rs.getString("telephone"));
                    clientData.put("adresseMail", rs.getString("adresseMail"));
                    clientData.put("commentaire", rs.getString("commentaire"));
                    // Données de l'adresse
                    clientData.put("idAdresse", rs.getInt("idAdresse"));
                    clientData.put("numeroDeRue", rs.getString("numeroDeRue"));
                    clientData.put("codePostal", rs.getString("codePostal"));
                    clientData.put("ville", rs.getString("ville"));
                    clientData.put("nomDeRue", rs.getString("nomDeRue"));
                    // Initialisation d'une liste pour les contrats
                    clientData.put("contracts", new ArrayList<Map<String, Object>>());
                    clientMap.put(idClient, clientData);
                }
                // Récupération des données de contrat (si présentes)
                int idContrat = rs.getInt("idContrat");
                // Si la colonne idContrat n'est pas nulle, on considère qu'il y a un contrat
                if (!rs.wasNull()) {
                    Map<String, Object> contractData = new HashMap<>();
                    contractData.put("idContrat", idContrat);
                    contractData.put("libelle", rs.getString("libelle"));
                    contractData.put("montant", rs.getString("montant"));
                    // Ajout du contrat à la liste des contrats du client
                    List<Map<String, Object>> contracts = (List<Map<String, Object>>) clientData.get("contracts");
                    contracts.add(contractData);
                }
            }
            List<Map<String, Object>> clients = new ArrayList<>(clientMap.values());
            if(clients.isEmpty()){
                request.setAttribute("error", "Erreur, vous n'avez aucun client à votre actif");
                LOGGER.info("Aucun client trouvé");
                return "client/listeClient.jsp";
            }

            // Envoi de la liste des clients (avec leurs contrats imbriqués) à la vue
            request.setAttribute("clients", clients);
        } catch (SQLException e){
            request.setAttribute("error", "Erreur SQL: Veuillez réessayer plus tard");
            LOGGER.info("Erreur SQL: " + e.getMessage());
            return "index.jsp";
        }
        return "client/listeClient.jsp";
    }
}
