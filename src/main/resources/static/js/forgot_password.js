document.addEventListener("DOMContentLoaded", () => {

    const email = document.getElementById("email");

    email.addEventListener("focus", () => {

        email.style.transition = "0.3s";

    });

});