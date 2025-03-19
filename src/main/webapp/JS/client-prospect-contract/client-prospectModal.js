const entityLinks = document.querySelectorAll('.entity-link');
let coordonnes;
let map;
let apiMeteoURL = "https://www.infoclimat.fr/public-api/gfs/json?_ll="
let apiMeteoAuth = "&_auth=ARtUQwR6VHZXegYxBnAGLwJqDzpZL1B3BHgEZw5rUC0FblEwB2dWMF8xWicCLQE3Un8PbA02ADADaAF5CHpUNQFrVDgEb1QzVzgGYwYpBi0CLA9uWXlQdwRmBGsOa1" +
    "AtBWRRNgdlVipfNlo9AiwBNFJhD2cNLQAnA2EBYQhhVDYBYlQ5BGBUNlc" +
    "%2BBmMGKQYtAjcPaFk1UG8EZwRrDmVQYQVvUTMHZFY0XzVaPwIsATVSZw9vDTsAPgNpAW8IZVQoAX1USQQUVCtXeAYmBmMGdAIsDzpZOFA8&_c=4781c99121f14afc04f949f663cbe670"

entityLinks.forEach(link => {
    link.addEventListener('click', async () => {
        // Récupère les data-* du lien cliqué
        const raison = link.getAttribute('data-raison') || '';
        const email = link.getAttribute('data-email') || '';
        const phone = link.getAttribute('data-phone') || '';

        // Champs spécifiques "client"
        const chiffreAffaire = link.getAttribute('data-chiffreaffaire') || '';
        const nbrEmploye = link.getAttribute('data-nbremploye') || '';

        // Champs spécifiques "prospect"
        const prospection = link.getAttribute('data-prospection') || '';
        const interest = link.getAttribute('data-interest') || '';

        // Champs communs
        const localisation = link.getAttribute('data-localisation') || '';

        // Injecte les infos dans la modale
        // ATTENTION : selon la page, tous les spans n'existent pas forcément.
        // On vérifie que l'élément existe avant de le remplir.
        const modalRaisonSociale = document.getElementById('modalRaisonSociale');
        if (modalRaisonSociale) modalRaisonSociale.textContent = raison;

        const modalEmail = document.getElementById('modalEmail');
        if (modalEmail) modalEmail.textContent = email;

        const modalTelephone = document.getElementById('modalTelephone');
        if (modalTelephone) modalTelephone.textContent = phone;

        const modalChiffreAffaire = document.getElementById('modalChiffreAffaire');
        if (modalChiffreAffaire) modalChiffreAffaire.textContent = chiffreAffaire;

        const modalNbrEmploye = document.getElementById('modalNbrEmploye');
        if (modalNbrEmploye) modalNbrEmploye.textContent = nbrEmploye;

        const modalDateProspection = document.getElementById('modalDateProspection');
        if (modalDateProspection) modalDateProspection.textContent = prospection;

        const modalInteress = document.getElementById('modalInteress');
        if (modalInteress) modalInteress.textContent = interest;

        const modalAdress = document.getElementById('modalAdresse');
        if (modalAdress) modalAdress.textContent = localisation;

        await getCoordinatedByAdress(localisation).then(r => {
            if (map !== undefined) {
                map.remove();
            }
            map = L.map('modalLocalisation').setView(r, 13);
            L.tileLayer(`https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png`, {
                maxZoom: 19,
                attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }).addTo(map);
            const marker = L.marker(r).addTo(map);
            marker.bindPopup("<b>" + raison + "</b>").openPopup();
            coordonnes = r;
        })

        await getMeteoByCoordinates(coordonnes).then(r => {
            const modalMeteo = document.getElementById('modalMeteo');
            if (modalMeteo) modalMeteo.textContent = r;
        })

    });
});

async function getCoordinatedByAdress(adress) {
    let coordinates;

    await fetch("https://api-adresse.data.gouv.fr/search/?q=" + adress)
        .then(async res => {
            await res.json().then(data => {
                coordinates = [data.features[0].geometry.coordinates[1], data.features[0].geometry.coordinates[0]];
            })
        })

    return coordinates;
}

async function getMeteoByCoordinates(coordinates) {
    let meteo;

    await fetch(apiMeteoURL + coordinates + apiMeteoAuth)
        .then(async res => {
            await res.json().then(async data => {
                await transformMeteoData(data).then(meteoData => {
                    meteo = meteoData;
                })
            })
        })
    return meteo;
}

async function transformMeteoData(meteo) {
    const now = new Date();

    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    let date = `${year}-${month}-${day}`;

    const currentHour = now.getHours();
    let targetHour;

    if (currentHour < 13) {         // Entre 00h et 12h59 → météo de 6h
        targetHour = "07:00:00";
    } else if (currentHour < 19) {  // Entre 13h et 18h59 → météo de 13h
        targetHour = "13:00:00";
    } else {                        // Entre 19h et 23h59 → météo de 19h
        targetHour = "19:00:00";
    }

    let fullDate = date + ' ' + targetHour

    const temperature = (meteo[fullDate].temperature["2m"] - 273.15).toFixed();
    const pluie = meteo[fullDate].pluie > 0 ? "risque de pluie" : "pas de pluie";
    const risqueNeige = meteo[fullDate].risque_neige === "oui" ? "possibles chutes de neige" : "pas de possible chute de neige";
    const vent = meteo[fullDate].vent_moyen["10m"];
    const ventTexte = vent > 20 ? `vent fort de ${vent.toFixed()} km/h` : `vent faible de ${vent.toFixed()} km/h`;
    const nebulosite = meteo[fullDate].nebulosite.totale;
    let ciel;

    if (nebulosite < 20) {
        ciel = "ciel dégagé";
    } else if (nebulosite < 50) {
        ciel = "quelques nuages";
    } else if (nebulosite < 80) {
        ciel = "ciel nuageux";
    } else {
        ciel = "ciel couvert";
    }

    return `Il fait ${temperature}°C, ${pluie}, ${risqueNeige}, ${ventTexte}, avec un ${ciel}.`;
}
