<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19/03/2025
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a href="?cmd=index" class="navbar-brand">Spark</a>
            <button
                    type="button"
                    class="navbar-toggler"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarCollapse"
                    aria-controls="navbarCollapse"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <div class="navbar-nav">
                    <a href="#" class="nav-item nav-link" id="listeClientBtn">Gérer vos clients</a>
                    <a href="#" class="nav-item nav-link" id="listeProspectBtn">Gérer vos prospects</a>
                </div>
                <div class="navbar-nav ms-auto">
                    <!-- Version pour utilisateur non connecté -->
                    <a href="user/login.jsp" class="btn loginBtn" id="loginBtn">Se connecter</a>

                    <!-- Version pour utilisateur connecté, cachée par défaut -->
                    <div class="nav-item dropdown d-none" id="userDropdownContainer">
                        <a class="nav-link dropdown-toggle btn loginBtn" href="#" id="userDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            Mon Compte
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <li><a class="dropdown-item" href="#">Mon Profil</a></li>
                            <li><a class="dropdown-item" href="#" id="disconnectBtn">Se déconnecter</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>
