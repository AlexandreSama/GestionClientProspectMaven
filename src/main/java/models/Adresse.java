package models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**.
 * Classe métier pour l'adresse
 */
public class Adresse {
    /**.
     * Identifiant de l'adresse
     */
    private Integer identifiant;
    /**.
     * Numéro de rue de l'adresse
     */
    @NotNull
    @Pattern(regexp = "^\\d+[a-zA-Z]*$")
    private String numeroDeRue;
    /**.
     * Nom de rue de l'adresse
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String nomDeRue;
    /**.
     * Code postal de l'adresse
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{5}$")
    private String codePostal;
    /**.
     * Ville de l'adresse
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String ville;

    /**.
     * Constructeur pour initialiser une adresse avec les informations fournies.
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

    /**.
     * Retourne le code postal de l'adresse.
     *
     * @return Code postal.
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**.
     * Définit le code postal après validation.
     *
     * @param codePostal Code postal à définir.
     */
    public void setCodePostal(final String codePostal) {
        this.codePostal = codePostal;
    }

    /**.
     * Retourne le nom de la rue de l'adresse.
     *
     * @return Nom de la rue.
     */
    public String getNomDeRue() {
        return nomDeRue;
    }

    /**.
     * Définit le nom de la rue après validation.
     *
     * @param nomDeRue Nom de la rue à définir.
     */
    public void setNomDeRue(final String nomDeRue) {
        this.nomDeRue = nomDeRue;
    }

    /**.
     * Retourne le numéro de la rue de l'adresse.
     *
     * @return Numéro de la rue.
     */
    public String getNumeroDeRue() {
        return numeroDeRue;
    }

    /**.
     * Définit le numéro de la rue après validation.
     *
     * @param numeroDeRue Numéro de la rue à définir.
     */
    public void setNumeroDeRue(final String numeroDeRue) {
        this.numeroDeRue = numeroDeRue;
    }

    /**.
     * Retourne la ville de l'adresse.
     *
     * @return Ville.
     */
    public String getVille() {
        return ville;
    }

    /**.
     * Définit la ville après validation.
     *
     * @param ville Ville à définir.
     */
    public void setVille(final String ville) {
        this.ville = ville;
    }

    /**.
     * Retourne l'identifiant de l'adresse.
     *
     * @return Identifiant.
     */
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**.
     * Définit l'identifiant de l'adresse
     * @param identifiant l'identifiant récupéré depuis la BDD
     */
    public void setIdentifiant(final Integer identifiant) {
        this.identifiant = identifiant;
    }


    /**.
     * Méthode toString pour récupérer l'ensemble
     * des infos de l'objet
     * @return Les infos complétes de l'objet
     */
    @Override
    public String toString() {
        return "Adresse{"
                +
                "numeroDeRue='" + numeroDeRue + '\''
                +
                ", nomDeRue='" + nomDeRue + '\''
                +
                ", codePostal='" + codePostal + '\''
                +
                ", ville='" + ville + '\''
                +
                '}';
    }
}
