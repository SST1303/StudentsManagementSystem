document.addEventListener("DOMContentLoaded", () => {

    const name = document.getElementById("name");

    const email = document.getElementById("email");

    const password = document.getElementById("password");

    name.addEventListener("input", () => {

        name.value = name.value.replace(/\s+/g, " ");

    });

    email.addEventListener("blur", () => {

        email.value = email.value.trim().toLowerCase();

    });

    password.addEventListener("input", () => {

        if(password.value.length < 6){

            password.style.borderColor = "#dc3545";

        }else{

            password.style.borderColor = "#198754";

        }

    });

});