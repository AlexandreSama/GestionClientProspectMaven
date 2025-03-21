package models;

/**.
 * Classe métier pour un contrat
 */
public class Contrat {

    /**.
     * Identifiant du contrat
     */
    private Integer idContrat = null;
    /**.
     * Identifiant du client
     */
    private Integer idClient = null;
    /**.
     * Nom du contrat
     */
    private String nomContrat;
    /**.
     * Montant du contrat
     */
    private Double montantContrat;

    /**.
     * Constructeur pour initialiser un contrat.
     *
     * @param nomContrat Nom du contrat
     * @param montantContrat Montant du contrat
     */
    public Contrat(final String nomContrat, final Double montantContrat) {
        setNomContrat(nomContrat);
        setMontantContrat(montantContrat);
    }

    /**.
     * Constructeur pour un contrat déjà préparé
     * @param id L'identifiant du contrat
     * @param idClient L'identifiant du client lié au contrat
     * @param nomContrat Le nom du contrat
     * @param montantContrat Le montant du contrat
     */
    public Contrat(final Integer id, final Integer idClient,
                   final String nomContrat, final Double montantContrat) {
        setIdContrat(id);
        setClient(idClient);
        setNomContrat(nomContrat);
        setMontantContrat(montantContrat);
    }

    /**.
     * Retourne le montant du contrat
     * @return le montant du contrat (Double)
     */
    public Double getMontantContrat() {
        return montantContrat;
    }

    /**.
     * Ajoute le montant du contrat
     * @param montantContrat Double, montant du contrat
     */
    public void setMontantContrat(final Double montantContrat) {
        this.montantContrat = montantContrat;
    }

    /**.
     * Retourne le nom du contrat
     * @return le nom du contrat (String)
     */
    public String getNomContrat() {
        return nomContrat;
    }

    /**.
     * Ajoute le nom du contrat
     * @param nomContrat Le nom du contrat
     */
    public void setNomContrat(final String nomContrat) {
        this.nomContrat = nomContrat;
    }

    /**.
     * Retourne l'identifiant du client
     * @return l'identifiant du client
     */
    public Integer getClient() {
        return idClient;
    }

    /**.
     * Ajoute l'identifiant du client
     * @param idClient l'identifiant du client
     */
    public void setClient(final int idClient) {
        this.idClient = idClient;
    }

    /**.
     * Retourne l'identifiant du contrat
     * @return l'identifiant du contrat
     */
    public Integer getIdContrat() {
        return idContrat;
    }

    /**.
     * Ajoute l'identifiant du contrat
     * @param idContrat l'identifiant du contrat
     */
    public void setIdContrat(final Integer idContrat) {
        this.idContrat = idContrat;
    }

    /**.
     * Retourne les infos de l'objet en string
     * @return les infos de l'objet en string
     */
    @Override
    public String toString() {
        return "Contrat{"
                +
                "idClient="
                + idClient
                +
                ", idContrat="
                + idContrat
                +
                ", nomContrat='"
                + nomContrat
                + '\''
                +
                ", montantContrat="
                + montantContrat
                +
                '}';
    }
}
