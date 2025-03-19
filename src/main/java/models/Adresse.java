package models;

public class Adresse {
    private Integer identifiant;
    private String numeroDeRue;
    private String nomDeRue;
    private String codePostal;
    private String ville;

    /**
     * Constructeur pour initialiser une adresse avec les informations fournies.
     *
     * @param codePostal   Code postal de l'adresse.
     * @param nomDeRue     Nom de la rue.
     * @param numeroDeRue  Numéro de la rue.
     * @param ville        Ville de l'adresse.
     */
    public Adresse(String codePostal, String nomDeRue, String numeroDeRue, String ville) {
        setCodePostal(codePostal);
        setNomDeRue(nomDeRue);
        setNumeroDeRue(numeroDeRue);
        setVille(ville);
    }

    /**
     * Retourne le code postal de l'adresse.
     *
     * @return Code postal.
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Définit le code postal après validation.
     *
     * @param codePostal Code postal à définir.
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Retourne le nom de la rue de l'adresse.
     *
     * @return Nom de la rue.
     */
    public String getNomDeRue() {
        return nomDeRue;
    }

    /**
     * Définit le nom de la rue après validation.
     *
     * @param nomDeRue Nom de la rue à définir.
     */
    public void setNomDeRue(String nomDeRue) {
        this.nomDeRue = nomDeRue;
    }

    /**
     * Retourne le numéro de la rue de l'adresse.
     *
     * @return Numéro de la rue.
     */
    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    /**
     * Définit le numéro de la rue après validation.
     *
     * @param numeroDeRue Numéro de la rue à définir.
     */
    public void setNumeroDeRue(String numeroDeRue) {
        this.numeroDeRue = numeroDeRue;
    }

    /**
     * Retourne la ville de l'adresse.
     *
     * @return Ville.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Définit la ville après validation.
     *
     * @param ville Ville à définir.
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Retourne l'identifiant de l'adresse.
     *
     * @return Identifiant.
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**
     * Définit l'identifiant de l'adresse
     * @param identifiant l'identifiant récupéré depuis la BDD
     */
    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "numeroDeRue='" + numeroDeRue + '\'' +
                ", nomDeRue='" + nomDeRue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }
}
