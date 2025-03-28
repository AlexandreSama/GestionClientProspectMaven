<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Connexion</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%@ include file="../header.jsp" %>

<main>
    <section class="form-container">
        <h2 class="title">Inscription</h2>

        <!-- Affichage de l'erreur si présente -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form class="form" action="?cmd=user/valider-inscription" method="POST">
            <div class="input-group">
                <label for="emailField">Pseudonyme</label>
                <input type="text"
                       name="username"
                       id="emailField"
                       placeholder="Entrer votre pseudonyme"
                       value="${username}"
                       required />
            </div>
            <div class="input-group">
                <label for="passwordField">Mot de passe</label>
                <input type="password"
                       name="password"
                       id="passwordField"
                       placeholder="Entrer votre mot de passe"
                />
                <div class="forgot">
                    <a rel="noopener noreferrer" href="?cmd=user/login">Se connecter ?</a>
                </div>
            </div>

            <input type="hidden" name="csrfToken" value="${token}">

            <button type="submit" class="sign" id="submitBtn">
                S'inscrire
            </button>
        </form>
    </section>
</main>

<%@ include file="../footer.jsp" %>
<script src="js/all.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
