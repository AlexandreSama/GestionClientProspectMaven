package models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**.
 * Classe métier pour un client
 */
public class Client extends Societe {

    /**.
     * Identifiant du client
     */
    private Integer identifiantClient = null;
    /**.
     * chiffre d'affaire du client
     */
    @NotNull
    @Size(min = 4, max = 20)
    private long chiffreAffaire;
    /**.
     * Nombre d'employés du client
     */
    @NotNull
    @Size(min = 3, max = 20)
    private int nbrEmploye;
    /**.
     * Liste de contrats du client
     */
    private final List<Contrat> contrats = new ArrayList<>();

    /**.
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
     * @param gestionnaire      L'utilisateur gérant le client
     */
    public Client(final Integer identifiantClient, final Adresse adresse,
                  final String adresseMail, final String commentaire,
                  final String raisonSociale, final String telephone,
                  final long chiffreAffaire, final int nbrEmploye,
                  final User gestionnaire) {
        super(adresse, adresseMail, commentaire,
                raisonSociale, telephone, gestionnaire);
        setIdentifiantClient(identifiantClient);
        setChiffreAffaire(chiffreAffaire);
        setNbrEmploye(nbrEmploye);
    }

    /**.
     * Définit l'identifiant spécifique du client.
     *
     * @param identifiantClient Identifiant du client.
     */
    public void setIdentifiantClient(final Integer identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    /**.
     * Retourne l'identifiant spécifique du client.
     *
     * @return Identifiant du client.
     */
    public Integer getIdentifiantClient() {
        return identifiantClient;
    }

    /**.
     * Retourne le chiffre d'affaires du client.
     *
     * @return Chiffre d'affaires.
     */
    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    /**.
     * Définit le chiffre d'affaires du client après validation.
     *
     * @param chiffreAffaire Chiffre d'affaires à définir.
     */
    public void setChiffreAffaire(final long chiffreAffaire)  {
        this.chiffreAffaire = chiffreAffaire;
    }

    /**.
     * Retourne le nombre d'employés du client.
     *
     * @return Nombre d'employés.
     */
    public int getNbrEmploye() {
        return nbrEmploye;
    }

    /**.
     * Définit le nombre d'employés du client après validation.
     *
     * @param nbrEmploye Nombre d'employés à définir.
     */
    public void setNbrEmploye(final int nbrEmploye) {
        this.nbrEmploye = nbrEmploye;
    }

    /**.
     * Méthode toString pour récupérer l'ensemble
     * des infos de l'objet
     * @return Les infos complétes de l'objet
     */
    @Override
    public String toString() {
        return "Client{"
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
                ", chiffreAffaire="
                + getChiffreAffaire()
                +
                ", nbrEmploye="
                + getNbrEmploye()
                +
                ", adresse="
                + (getAdresse() != null ? getAdresse().toString() : "null")
                +
                '}';
    }

    /**.
     * Récupère la liste des contrats
     * @return une liste de contrats
     */
    public List<Contrat> getContrats() {
        return contrats;
    }

    /**.
     * Ajoute un contrat au client
     * @param contrat Un objet Contrat
     */
    public void addContrat(final Contrat contrat) {
        this.contrats.add(contrat);
    }
}
