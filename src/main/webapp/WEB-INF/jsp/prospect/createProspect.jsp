<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19/03/2025
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Formulaire création prospect</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<main class="container my-5">
    <h1 class="mb-4" style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;">
        Ajouter un prospect
    </h1>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="?cmd=prospects/add/validate" method="POST">
        <div class="mb-3">
            <label for="clientId" class="form-label">Identifiant</label>
            <input
                    type="text"
                    class="form-control"
                    id="clientId"
                    name="clientId"
                    value="${prospect.identifiantClient}"
                    readonly
            />
        </div>

        <div class="mb-3">
            <label for="raisonSociale" class="form-label">Raison Sociale</label>
            <input
                    type="text"
                    class="form-control"
                    id="raisonSociale"
                    name="raisonSociale"
                    placeholder="Entrez la raison sociale"
                    value="${prospect.raisonSociale}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    placeholder="exemple@domaine.com"
                    value="${prospect.adresseMail}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="phone" class="form-label">Numéro de téléphone</label>
            <input
                    type="tel"
                    class="form-control"
                    id="phone"
                    name="phone"
                    placeholder="06 12 34 56 78"
                    value="${prospect.telephone}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="numeroRue" class="form-label">Numéro de rue</label>
            <input
                    type="text"
                    class="form-control"
                    id="numeroRue"
                    name="numeroRue"
                    placeholder="Ex : 14"
                    value="${prospect.adresse.numeroDeRue}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="nomRue" class="form-label">Nom de rue</label>
            <input
                    type="text"
                    class="form-control"
                    id="nomRue"
                    name="nomRue"
                    placeholder="Ex : Rue des Fleurs"
                    value="${prospect.adresse.nomDeRue}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="codePostal" class="form-label">Code Postal</label>
            <input
                    type="text"
                    class="form-control"
                    id="codePostal"
                    name="codePostal"
                    placeholder="Ex : 75000"
                    value="${prospect.adresse.codePostal}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="ville" class="form-label">Ville</label>
            <input
                    type="text"
                    class="form-control"
                    id="ville"
                    name="ville"
                    placeholder="Ex : Paris"
                    value="${prospect.adresse.ville}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="dateProspection" class="form-label">Date de prospection</label>
            <input
                    type="date"
                    class="form-control"
                    id="dateProspection"
                    name="dateProspection"
                    value="${prospect.dateProspection}"
                    required
            />
        </div>

        <div class="mb-4">
            <label for="estInteresse" class="form-label">Est Intéressé ?</label>
            <select class="form-select" id="estInteresse" name="estInteresse" required>
                <option value="">Sélectionnez une option</option>
                <option value="OUI" <c:if test="${prospect.estInteresse eq 'OUI'}">selected="selected"</c:if>>OUI</option>
                <option value="NON" <c:if test="${prospect.estInteresse eq 'NON'}">selected="selected"</c:if>>NON</option>
            </select>
        </div>

        <input type="hidden" name="csrfToken" value="${token}">

        <button
                type="submit"
                class="btn btn-success"
        >
            Enregistrer
        </button>
        <button
                type="button"
                class="btn btn-secondary ms-2"
                onclick="history.back()"
        >
            Annuler
        </button>
    </form>
</main>
<%@ include file="../footer.jsp" %>
<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.js"></script>

</body>
</html>
