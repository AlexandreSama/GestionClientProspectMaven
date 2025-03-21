<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Accueil</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
    <link rel="stylesheet" href="css/home.css">
</head>
<body>
<%@ include file="header.jsp" %>

<!-- Toast Container pour les messages -->
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <!-- Toast pour la réussite de connexion -->
    <c:if test="${not empty sessionScope.success}">
        <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <svg class="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#007aff"></rect>
                </svg>
                <strong class="me-auto">Succès</strong>
                <small>À l'instant</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                    ${sessionScope.success}
            </div>
        </div>
    </c:if>
    <!-- Toast pour la déconnexion -->
    <c:if test="${not empty requestScope.logout}">
        <div id="logoutToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
                <svg class="bd-placeholder-img rounded me-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#ff5252"></rect>
                </svg>
                <strong class="me-auto">Déconnexion</strong>
                <small>À l'instant</small>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                    ${logout}
            </div>
        </div>
    </c:if>
</div>

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

<script src="node_modules/bootstrap/dist/js/bootstrap.bundle.js"></script>
<script src="js/all.js"></script>
<!-- Script pour afficher automatiquement les toasts -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const toastElList = [].slice.call(document.querySelectorAll('.toast'));
        toastElList.forEach(function (toastEl) {
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        });
    });
</script>
</body>
</html>
