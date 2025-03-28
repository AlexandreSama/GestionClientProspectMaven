package models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe métier pour un user
 */
@Entity
@Table(name = "utilisateur")
public class User {

    /**
     * Identifiant de l'utilisateur
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer identifiantUser = null;

    /**
     * Pseudo de l'utilisateur
     */
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    /**
     * Mot de passe hashé de l'utilisateur
     */
    @NotNull
    @Size(min = 10, max = 255)
    private String pwd;

    /**
     * Liste de sociétés que l'utilisateur gère.
     */
    @OneToMany(mappedBy = "gestionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Societe> societes = new ArrayList<>();

    // Constructeur sans argument requis par JPA
    public User() {
    }

    /**
     * Constructeur de la classe User si l'utilisateur est déjà créé.
     *
     * @param identifiantUser L'identifiant de l'utilisateur.
     * @param pwd             Mot de passe de l'utilisateur.
     * @param username        Pseudo de l'utilisateur.
     */
    public User(final Integer identifiantUser,
                final String pwd, final String username) {
        setIdentifiantUser(identifiantUser);
        setPwd(pwd);
        setUsername(username);
    }

    /**
     * Constructeur de la classe User si l'utilisateur n'est pas encore créé.
     *
     * @param pwd      Mot de passe de l'utilisateur.
     * @param username Pseudo de l'utilisateur.
     */
    public User(final String pwd, final String username) {
        setPwd(pwd);
        setUsername(username);
    }

    public Integer getIdentifiantUser() {
        return identifiantUser;
    }

    public void setIdentifiantUser(final Integer identifiantUser) {
        this.identifiantUser = identifiantUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    public List<Societe> getSocietes() {
        return societes;
    }

    /**
     * Ajoute une société à la liste et met à jour la relation bidirectionnelle.
     *
     * @param societe la société à ajouter.
     */
    public void addSociete(final Societe societe) {
        societes.add(societe);
        societe.setGestionnaire(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifiantUser=" + identifiantUser +
                ", username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
