package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Classe métier pour l'adresse
 */
@Entity
@Table(name = "adresse")
public class Adresse {

    /**
     * Identifiant de l'adresse
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer identifiantAdresse = null;

    /**
     * Numéro de rue de l'adresse
     */
    @NotNull
    @Pattern(regexp = "^\\d+[a-zA-Z]*$")
    private String numeroDeRue;

    /**
     * Nom de rue de l'adresse
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String nomDeRue;

    /**
     * Code postal de l'adresse
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{5}$")
    private String codePostal;

    /**
     * Ville de l'adresse
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String ville;

    /**
     * Constructeur sans argument requis par JPA.
     */
    public Adresse() {
    }

    /**
     * Constructeur pour initialiser une adresse avec un identifiant.
     *
     * @param identifiantAdresse l'identifiant de l'adresse.
     * @param codePostal         Code postal de l'adresse.
     * @param nomDeRue           Nom de la rue.
     * @param numeroDeRue        Numéro de la rue.
     * @param ville              Ville de l'adresse.
     */
    public Adresse(final Integer identifiantAdresse, final String codePostal, final String nomDeRue,
                   final String numeroDeRue, final String ville) {
        setIdentifiant(identifiantAdresse);
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setNumeroDeRue(numeroDeRue);
        setVille(ville);
    }

    /**
     * Constructeur pour initialiser une adresse sans identifiant.
     *
     * @param codePostal   Code postal de l'adresse.
     * @param nomDeRue     Nom de la rue.
     * @param numeroDeRue  Numéro de la rue.
     * @param ville        Ville de l'adresse.
     */
    public Adresse(final String codePostal, final String nomDeRue,
                   final String numeroDeRue, final String ville) {
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setNumeroDeRue(numeroDeRue);
        setVille(ville);
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(final String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNomDeRue() {
        return nomDeRue;
    }

    public void setNomDeRue(final String nomDeRue) {
        this.nomDeRue = nomDeRue;
    }

    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    public void setNumeroDeRue(final String numeroDeRue) {
        this.numeroDeRue = numeroDeRue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(final String ville) {
        this.ville = ville;
    }

    public Integer getIdentifiant() {
        return identifiantAdresse;
    }

    public void setIdentifiant(final Integer identifiant) {
        this.identifiantAdresse = identifiant;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "idAdresse=" + getIdentifiant() +
                ", numeroDeRue='" + numeroDeRue + '\'' +
                ", nomDeRue='" + nomDeRue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
