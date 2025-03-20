<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Spark est un outil pour gérer des clients et des prospects en ligne">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="index, follow">
    <title>Spark | Liste des clients</title>
    <link rel="shortcut icon" href="images/favicon(1).ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/all.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<main class="container my-5">
    <h1
            class="mb-4"
            style="font-family: 'Poppins', sans-serif; color: #4F46E5; font-weight: 700;"
    >
        Liste des clients
    </h1>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Raison Sociale</th>
                <th scope="col">Email</th>
                <th scope="col">Numéro de téléphone</th>
                <th scope="col">Numéro de rue</th>
                <th scope="col">Nom de rue</th>
                <th scope="col">Code Postal</th>
                <th scope="col">Ville</th>
                <th scope="col">Chiffre d'Affaire</th>
                <th scope="col">Nombre d'employé</th>
                <th scope="col">Contrats</th>
                <th scope="col" style="min-width:200px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <!-- Exemple de ligne Client -->
            <tr>
                <th scope="row">1</th>
                <td>
                    <!-- Lien qui ouvre la modale (même classe pour client ou prospect) -->
                    <a href="#"
                       class="text-decoration-none entity-link"
                       data-bs-toggle="modal"
                       data-bs-target="#clientModal"

                       data-raison="Shifty Corps"
                       data-email="client1@gmail.com"
                       data-phone="06 12 34 86 78"
                       data-chiffreAffaire="50000"
                       data-nbrEmploye="50"
                       data-localisation="73 Rue Chanzy, 51800 Sainte-Menehould"
                       data-meteo=""
                    >
                        Shifty Corps
                    </a>
                </td>
                <td>client1@gmail.com</td>
                <td>06 45 34 56 78</td>
                <td>73</td>
                <td>Rue Chanzy</td>
                <td>51800</td>
                <td>Sainte-Menehould</td>
                <td>50000</td>
                <td>50</td>
                <td>
                    <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#contractsModal">Voir les contrats</button>
                </td>
                <td>
                    <a
                            href="?cmd=clients/update"
                            class="btn btn-sm btn-primary"
                    >
                        Modifier
                    </a>
                    <a
                            href="?cmd=clients/delete"
                            class="btn btn-sm btn-danger ms-2"
                    >
                        Supprimer
                    </a>
                </td>
            </tr>

            <!-- Exemple de ligne Client -->
            <tr>
                <th scope="row">2</th>
                <td>
                    <a href="#"
                       class="text-decoration-none entity-link"
                       data-bs-toggle="modal"
                       data-bs-target="#clientModal"

                       data-raison="Hammer Inc."
                       data-email="client2@gmail.com"
                       data-phone="06 45 34 74 78"
                       data-chiffreAffaire="250000"
                       data-nbrEmploye="1500"
                       data-localisation="21 Av. du 3ème Rac, 89300 Joigny"
                       data-meteo=""
                    >
                        Hammer Inc.
                    </a>
                </td>
                <td>client2@gmail.com</td>
                <td>06 45 34 74 78</td>
                <td>21</td>
                <td>Av. du 3ème Rac</td>
                <td>89300</td>
                <td>Joigny</td>
                <td>250000</td>
                <td>1500</td>
                <td>
                    <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#contractsModal">Voir les contrats</button>
                </td>
                <td>
                    <a
                            href="?cmd=clients/update"
                            class="btn btn-sm btn-primary"
                    >
                        Modifier
                    </a>
                    <a
                            href="?cmd=clients/delete"
                            class="btn btn-sm btn-danger ms-2"
                    >
                        Supprimer
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="text-end mt-3">
        <a href="?cmd=clients/add" class="btn btn-success">
            Ajouter un client
        </a>
    </div>

    <!-- Modal Contrats -->
    <div class="modal fade" id="contractsModal" tabindex="-1" aria-labelledby="contractsModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="contractsModalLabel">Liste des Contrats</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
                </div>
                <div class="modal-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Identifiant</th>
                                <th>Libellé du contrat</th>
                                <th>Montant du contrat</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>1</td>
                                <td>Contrat A</td>
                                <td>1 000 €</td>
                                <td>
                                    <a href="../contract/formContract.html?action=edit&id=1&libelle=Contrat%20A&montant=1000" class="btn btn-sm btn-primary">Modifier</a>
                                    <button class="btn btn-sm btn-danger ms-2">Supprimer</button>
                                </td>
                            </tr>
                            <tr>
                                <td>2</td>
                                <td>Contrat B</td>
                                <td>2 500 €</td>
                                <td>
                                    <a href="../contract/formContract.html?action=edit&id=2&libelle=Contrat%20B&montant=2500" class="btn btn-sm btn-primary">Modifier</a>
                                    <button class="btn btn-sm btn-danger ms-2">Supprimer</button>
                                </td>
                            </tr>
                            <!-- Ajoutez d'autres lignes selon vos besoins -->
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Fermer</button>
                    <a href="../contract/formContract.html" class="btn btn-success">Ajouter un contrat</a>
                </div>
            </div>
        </div>
    </div>
</main>

<!-- Modal unique pour Clients / Prospects -->
<div class="modal fade" id="clientModal" tabindex="-1" aria-labelledby="clientModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="clientModalLabel">Détails</h1>
                <button
                        type="button"
                        class="btn-close"
                        data-bs-dismiss="modal"
                        aria-label="Close"
                ></button>
            </div>
            <div class="modal-body">
                <p><strong>Raison Sociale :</strong> <span id="modalRaisonSociale"></span></p>
                <p><strong>Email :</strong> <span id="modalEmail"></span></p>
                <p><strong>Numéro de téléphone :</strong> <span id="modalTelephone"></span></p>
                <p><strong>Chiffre d'affaire :</strong> <span id="modalChiffreAffaire"></span></p>
                <p><strong>Nombre d'employé :</strong> <span id="modalNbrEmploye"></span></p>
                <p><strong>Adresse :</strong> <span id="modalAdresse"></span></p>
                <p><strong>Géolocalisation :</strong> <span id="modalLocalisation"></span></p>
                <p><strong>Météo :</strong> <span id="modalMeteo"></span></p>
            </div>
            <div class="modal-footer">
                <button
                        type="button"
                        class="btn btn-secondary"
                        data-bs-dismiss="modal"
                >Fermer</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="js/all.js"></script>
<script src="js/client-prospect-contract/client-prospectModal.js"></script>
</body>
</html>
