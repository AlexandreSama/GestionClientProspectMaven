<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Supprimer un client</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<main class="container my-5">
    <h1 class="mb-4" style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;">
        Supprimer le client
    </h1>

    <form>
        <div class="mb-3">
            <label for="clientId" class="form-label">Identifiant</label>
            <input
                    type="text"
                    class="form-control"
                    id="clientId"
                    name="clientId"
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
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
                    readonly
            />
        </div>

        <button
                type="submit"
                class="btn btn-danger"
        >
            Supprimer
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="js/all.js"></script>
</body>
</html>
