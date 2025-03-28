package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe métier pour un client
 */
@Entity
@Table(name = "client")
public class Client extends Societe {

    /**
     * Chiffre d'affaires du client
     */
    @NotNull
    @Max(2000000)
    private long chiffreAffaire;

    /**
     * Nombre d'employés du client
     */
    @NotNull
    @Max(2000000)
    private int nbrEmploye;

    /**
     * Liste de contrats du client
     */
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrat> contrats = new ArrayList<>();

    // Constructeur sans argument requis par JPA
    public Client() {
        super();
    }

    /**
     * Constructeur pour modifier / supprimer un client.
     */
    public Client(final Adresse adresse,
                  final String adresseMail, final String commentaire,
                  final String raisonSociale, final String telephone,
                  final long chiffreAffaire, final int nbrEmploye,
                  final User gestionnaire) {
        super(adresse, adresseMail, commentaire, raisonSociale, telephone, gestionnaire);
        this.chiffreAffaire = chiffreAffaire;
        this.nbrEmploye = nbrEmploye;
    }

    /**
     * Constructeur pour créer un client.
     */
    public Client(final Adresse adresse,
                  final String adresseMail, final String commentaire,
                  final String raisonSociale, final String telephone,
                  final long chiffreAffaire, final int nbrEmploye,
                  final User gestionnaire, final List<Contrat> contrats) {
        super(adresse, adresseMail, commentaire, raisonSociale, telephone, gestionnaire);
        this.chiffreAffaire = chiffreAffaire;
        this.nbrEmploye = nbrEmploye;
        this.contrats = contrats;
    }

    public long getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(final long chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public int getNbrEmploye() {
        return nbrEmploye;
    }

    public void setNbrEmploye(final int nbrEmploye) {
        this.nbrEmploye = nbrEmploye;
    }

    public List<Contrat> getContrats() {
        return contrats;
    }

    /**
     * Ajoute un contrat au client et établit la relation bidirectionnelle.
     */
    public void addContrat(final Contrat contrat) {
        contrats.add(contrat);
        contrat.setClient(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + getIdentifiant() +
                ", chiffreAffaire=" + chiffreAffaire +
                ", nbrEmploye=" + nbrEmploye +
                '}';
    }
}
