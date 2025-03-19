package models;

import java.util.ArrayList;
import java.util.List;

public class Client extends Societe {

    private Integer identifiantClient = null;
    private long chiffreAffaire;
    private int nbrEmploye;
    private final List<Contrat> contrats = new ArrayList<>();

    /**
     * Constructeur pour créer un client avec les informations spécifiées.
     *
     * @param identifiantClient Identifiant spécifique au client.
     * @param adresse           Adresse du client.
     * @param adresseMail       Adresse e-mail du client.
     * @param commentaire       Commentaire sur le client.
     * @param raisonSociale     Raison sociale du client.
     * @param telephone         Numéro de téléphone du client.
     * @param chiffreAffaire    Chiffre d'affaires du client.
     * @param nbrEmploye        Nombre d'employés du client.
     */
    public Client(Integer identifiantClient, Adresse adresse, String adresseMail, String commentaire,
                  String raisonSociale, String telephone,
                  long chiffreAffaire, int nbrEmploye) {
        super(adresse, adresseMail, commentaire, raisonSociale, telephone);
        setIdentifiantClient(identifiantClient);
        setChiffreAffaire(chiffreAffaire);
        setNbrEmploye(nbrEmploye);
    }

    /**
     * Définit l'identifiant spécifique du client.
     *
     * @param identifiantClient Identifiant du client.
     */
    public void setIdentifiantClient(Integer identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    /**
     * Retourne l'identifiant spécifique du client.
     *
     * @return Identifiant du client.
     */
    public Integer getIdentifiantClient() {
        return identifiantClient;
    }

    /**
     * Retourne le chiffre d'affaires du client.
     *
     * @return Chiffre d'affaires.
     */
    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**
     * Définit le chiffre d'affaires du client après validation.
     *
     * @param chiffreAffaire Chiffre d'affaires à définir.
     */
    public void setChiffreAffaire(long chiffreAffaire)  {
        this.chiffreAffaire = chiffreAffaire;
    }

    /**
     * Retourne le nombre d'employés du client.
     *
     * @return Nombre d'employés.
     */
    public int getNbrEmploye() {
        return nbrEmploye;
    }

    /**
     * Définit le nombre d'employés du client après validation.
     *
     * @param nbrEmploye Nombre d'employés à définir.
     */
    public void setNbrEmploye(int nbrEmploye) {
        this.nbrEmploye = nbrEmploye;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + getIdentifiant() +
                ", raisonSociale='" + getRaisonSociale() + '\'' +
                ", telephone='" + getTelephone() + '\'' +
                ", email='" + getAdresseMail() + '\'' +
                ", chiffreAffaire=" + getChiffreAffaire() +
                ", nbrEmploye=" + getNbrEmploye() +
                ", adresse=" + (getAdresse() != null ? getAdresse().toString() : "null") +
                '}';
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    public void addContrat(Contrat contrat) {
        this.contrats.add(contrat);
    }
}
