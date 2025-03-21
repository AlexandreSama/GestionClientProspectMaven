<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | </title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<main class="container my-5">
    <h1 class="mb-4" style="font-family: 'Poppins, sans-serif',serif; color: #4F46E5; font-weight: 700;">
        Contactez-nous
    </h1>
    <form action="#" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Nom</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Votre nom" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" placeholder="Votre email" required>
        </div>
        <div class="mb-3">
            <label for="subject" class="form-label">Sujet</label>
            <input type="text" class="form-control" id="subject" name="subject" placeholder="Objet du message" required>
        </div>
        <div class="mb-3">
            <label for="message" class="form-label">Message</label>
            <textarea class="form-control" id="message" name="message" rows="5" placeholder="Votre message"
                      required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Envoyer</button>
    </form>
</main>
<%@ include file="footer.jsp" %>
<script src="js/all.js"></script>
<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.js"></script>
</body>
</html>