<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                    <c:if test="${empty sessionScope.user}">
                        <a href="?cmd=user/login" class="nav-item nav-link" id="listeClientBtn">Gérer vos clients</a>
                        <a href="?cmd=user/login" class="nav-item nav-link" id="listeProspectBtn">Gérer vos prospects</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.user}">
                        <a href="?cmd=clients/view" class="nav-item nav-link" id="listeClientBtn">Gérer vos clients</a>
                        <a href="?cmd=prospects/view" class="nav-item nav-link" id="listeProspectBtn">Gérer vos prospects</a>
                    </c:if>
                </div>
                <div class="navbar-nav ms-auto">
                    <!-- Version pour utilisateur non connecté -->
                    <c:if test="${empty sessionScope.user}">
                    <a href="?cmd=user/login" class="btn loginBtn" id="loginBtn">Se connecter</a>
                    </c:if>
                    <!-- Version pour utilisateur connecté, cachée par défaut -->
                    <c:if test="${not empty sessionScope.user}">
                    <div class="nav-item dropdown" id="userDropdownContainer">
                        <a class="nav-link dropdown-toggle btn loginBtn" href="#" id="userDropdown" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            Mon Compte
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <li><a class="dropdown-item" href="#">Mon Profil</a></li>
                            <li><a class="dropdown-item" href="?cmd=user/logout" id="disconnectBtn">Se déconnecter</a></li>
                        </ul>
                    </div>
                    </c:if>
                </div>
            </div>
        </div>
    </nav>
</header>
