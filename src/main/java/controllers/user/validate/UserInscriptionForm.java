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
import java.util.Set;
import java.util.logging.Logger;

public class UserInscriptionForm implements ICommand {

    /**.
     * La variable pour la connexion BDD
     */
    private final Connection connection;

    /**.
     * Le classique LOGGER
     */
    private static final Logger LOGGER =
            Logger.getLogger(UserInscriptionForm.class.getName());

    /**.
     * Le constructeur pour le controller
     * @param connection La variable de connexion BDD
     */
    public UserInscriptionForm(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response)
            throws Exception {

        HttpSession session = request.getSession();
        String tokenFromSession = (String) session.getAttribute("csrfToken");
        String tokenFromForm = request.getParameter("csrfToken");

        // Vérification du token CSRF
        if (tokenFromSession == null || !tokenFromSession.equals(tokenFromForm)) {
            request.setAttribute("error", "Requête invalide (token CSRF non valide).");
            return "erreur.jsp";
        }

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
            return "user/register.jsp";
        }

        try{
            Argon2 argon2 = Argon2Factory.create();
            String hashedPassword = argon2.hash(2, 65536, 1, password.toCharArray());

            String sql = "INSERT INTO user (username, pwd) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            int result = ps.executeUpdate();

            if (result > 0) {
                request.setAttribute("message", "Votre compté a bien été créer, vous pouvez vous connecter");
                LOGGER.info("Utilisateur créé avec succès.");
            } else {
                request.setAttribute("error", "Erreur lors de la création de l'utilisateur, veuillez réessayer plus tard");
                LOGGER.info("Erreur lors de la création de l'utilisateur en dur.");
                return "index.jsp";
            }
        } catch (Exception e){
            request.setAttribute("error", "Erreur SQL, veuillez réessayer plus tard");
            LOGGER.info("Erreur SQL: " + e.getMessage());
            return "index.jsp";
        }
        return "index.jsp";
    }
}
