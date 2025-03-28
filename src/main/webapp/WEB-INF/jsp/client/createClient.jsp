<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Formulaire création client</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<main class="container my-5">
    <h1 class="mb-4" style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;">
        Ajouter un client
    </h1>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger">
        <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <form action="?cmd=clients/add/validate" method="POST">
        <div class="mb-3">
            <label for="clientId" class="form-label">Identifiant</label>
            <input
                    type="text"
                    class="form-control"
                    id="clientId"
                    name="clientId"
                    value="${client.identifiantClient}"
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
                    value="${client.raisonSociale}"
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
                    value="${client.adresseMail}"
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
                    value="${client.telephone}"
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
                    value="${client.adresse.numeroDeRue}"
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
                    value="${client.adresse.nomDeRue}"
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
                    value="${client.adresse.codePostal}"
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
                    value="${client.adresse.ville}"
                    required
            />
        </div>

        <div class="mb-3">
            <label for="chiffreAffaire" class="form-label">Chiffre d'Affaire</label>
            <input
                    type="number"
                    class="form-control"
                    id="chiffreAffaire"
                    name="chiffreAffaire"
                    placeholder="Ex : 100000"
                    value="${client.chiffreAffaire}"
                    required
            />
        </div>

        <div class="mb-4">
            <label for="nbEmploye" class="form-label">Nombre d'employé</label>
            <input
                    type="number"
                    class="form-control"
                    id="nbEmploye"
                    name="nbEmploye"
                    placeholder="Ex : 50"
                    value="${client.nbrEmploye}"
                    required
            />
        </div>

        <input type="hidden" name="csrfToken" value="${token}">

        <button
                type="submit"
                class="btn btn-primary"
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
