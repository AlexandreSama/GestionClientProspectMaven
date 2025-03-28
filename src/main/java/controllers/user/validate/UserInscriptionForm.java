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
import java.util.Set;
import java.util.logging.Logger;

public class UserInscriptionForm implements ICommand {

    private final EntityManager em;
    private static final Logger LOGGER = Logger.getLogger(UserInscriptionForm.class.getName());

    public UserInscriptionForm(final EntityManager em) {
        this.em = em;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

        // Création de l'objet User pour la validation
        User user = new User(password, username);

        // Construction du Validator et validation de l'objet User
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

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
            // Réaffiche le pseudo dans le champ correspondant
            request.setAttribute("username", username);
            return "user/register.jsp";
        }

        try {
            // Hachage du mot de passe avec Argon2
            Argon2 argon2 = Argon2Factory.create();
            String hashedPassword = argon2.hash(2, 65536, 1, password.toCharArray());
            argon2.wipeArray(password.toCharArray());
            // Mise à jour du mot de passe dans l'objet User
            user.setPwd(hashedPassword);

            // Sauvegarde de l'utilisateur via le DAO
            UserDAO userDao = new UserDAO(em);
            userDao.save(user);

            request.setAttribute("message", "Votre compte a bien été créé, vous pouvez vous connecter");
            LOGGER.info("Utilisateur créé avec succès.");
        } catch (Exception e) {
            request.setAttribute("error", "Erreur lors de la création de l'utilisateur, veuillez réessayer plus tard");
            LOGGER.info("Erreur lors de la création de l'utilisateur : " + e.getMessage());
            return "index.jsp";
        }
        return "index.jsp";
    }
}
