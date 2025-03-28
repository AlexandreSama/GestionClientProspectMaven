<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spark | Liste des clients</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="node_modules/leaflet/dist/leaflet.css">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>

<main class="container my-5">
    <h1 class="mb-4" style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;">
        Liste des clients
    </h1>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th>#</th>
                <th>Raison Sociale</th>
                <th>Email</th>
                <th>Téléphone</th>
                <th>N° de rue</th>
                <th>Nom de rue</th>
                <th>Code Postal</th>
                <th>Ville</th>
                <th>Chiffre d'Affaire</th>
                <th>Nb d'employés</th>
                <th>Contrats</th>
                <th style="min-width:200px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="client" items="${clients}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>
                        <!-- Modal client unique déclenchée par ce lien -->
                        <a href="#clientModal"
                           class="text-decoration-none entity-link"
                           data-bs-toggle="modal"
                           data-bs-target="#clientModal"
                           data-raison="${client.raisonSociale}"
                           data-email="${client.adresseMail}"
                           data-phone="${client.telephone}"
                           data-chiffreAffaire="${client.chiffreAffaire}"
                           data-nbrEmploye="${client.nbrEmploye}"
                           data-localisation="${client.adresse.numeroDeRue} ${client.adresse.nomDeRue}, ${client.adresse.codePostal} ${client.adresse.ville}"
                           data-meteo="">
                                ${client.raisonSociale}
                        </a>
                    </td>
                    <td>${client.adresseMail}</td>
                    <td>${client.telephone}</td>
                    <td>${client.adresse.numeroDeRue}</td>
                    <td>${client.adresse.nomDeRue}</td>
                    <td>${client.adresse.codePostal}</td>
                    <td>${client.adresse.ville}</td>
                    <td>${client.chiffreAffaire}</td>
                    <td>${client.nbrEmploye}</td>
                    <td>
                        <!-- Bouton pour ouvrir la modal des contrats de CE client -->
                        <button class="btn btn-sm btn-info"
                                data-bs-toggle="modal"
                                data-bs-target="#contractsModal-${client.identifiant}">
                            Voir les contrats
                        </button>
                    </td>
                    <td>
                        <a href="?cmd=clients/update&id=${client.identifiant}" class="btn btn-sm btn-primary">Modifier</a>
                        <a href="?cmd=clients/delete&id=${client.identifiant}" class="btn btn-sm btn-danger ms-2">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-end mt-3">
        <a href="?cmd=clients/add" class="btn btn-success">Ajouter un client</a>
    </div>

    <!-- 1) Modal unique pour les détails du client -->
    <div class="modal fade" id="clientModal" tabindex="-1" aria-labelledby="clientModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="clientModalLabel">Détails</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><strong>Raison Sociale :</strong> <span id="modalRaisonSociale"></span></p>
                    <p><strong>Email :</strong> <span id="modalEmail"></span></p>
                    <p><strong>Téléphone :</strong> <span id="modalTelephone"></span></p>
                    <p><strong>Chiffre d'affaire :</strong> <span id="modalChiffreAffaire"></span></p>
                    <p><strong>Nb d'employés :</strong> <span id="modalNbrEmploye"></span></p>
                    <p><strong>Adresse :</strong> <span id="modalAdresse"></span></p>
                    <p><strong>Géolocalisation :</strong> <span id="modalLocalisation"></span></p>
                    <p><strong>Météo :</strong> <span id="modalMeteo"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button"
                            class="btn btn-secondary"
                            data-bs-dismiss="modal">
                        Fermer
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- 2) Génération des modals contrats en dehors du tableau -->
    <c:forEach var="client" items="${clients}">
        <div class="modal fade" id="contractsModal-${client.identifiant}" tabindex="-1"
             aria-labelledby="contractsModalLabel-${client.identifiant}" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="contractsModalLabel-${client.identifiant}">
                            Liste des Contrats pour ${client.raisonSociale}
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Identifiant</th>
                                    <th>Libellé du contrat</th>
                                    <th>Montant du contrat</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty client.contrats}">
                                    <c:forEach var="contract" items="${client.contrats}">
                                        <tr>
                                            <td>${contract.idContrat}</td>
                                            <td>${contract.nomContrat}</td>
                                            <td>${contract.montantContrat}</td>
                                            <td>
                                                <a href="?cmd=contract/update&id=${contract.idContrat}&libelle=${contract.nomContrat}&montant=${contract.montantContrat}"
                                                   class="btn btn-sm btn-primary">
                                                    Modifier
                                                </a>
                                                <a href="?cmd=contract/delete&id=${contract.idContrat}"
                                                   class="btn btn-sm btn-danger ms-2">
                                                    Supprimer
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty client.contrats}">
                                    <tr>
                                        <td colspan="4" class="text-center">Aucun contrat disponible.</td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button"
                                class="btn btn-secondary"
                                data-bs-dismiss="modal">
                            Fermer
                        </button>
                        <a href="../contract/formContract.html" class="btn btn-success">Ajouter un contrat</a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</main>

<%@ include file="../footer.jsp" %>
<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.js"></script>
<script src="node_modules/leaflet/dist/leaflet.js"></script>

<script src="js/client-prospectModal.js"></script>
</body>
</html>
