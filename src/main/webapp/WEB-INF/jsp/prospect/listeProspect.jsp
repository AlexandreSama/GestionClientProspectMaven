<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19/03/2025
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spark | Liste des prospects</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link rel="stylesheet" href="node_modules/leaflet/dist/leaflet.css">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>

<main class="container my-5">
    <h1
            class="mb-4"
            style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;"
    >
        Liste des prospects
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
                <th scope="col">#</th>
                <th scope="col">Raison Sociale</th>
                <th scope="col">Email</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Numéro de rue</th>
                <th scope="col">Nom de rue</th>
                <th scope="col">Code Postal</th>
                <th scope="col">Ville</th>
                <th scope="col">Date de Prospection</th>
                <th scope="col">Est intéressé</th>
                <th scope="col" style="min-width:200px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="prospect" items="${prospects}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>
                        <a href="#clientModal"
                           class="text-decoration-none entity-link"
                           data-bs-toggle="modal"
                           data-bs-target="#clientModal"
                           data-raison="${prospect.raisonSociale}"
                           data-email="${prospect.adresseMail}"
                           data-phone="${prospect.telephone}"
                           data-prospection="${prospect.dateProspection}"
                           data-interest="${prospect.estInteresse}"
                           data-localisation="${prospect.numeroDeRue} ${prospect.nomDeRue}, ${prospect.codePostal} ${prospect.ville}"
                           data-meteo="">
                            ${prospect.raisonSociale}
                        </a>
                    </td>
                    <td>${prospect.adresseMail}</td>
                    <td>${prospect.telephone}</td>
                    <td>${prospect.numeroDeRue}</td>
                    <td>${prospect.nomDeRue}</td>
                    <td>${prospect.codePostal}</td>
                    <td>${prospect.ville}</td>
                    <td>${prospect.dateProspection}</td>
                    <td>${prospect.estInteresse}</td>
                    <td>
                        <a href="?cmd=prospects/update&id=${prospect.idProspect}" class="btn btn-sm btn-primary">Modifier</a>
                        <a href="?cmd=prospects/delete&id=${prospect.idProspect}" class="btn btn-sm btn-danger ms-2">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="text-end mt-3">
        <a href="?cmd=prospects/add" class="btn btn-success">Ajouter un prospect</a>
    </div>
</main>

<div class="modal fade" id="clientModal" tabindex="-1" aria-labelledby="clientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <!-- Vous pouvez mettre "Détails" ou "Détails du prospect" au choix -->
                <h1 class="modal-title fs-5" id="clientModalLabel">Détails du prospect</h1>
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
                <p><strong>Date de prospection :</strong> <span id="modalDateProspection"></span></p>
                <p><strong>Est intéressé :</strong> <span id="modalInteress"></span></p>
                <p><strong>Adresse :</strong> <span id="modalAdresse"></span></p>
                <p><strong>Localisation :</strong> <span id="modalLocalisation"></span></p>
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
<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.js"></script>
<script src="node_modules/leaflet/dist/leaflet.js"></script>
<script src="js/client-prospectModal.js"></script>
</body>
</html>
