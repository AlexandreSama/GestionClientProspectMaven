<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Connexion</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
</head>
<body>
<%@ include file="../header.jsp" %>
<main>
    <section class="form-container">
        <h2 class="title">Connexion</h2>

        <form class="form" action="#" method="POST">
            <div class="input-group">
                <label for="emailField">Email</label>
                <input
                        type="email"
                        name="username"
                        id="emailField"
                        placeholder="Entrer votre email"
                        required
                />
            </div>
            <div class="input-group">
                <label for="passwordField">Mot de passe</label>
                <input
                        type="password"
                        name="password"
                        id="passwordField"
                        placeholder="Entrer votre mot de passe"
                        required
                />
                <div class="forgot">
                    <a rel="noopener noreferrer" href="#">Mot de passe oublié?</a>
                </div>
            </div>
            <button type="submit" class="sign" id="submitBtn">
                Se connecter
            </button>
        </form>
    </section>
</main>
<%@ include file="../footer.jsp" %>
<script src="js/all.js"></script>
<script src="js/user/login.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
