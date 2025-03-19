package models;

import models.types.InterestedType;

import java.time.LocalDate;

public class Prospect extends Societe {

    private Integer identifiantProspect = null;
    private LocalDate dateProspection;
    private InterestedType estInteresse;

    /**
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
     */
    public Prospect(Integer identifiantProspect, Adresse adresse, String adresseMail, String commentaire,
                    String raisonSociale, String telephone,
                    LocalDate dateProspection, InterestedType estInteresse) {
        super(adresse, adresseMail, commentaire, raisonSociale, telephone);
        setIdentifiantProspect(identifiantProspect);
        setDateProspection(dateProspection);
        setEstInteresse(estInteresse);
    }

    /**
     * Définit l'identifiant spécifique du prospect.
     *
     * @param identifiantProspect Identifiant du prospect.
     */
    public void setIdentifiantProspect(Integer identifiantProspect) {
        this.identifiantProspect = identifiantProspect;
    }

    /**
     * Retourne l'identifiant spécifique du prospect.
     *
     * @return Identifiant du prospect.
     */
    public Integer getIdentifiantProspect() {
        return identifiantProspect;
    }

    /**
     * Retourne la date de prospection.
     *
     * @return Date de prospection.
     */
    public LocalDate getDateProspection() {
        return dateProspection;
    }

    /**
     * Définit la date de prospection après validation.
     * La date ne peut pas être nulle ni être dans le futur.
     *
     * @param dateProspection Date de prospection.
     */
    public void setDateProspection(LocalDate dateProspection) {
        this.dateProspection = dateProspection;
    }

    /**
     * Retourne le statut d'intérêt du prospect.
     *
     * @return Statut d'intérêt (OUI/NON).
     */
    public InterestedType getEstInteresse() {
        return estInteresse;
    }

    /**
     * Définit le statut d'intérêt du prospect après validation.
     * Le statut ne peut pas être nul.
     *
     * @param estInteresse Statut d'intérêt (OUI/NON).
     */
    public void setEstInteresse(InterestedType estInteresse) {
        this.estInteresse = estInteresse;
    }

    @Override
    public String toString() {
        return "Prospect{" +
                "id=" + getIdentifiant() +
                ", raisonSociale='" + getRaisonSociale() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", email='" + getAdresseMail() + '\'' +
                ", dateProspection=" + (dateProspection != null ? dateProspection.toString() : "null") +
                ", estInteresse=" + estInteresse +
                ", adresse=" + (getAdresse() != null ? getAdresse().toString() : "null") +
                '}';
    }
}