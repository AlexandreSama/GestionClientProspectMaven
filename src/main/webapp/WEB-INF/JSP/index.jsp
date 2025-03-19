<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | </title>
    <link rel="shortcut icon" href="<c:url value='../../images/favicon(1).ico'/>" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="<c:url value='/css/all.css' />" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<c:url value='/css/home.css' />" type="text/css">
</head>
<body>
<%@ include file="header.jsp" %>
<main>
    <section class="hero text-center">
        <div class="container container-hero">
            <h1 class="hero-title">Bienvenue sur Spark</h1>
            <p class="hero-subtitle">
                Gérez efficacement vos clients et prospects avec notre solution intuitive.
            </p>
            <a href="?cmd=contact" class="btn btn-primary hero-btn">
                Contactez-nous
            </a>
        </div>
    </section>

    <section class="features py-5">
        <div class="container">
            <h2 class="section-title text-center">Nos Fonctionnalités</h2>
            <div class="row">
                <div class="col-md-4">
                    <div class="card feature-card mb-4">
                        <div class="card-body text-center">
                            <i class="feature-icon"></i>
                            <h3 class="feature-title">Gestion de clients</h3>
                            <p class="feature-description">Organisez et suivez vos clients facilement</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card feature-card mb-4">
                        <div class="card-body text-center">
                            <i class="feature-icon"></i>
                            <h3 class="feature-title">Gestion de prospects</h3>
                            <p class="feature-description">Suivez l’évolution de vos prospects pour les convertir</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card feature-card mb-4">
                        <div class="card-body text-center">
                            <i class="feature-icon"></i>
                            <h3 class="feature-title">Automatisation</h3>
                            <p class="feature-description">Automatisez vos tâches pour plus d’efficacité</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="testimonials py-5 bg-light">
        <div class="container">
            <h2 class="section-title text-center">Ce qu'ils en disent</h2>
            <div class="row">
                <div class="col-md-4">
                    <div class="testimonial p-3">
                        <p class="testimonial-text">"Spark a révolutionné notre gestion client !"</p>
                        <p class="testimonial-author">- Woods Associates</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="testimonial p-3">
                        <p class="testimonial-text">"Un outil intuitif qui nous fait gagner un temps précieux"</p>
                        <p class="testimonial-author">- BigTech Inc.</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="testimonial p-3">
                        <p class="testimonial-text">"Nous recommandons vivement Spark pour optimiser nos ventes"</p>
                        <p class="testimonial-author">- Thunder LLC</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="newsletter py-5">
        <div class="container text-center">
            <h2 class="section-title">Inscrivez-vous à notre Newsletter</h2>
            <p>Restez informé des dernières nouveautés et offres.</p>
            <form action="#" method="post" class="newsletter-form">
                <div class="input-group">
                    <label>
                        <input type="email" name="newsletterEmail" class="form-control" placeholder="Votre email" required>
                    </label>
                    <button type="submit" class="btn btn-secondary">S'abonner</button>
                </div>
            </form>
        </div>
    </section>
</main>
<%@ include file="footer.jsp" %>
<script src="<c:url value='/JS/all.js'/>"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>