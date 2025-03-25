package models;

import models.types.InterestedType;

import java.time.LocalDate;

/**.
 * Classe métier pour un prospect
 */
public class Prospect extends Societe {

    /**.
     * L'identifiant du prospect
     */
    private Integer identifiantProspect = null;
    /**.
     * La date de prospection du prospect
     */
    private LocalDate dateProspection;
    /**.
     * Si le prospect est interéssé ou non
     */
    private InterestedType estInteresse;

    /**.
     * Constructeur pour créer un prospect avec les informations spécifiées.
     *
     * @param identifiantProspect Identifiant unique du prospect.
     * @param adresse           Adresse du prospect.
     * @param adresseMail       Adresse e-mail du prospect.
     * @param commentaire       Commentaire sur le prospect.
     * @param raisonSociale     Raison sociale du prospect.
     * @param telephone         Numéro de téléphone du prospect.
     * @param dateProspection   Date de prospection.
     * @param estInteresse      Statut d'intérêt (OUI/NON).
     * @param gestionnaire      L'utilisateur gérant ce prospect
     */
    public Prospect(final Integer identifiantProspect, final Adresse adresse,
                    final String adresseMail, final String commentaire,
                    final String raisonSociale, final String telephone,
                    final LocalDate dateProspection,
                    final InterestedType estInteresse,
                    final Integer gestionnaire) {
        super(adresse, adresseMail, commentaire,
                raisonSociale, telephone, gestionnaire);
        setIdentifiantProspect(identifiantProspect);
        setDateProspection(dateProspection);
        setEstInteresse(estInteresse);
    }

    /**.
     * Définit l'identifiant spécifique du prospect.
     *
     * @param identifiantProspect Identifiant du prospect.
     */
    public void setIdentifiantProspect(final Integer identifiantProspect) {
        this.identifiantProspect = identifiantProspect;
    }

    /**.
     * Retourne l'identifiant spécifique du prospect.
     *
     * @return Identifiant du prospect.
     */
    public Integer getIdentifiantProspect() {
        return identifiantProspect;
    }

    /**.
     * Retourne la date de prospection.
     *
     * @return Date de prospection.
     */
    public LocalDate getDateProspection() {
        return dateProspection;
    }

    /**.
     * Définit la date de prospection après validation.
     * La date ne peut pas être nulle ni être dans le futur.
     *
     * @param dateProspection Date de prospection.
     */
    public void setDateProspection(final LocalDate dateProspection) {
        this.dateProspection = dateProspection;
    }

    /**.
     * Retourne le statut d'intérêt du prospect.
     *
     * @return Statut d'intérêt (OUI/NON).
     */
    public InterestedType getEstInteresse() {
        return estInteresse;
    }

    /**.
     * Définit le statut d'intérêt du prospect après validation.
     * Le statut ne peut pas être nul.
     *
     * @param estInteresse Statut d'intérêt (OUI/NON).
     */
    public void setEstInteresse(final InterestedType estInteresse) {
        this.estInteresse = estInteresse;
    }

    /**.
     * Méthode toString pour récupérer l'ensemble
     * des infos de l'objet
     * @return Les infos complétes de l'objet
     */
    @Override
    public String toString() {
        return "Prospect{"
                +
                "id="
                + getIdentifiant()
                +
                ", raisonSociale='"
                + getRaisonSociale()
                + '\''
                +
                ", telephone='"
                + getTelephone()
                + '\''
                +
                ", email='"
                + getAdresseMail()
                + '\''
                +
                ", dateProspection="
                + (dateProspection != null
                ? dateProspection.toString() : "null")
                +
                ", estInteresse="
                + estInteresse
                +
                ", adresse="
                + (getAdresse() != null ? getAdresse().toString() : "null")
                +
                '}';
    }
}
