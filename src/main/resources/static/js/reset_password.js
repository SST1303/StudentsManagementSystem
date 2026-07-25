const form = document.getElementById("resetForm");

form.addEventListener("submit", function (event) {

    const password = document.getElementById("password").value;

    const confirmPassword = document.getElementById("confirmPassword").value;

    const error = document.getElementById("passwordError");

    if (password !== confirmPassword) {

        event.preventDefault();

        error.classList.remove("d-none");

    } else {

        error.classList.add("d-none");

    }

});