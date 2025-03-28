package controllers.user.validate;

import controllers.ICommand;
import controllers.user.dao.UserDAO;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import models.User;
import java.util.Arrays;
import java.util.Set;
import java.util.logging.Logger;

public class UserLoginForm implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(UserLoginForm.class.getName());

    public UserLoginForm(final EntityManager em) {
        this.em = em;
    }

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // Récupération des paramètres depuis le formulaire
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Création d'un objet User pour la validation (le constructeur attend (pwd, username))
        User userInput = new User(password, username);

        // Validation de l'objet User via Bean Validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(userInput);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<User> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String displayName = ("pwd".equals(propertyPath)) ? "Mot de passe"
                        : ("username".equals(propertyPath)) ? "Pseudonyme" : propertyPath;
                errorMessage.append(displayName)
                        .append(" : ")
                        .append(violation.getMessage())
                        .append("<br>");
            }
            request.setAttribute("error", errorMessage.toString());
            // Réafficher le pseudo dans l'input
            request.setAttribute("username", username);
            return "user/login.jsp";
        }

        try {
            // Utilisation du DAO pour rechercher l'utilisateur par son pseudo
            UserDAO userDao = new UserDAO(em);
            User userFromDb = userDao.findByUsername(username);

            if (userFromDb != null) {
                String storedHash = userFromDb.getPwd();
                Argon2 argon2 = Argon2Factory.create();
                // Vérification du mot de passe fourni par rapport au hash stocké
                if (argon2.verify(storedHash, password.toCharArray())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", Arrays.asList(userFromDb.getIdentifiantUser(), userFromDb.getUsername()));
                    session.setAttribute("hasRights", Arrays.asList("Supprimer", "Modifier", "Créer"));
                    session.setAttribute("success", "Connexion réussie pendant 30min");
                    argon2.wipeArray(password.toCharArray());
                    return "index.jsp";
                } else {
                    request.setAttribute("error", "Erreur, veuillez vérifier votre mot de passe");
                    LOGGER.info("Mot de passe incorrect pour l'utilisateur " + username);
                    return "user/login.jsp";
                }
            } else {
                request.setAttribute("error", "Erreur, veuillez vérifier votre pseudonyme");
                LOGGER.info("Aucun utilisateur trouvé pour " + username);
                return "user/login.jsp";
            }
        } catch (Exception e) {
            request.setAttribute("error", "Erreur : Veuillez réessayer plus tard");
            LOGGER.info("Erreur lors de la connexion: " + e.getMessage());
            return "user/login.jsp";
        }
    }
}
