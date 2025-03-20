package controllers.user;

import controllers.ICommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class CreateUserController implements ICommand {

    private final Connection connection;

    private static final Logger LOGGER =
            Logger.getLogger(CreateUserController.class.getName());

    public CreateUserController(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // Valeurs en dur pour la création de l'utilisateur
        String username = "alex";
        String password = "alexandre123Sa";

        // Hachage du mot de passe avec Argon2
        String hashedPassword = hashPassword(password);

        // Insertion de l'utilisateur dans la base de données
        try {
            String sql = "INSERT INTO user (username, pwd) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            int result = ps.executeUpdate();

            if (result > 0) {
                request.setAttribute("message", "Utilisateur créé avec succès en dur.");
                LOGGER.info("Utilisateur créé avec succès en dur.");
            } else {
                request.setAttribute("error", "Erreur lors de la création de l'utilisateur en dur.");
                LOGGER.info("Erreur lors de la création de l'utilisateur en dur.");
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Erreur SQL: " + e.getMessage());
            LOGGER.info("Erreur SQL: " + e.getMessage());
        }
        return username;
    }

    /**
     * Méthode pour hacher le mot de passe en utilisant Argon2.
     * Assurez-vous d'ajouter la dépendance argon2-jvm dans votre projet.
     */
    private String hashPassword(String password) {
        // Création d'une instance d'Argon2
        Argon2 argon2 = Argon2Factory.create();

        // Paramètres : nombre d'itérations, mémoire (en kilo-octets), parallélisme
        int iterations = 2;
        int memory = 65536;  // par exemple, 64 Mo
        int parallelism = 1;

        // Génération du hash avec Argon2. Le hash retourné contient également le sel.
        return argon2.hash(iterations, memory, parallelism, password.toCharArray());
    }
}
