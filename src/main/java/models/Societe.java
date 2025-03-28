package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Classe métier abstract pour une société
 */
@Entity
@Table(name = "societe")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Societe {

    /**
     * Identifiant de la société
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer identifiantSociete = null;

    /**
     * Adresse de la société
     */
    @OneToOne(cascade = CascadeType.PERSIST) // ou CascadeType.ALL
    @JoinColumn(name = "adresse_id")
    @NotNull
    private Adresse adresse;

    /**
     * Email de la société
     */
    @NotNull
    @Email
    private String adresseMail;

    /**
     * Commentaire de la société
     */
    private String commentaire;

    /**
     * Téléphone de la société
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    @Size(min = 10, max = 10)
    private String telephone;

    /**
     * Raison Sociale de la société
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String raisonSociale;

    /**
     * Gérant de la société (l'utilisateur qui gère la société).
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User gestionnaire;

    /**
     * Constructeur pour initialiser une société avec les informations de base.
     *
     * @param adresse        Adresse de la société.
     * @param adresseMail    Adresse e-mail de la société.
     * @param commentaire    Commentaire sur la société.
     * @param raisonSociale  Raison sociale de la société.
     * @param telephone      Numéro de téléphone de la société.
     * @param gestionnaire   L'utilisateur gérant cette société.
     */
    public Societe(final Adresse adresse, final String adresseMail,
                   final String commentaire, final String raisonSociale,
                   final String telephone, final User gestionnaire) {
        setAdresse(adresse);
        setAdresseMail(adresseMail);
        setCommentaire(commentaire);
        setTelephone(telephone);
        setRaisonSociale(raisonSociale);
        setGestionnaire(gestionnaire);
    }

    // Constructeur sans argument requis par JPA
    public Societe() {
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(final Adresse adresse) {
        this.adresse = adresse;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(final String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(final String commentaire) {
        this.commentaire = commentaire != null ? commentaire.trim() : null;
    }

    public Integer getIdentifiant() {
        return identifiantSociete;
    }

    public void setIdentifiant(final Integer identifiant) {
        this.identifiantSociete = identifiant;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(final String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone.trim();
    }

    public User getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(final User gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "identifiantSociete=" + identifiantSociete +
                ", adresseMail='" + adresseMail + '\'' +
                ", commentaire='" + commentaire + '\'' +
                ", telephone='" + telephone + '\'' +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", gestionnaire=" + (gestionnaire != null ? gestionnaire.getIdentifiantUser() : null) +
                '}';
    }
}
