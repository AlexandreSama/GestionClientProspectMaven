package models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**.
 * Classe métier abstract pour une société
 */
public abstract class Societe {
    /**.
     * Identifiant de la société
     */
    private Integer identifiant = null;
    /**.
     * Adresse de la société
     */
    @NotNull
    private Adresse adresse;
    /**.
     * Email de la société
     */
    @NotNull
    @Email
    private String adresseMail;
    /**.
     * Commentaire de la société
     */
    private String commentaire;
    /**.
     * Téléphone de la société
     */
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    @Size(min = 12, max = 12)
    private String telephone;
    /**.
     * Raison Sociale de la société
     */
    @NotNull
    @Size(min = 2, max = 50)
    private String raisonSociale;

    /**.
     * Constructeur pour initialiser une
     * société avec les informations de base.
     * Les setters sont utilisés pour
     * appliquer la validation sur chaque attribut.
     *
     * @param adresse        Adresse de la société.
     * @param adresseMail    Adresse e-mail de la société.
     * @param commentaire    Commentaire sur la société.
     * @param raisonSociale  Raison sociale de la société.
     * @param telephone      Numéro de téléphone de la société.
     */
    public Societe(final Adresse adresse, final String adresseMail,
                   final String commentaire, final String raisonSociale,
                   final String telephone) {
        setAdresse(adresse);
        setAdresseMail(adresseMail);
        setCommentaire(commentaire);
        setTelephone(telephone);
        setRaisonSociale(raisonSociale);
    }

    /**.
     * Retourne l'adresse de la société.
     *
     * @return Adresse de la société.
     */
    @NotNull
    public Adresse getAdresse() {
        return adresse;
    }

    /**.
     * Définit l'adresse de la société.
     *
     * @param adresse Adresse à définir.
     */
    public void setAdresse(final Adresse adresse) {
        this.adresse = adresse;
    }

    /**.
     * Retourne l'adresse e-mail de la société.
     *
     * @return Adresse e-mail de la société.
     */
    @NotNull
    public String getAdresseMail() {
        return adresseMail;
    }

    /**.
     * Définit l'adresse e-mail de la société.
     *
     * @param adresseMail Adresse e-mail à définir.
     */
    public void setAdresseMail(final String adresseMail) {
        this.adresseMail = adresseMail;
    }

    /**.
     * Retourne le commentaire sur la société.
     *
     * @return Commentaire sur la société.
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**.
     * Définit un commentaire pour la société.
     *
     * @param commentaire Commentaire à définir.
     */
    public void setCommentaire(final String commentaire) {
        this.commentaire = commentaire != null ? commentaire.trim() : null;
    }

    /**.
     * Retourne l'identifiant unique de la société.
     *
     * @return Identifiant de la société.
     */
    @NotNull
    public Integer getIdentifiant() {
        return identifiant;
    }

    /**.
     * Définit un identifiant pour la société
     * @param identifiant l'identifiant a donner pour cet société
     */
    public void setIdentifiant(final Integer identifiant) {
        this.identifiant = identifiant;
    }

    /**.
     * Retourne la raison sociale de la société.
     *
     * @return Raison sociale de la société.
     */
    @NotNull
    public String getRaisonSociale() {
        return raisonSociale;
    }

    /**.
     * Définit la raison sociale de la société.
     *
     * @param raisonSociale Raison sociale à définir.
     */
    public void setRaisonSociale(final String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    /**.
     * Retourne le numéro de téléphone de la société.
     *
     * @return Numéro de téléphone de la société.
     */
    @NotNull
    public String getTelephone() {
        return telephone;
    }

    /**.
     * Définit le numéro de téléphone de la société.
     *
     * @param telephone Numéro de téléphone à définir.
     */
    public void setTelephone(final String telephone) {
        this.telephone = telephone.trim();
    }
}
