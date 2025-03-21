package models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**.
 * Classe métier pour un user
 */
public class User {

    /**.
     * Identifiant de l'utilisateur
     */
    private Integer identifiantUser;

    /**.
     * Pseudo de l'utilisateur
     */
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    /**.
     * Mot de passe hashé de l'utilisateur
     */
    @NotNull
    @Size(min = 10, max = 255)
    private String pwd;

    /**.
     * Liste de sociétés que
     * l'utilisateur gère
     */
    private final List<Societe> societes = new ArrayList<>();

    /**.
     * Constructeur de la classe User si l'utilisateur est déjà créer
     * @param identifiantUser L'identifiant de l'utilisateur
     * @param pwd Mot de passe de l'utilisateur
     * @param username Pseudo de l'utilisateur
     */
    public User(final Integer identifiantUser,
                final String pwd, final String username) {
        setIdentifiantUser(identifiantUser);
        setPwd(pwd);
        setUsername(username);
    }

    /**.
     * Constructeur de la classe User si l'utilisateur n'est pas encore créer
     * @param pwd Mot de passe de l'utilisateur
     * @param username Pseudo de l'utilisateur
     */
    public User(final String pwd, final String username) {
        setPwd(pwd);
        setUsername(username);
    }

    /**.
     * Retourne l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public Integer getIdentifiantUser() {
        return identifiantUser;
    }

    /**.
     * Ajoute l'identifiant de l'utilisateur
     * @param identifiantUser L'identifiant de l'utilisateur
     */
    public void setIdentifiantUser(final Integer identifiantUser) {
        this.identifiantUser = identifiantUser;
    }

    /**.
     * Retourne le pseudonyme de l'utilisateur
     * @return le pseudonyme de l'utilisateur
     */
    public String getUsername() {
        return username;
    }

    /**.
     * Retourne le pseudonyme de l'utilisateur
     * @param username le pseudonyme de l'utilisateur
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**.
     * Retourne le mot de passe de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    public String getPwd() {
        return pwd;
    }

    /**.
     * Ajoute le mot de passe de l'utilisateur
     * @param pwd le mot de passe de l'utilisateur
     */
    public void setPwd(final String pwd) {
        this.pwd = pwd;
    }

    /**.
     * Retourne la liste des sociétés
     * que l'utilisateur gère
     * @return la liste des sociétés
     * que l'utilisateur gère
     */
    public List<Societe> getSocietes() {
        return societes;
    }

    /**.
     * Ajoute une société a la liste
     * @param societe la société a ajouté
     */
    public void addSociete(final Societe societe) {
        societes.add(societe);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
