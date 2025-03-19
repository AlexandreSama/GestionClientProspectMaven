package models;

public class Contrat {

    private Integer idContrat = null;
    private Integer idClient = null;
    private String nomContrat;
    private Double montantContrat;

    public Contrat(String nomContrat, Double montantContrat) {
        setNomContrat(nomContrat);
        setMontantContrat(montantContrat);
    }

    public Double getMontantContrat() {
        return montantContrat;
    }

    public void setMontantContrat(Double montantContrat) {
        this.montantContrat = montantContrat;
    }

    public String getNomContrat() {
        return nomContrat;
    }

    public void setNomContrat(String nomContrat) {
        this.nomContrat = nomContrat;
    }

    public Integer getClient() {
        return idClient;
    }

    public void setClient(int idClient) {
        this.idClient = idClient;
    }

    public Integer getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(Integer idContrat) {
        this.idContrat = idContrat;
    }
}
