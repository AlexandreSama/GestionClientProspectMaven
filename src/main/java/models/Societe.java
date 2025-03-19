package models;

public abstract class Societe {
    private Integer identifiant = null;
    private Adresse adresse;
    private String adresseMail;
    private String commentaire;
    private String telephone;
    private String raisonSociale;

    /**
     * Constructeur pour initialiser une société avec les informations de base.
     * Les setters sont utilisés pour appliquer la validation sur chaque attribut.
     *
     * @param adresse        Adresse de la société.
     * @param adresseMail    Adresse e-mail de la société.
     * @param commentaire    Commentaire sur la société.
     * @param raisonSociale  Raison sociale de la société.
     * @param telephone      Numéro de téléphone de la société.
     */
    public Societe(Adresse adresse, String adresseMail, String commentaire, String raisonSociale, String telephone) {
        // Les setters effectuent la validation des données.
        setAdresse(adresse);
        setAdresseMail(adresseMail);
        setCommentaire(commentaire);
        setTelephone(telephone);
        setRaisonSociale(raisonSociale);
    }

    /**
     * Retourne l'adresse de la société.
     *
     * @return Adresse de la société.
     */
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * Définit l'adresse de la société.
     *
     * @param adresse Adresse à définir.
     */
    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    /**
     * Retourne l'adresse e-mail de la société.
     *
     * @return Adresse e-mail de la société.
     */
    public String getAdresseMail() {
        return adresseMail;
    }

    /**
     * Définit l'adresse e-mail de la société.
     *
     * @param adresseMail Adresse e-mail à définir.
     */
    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    /**
     * Retourne le commentaire sur la société.
     *
     * @return Commentaire sur la société.
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Définit un commentaire pour la société.
     *
     * @param commentaire Commentaire à définir.
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire != null ? commentaire.trim() : null;
    }

    /**
     * Retourne l'identifiant unique de la société.
     *
     * @return Identifiant de la société.
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**
     * Définit un identifiant pour la société
     * @param identifiant l'identifiant a donner pour cet société
     */
    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    /**
     * Retourne la raison sociale de la société.
     *
     * @return Raison sociale de la société.
     */
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**
     * Définit la raison sociale de la société.
     *
     * @param raisonSociale Raison sociale à définir.
     */
    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    /**
     * Retourne le numéro de téléphone de la société.
     *
     * @return Numéro de téléphone de la société.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone de la société.
     *
     * @param telephone Numéro de téléphone à définir.
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone.trim();
    }
}
