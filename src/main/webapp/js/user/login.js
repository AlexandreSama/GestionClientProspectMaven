const emailField = document.querySelector('#emailField');
const passwordField = document.querySelector('#passwordField');
const submitBtn = document.querySelector('#submitBtn');

const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$/;
const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

submitBtn.addEventListener('click', e => {
    e.preventDefault();

    const email = emailField.value;
    const password = passwordField.value;

    if(emailRegex.test(email) && passwordRegex.test(password)) {

        localStorage.setItem("email", email);
        localStorage.setItem("isLoggedIn", "yes");
        localStorage.setItem("role", "Membre")

        location.href="/GestionClientProspect/index.html";
        location.href="../../../index.html";
    }
})