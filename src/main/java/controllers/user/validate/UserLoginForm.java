package controllers.user.validate;

import controllers.ICommand;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;

public class UserLoginForm implements ICommand {

    /**.
     * La variable pour la connexion BDD
     */
    private final Connection connection;

    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(UserLoginForm.class.getName());

    /**.
     * Le constructeur pour le controller
     * @param connection La variable de connexion BDD
     */
    public UserLoginForm(final Connection connection) {
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
                          final HttpServletResponse response) throws Exception {

        // Récupération des paramètres depuis le formulaire
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Ici, on suppose que le constructeur de User attend (pwd, username)
        User user = new User(password, username);

        // Construction du Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Validation de l'objet User
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String displayName;
                if ("pwd".equals(propertyPath)) {
                    displayName = "Mot de passe";
                } else if ("username".equals(propertyPath)) {
                    displayName = "Pseudonyme";
                } else {
                    displayName = propertyPath;
                }
                errorMessage.append(displayName)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("error", errorMessage.toString());
            // Remet le pseudo pour le réafficher dans l'input
            request.setAttribute("username", username);
            return "user/login.jsp";
        }

        try {
            // Récupération du hash stocké en base pour ce pseudonyme
            String sql = "SELECT id, username,"
                    + "pwd FROM user WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("pwd");
                Integer userId = rs.getInt("id");

                // Vérification du mot de passe fourni avec le hash stocké
                Argon2 argon2 = Argon2Factory.create();
                if (argon2.verify(storedHash, password.toCharArray())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", Arrays.asList(userId, username));
                    session.setAttribute("hasRights",
                            Arrays.asList("Supprimer", "Modifier", "Créer"));
                    session.setAttribute("success",
                            "Connexion réussie pendant 30min");
                    argon2.wipeArray(password.toCharArray());
                    return "index.jsp";
                } else {
                    request.setAttribute("error",
                            "Erreur, veuillez vérifier votre mot de passe");
                    LOGGER.info("Mot de passe incorrect "
                            + "pour l'utilisateur " + username);
                    return "user/login.jsp";
                }
            } else {
                request.setAttribute("error",
                        "Erreur, veuillez vérifier votre pseudonyme");
                LOGGER.info("Aucun utilisateur trouvé pour " + username);
                return "user/login.jsp";
            }
        } catch (SQLException e) {
            request.setAttribute("error",
                    "Erreur SQL: Veuillez réessayer plus tard");
            LOGGER.info("Erreur SQL: " + e.getMessage());
            return "user/login.jsp";
        }
    }
}
