<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Liste des clients</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<main class="container my-5">
    <h1
            class="mb-4"
            style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;"
    >
        Liste des clients
    </h1>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Raison Sociale</th>
                <th scope="col">Email</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Numéro de rue</th>
                <th scope="col">Nom de rue</th>
                <th scope="col">Code Postal</th>
                <th scope="col">Ville</th>
                <th scope="col">Chiffre d'Affaire</th>
                <th scope="col">Nombre d'employé</th>
                <th scope="col">Contrats</th>
                <th scope="col" style="min-width:200px;">Actions</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="client" items="${clients}" varStatus="status">
                    <tr>
                        <!-- Numéro de ligne -->
                        <th scope="row">${status.index + 1}</th>

                        <!-- Raison Sociale avec lien vers une modale -->
                        <td>
                            <a href="#"
                               class="text-decoration-none entity-link"
                               data-bs-toggle="modal"
                               data-bs-target="#clientModal"
                               data-raison="${client.raisonSociale}"
                               data-email="${client.adresseMail}"
                               data-phone="${client.telephone}"
                               data-chiffreAffaire="${client.chiffreAffaire}"
                               data-nbrEmploye="${client.nbrEmploye}"
                               data-localisation="${client.numeroDeRue} ${client.nomDeRue}, ${client.codePostal} ${client.ville}"
                               data-meteo=""
                            >
                                    ${client.raisonSociale}
                            </a>
                        </td>

                        <!-- Email -->
                        <td>${client.adresseMail}</td>

                        <!-- Téléphone -->
                        <td>${client.telephone}</td>

                        <!-- Numéro de rue -->
                        <td>${client.numeroDeRue}</td>

                        <!-- Nom de rue -->
                        <td>${client.nomDeRue}</td>

                        <!-- Code Postal -->
                        <td>${client.codePostal}</td>

                        <!-- Ville -->
                        <td>${client.ville}</td>

                        <!-- Chiffre d'Affaire -->
                        <td>${client.chiffreAffaire}</td>

                        <!-- Nombre d'employé -->
                        <td>${client.nbrEmploye}</td>

                        <!-- Bouton Voir les contrats -->
                        <td>
                            <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#contractsModal">
                                Voir les contrats
                            </button>
                        </td>

                        <!-- Actions (Modifier / Supprimer) -->
                        <td>
                            <a href="?cmd=clients/update&id=${client.idClient}" class="btn btn-sm btn-primary">Modifier</a>
                            <a href="?cmd=clients/delete&id=${client.idClient}" class="btn btn-sm btn-danger ms-2">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-end mt-3">
        <a href="?cmd=clients/add" class="btn btn-success">
            Ajouter un client
        </a>
    </div>

    <!-- Modal Contrats -->
    <div class="modal fade" id="contractsModal" tabindex="-1" aria-labelledby="contractsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="contractsModalLabel">Liste des Contrats</h5>
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
                            <c:if test="${not empty client.contracts}">
                                <c:forEach var="contract" items="${client.contracts}">
                                    <tr>
                                        <td>${contract.idContrat}</td>
                                        <td>${contract.libelle}</td>
                                        <td>${contract.montant}</td>
                                        <td>
                                            <a href="?cmd=contract/update&id=${contract.idContrat}&libelle=${contract.libelle}&montant=${contract.montant}" class="btn btn-sm btn-primary">Modifier</a>
                                            <a href="?cmd=contract/delete&id=${contract.idContrat}" class="btn btn-sm btn-danger ms-2">Supprimer</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty client.contracts}">
                                <tr>
                                    <td colspan="4" class="text-center">Aucun contrat disponible.</td>
                                </tr>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                    <a href="../contract/formContract.html" class="btn btn-success">Ajouter un contrat</a>
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Modal unique pour Clients / Prospects -->
<div class="modal fade" id="clientModal" tabindex="-1" aria-labelledby="clientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="clientModalLabel">Détails</h1>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div class="modal-body">
                <p><strong>Raison Sociale :</strong> <span id="modalRaisonSociale"></span></p>
                <p><strong>Email :</strong> <span id="modalEmail"></span></p>
                <p><strong>Numéro de téléphone :</strong> <span id="modalTelephone"></span></p>
                <p><strong>Chiffre d'affaire :</strong> <span id="modalChiffreAffaire"></span></p>
                <p><strong>Nombre d'employé :</strong> <span id="modalNbrEmploye"></span></p>
                <p><strong>Adresse :</strong> <span id="modalAdresse"></span></p>
                <p><strong>Géolocalisation :</strong> <span id="modalLocalisation"></span></p>
                <p><strong>Météo :</strong> <span id="modalMeteo"></span></p>
            </div>
            <div class="modal-footer">
                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                >Fermer</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="js/all.js"></script>
<script src="js/client-prospect-contract/client-prospectModal.js"></script>
</body>
</html>
