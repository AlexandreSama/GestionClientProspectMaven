package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Classe représentant un contrat.
 */
@Entity
@Table(name = "contrat")
public class Contrat {

    /**
     * Identifiant du contrat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idContrat;

    /**
     * Client associé au contrat.
     */
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    /**
     * Nom du contrat.
     */
    private String nomContrat;

    /**
     * Montant du contrat.
     */
    private Double montantContrat;

    /**
     * Constructeur sans argument requis par JPA.
     */
    public Contrat() {
    }

    /**
     * Constructeur pour initialiser un contrat sans client.
     *
     * @param nomContrat    Nom du contrat.
     * @param montantContrat Montant du contrat.
     */
    public Contrat(final String nomContrat, final Double montantContrat) {
        this.nomContrat = nomContrat;
        this.montantContrat = montantContrat;
    }

    /**
     * Constructeur pour un contrat déjà préparé avec client.
     *
     * @param idContrat     Identifiant du contrat.
     * @param client        Client associé au contrat.
     * @param nomContrat    Nom du contrat.
     * @param montantContrat Montant du contrat.
     */
    public Contrat(final Integer idContrat, final Client client,
                   final String nomContrat, final Double montantContrat) {
        this.idContrat = idContrat;
        this.client = client;
        this.nomContrat = nomContrat;
        this.montantContrat = montantContrat;
    }

    public Integer getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(final Integer idContrat) {
        this.idContrat = idContrat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(final Client client) {
        this.client = client;
    }

    public String getNomContrat() {
        return nomContrat;
    }

    public void setNomContrat(final String nomContrat) {
        this.nomContrat = nomContrat;
    }

    public Double getMontantContrat() {
        return montantContrat;
    }

    public void setMontantContrat(final Double montantContrat) {
        this.montantContrat = montantContrat;
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", client=" + (client != null ? client.getIdentifiant() : null) +
                ", nomContrat='" + nomContrat + '\'' +
                ", montantContrat=" + montantContrat +
                '}';
    }
}
