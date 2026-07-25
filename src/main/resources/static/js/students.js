document.addEventListener("DOMContentLoaded", function () {

    // Success Message
    if (typeof successMessage !== 'undefined' && successMessage) {
        Swal.fire({
            title: 'Success!',
            text: successMessage,
            icon: 'success',
            confirmButtonColor: '#28a745'
        });
    }

    // Mobile Validation
    const mobileInput = document.getElementById("mobileNumber");
    const profileForm = document.getElementById("profileForm");

    if (mobileInput) {
        mobileInput.addEventListener("input", function () {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }

    if (profileForm && mobileInput) {
        profileForm.addEventListener("submit", function (e) {

            const mobileValue = mobileInput.value.trim();

            if (mobileValue.length > 0 && mobileValue.length !== 10) {

                Swal.fire({
                    title: 'Invalid Input',
                    text: 'Please enter a valid 10-digit mobile number.',
                    icon: 'warning',
                    confirmButtonColor: '#ffc107'
                });

                e.preventDefault();
            }

        });
    }

    // View Student Modal
    const viewModal = document.getElementById("viewStudentModal");

    if (viewModal) {

        viewModal.addEventListener("show.bs.modal", function (event) {

            const button = event.relatedTarget;

            const firstName = button.getAttribute("data-firstname") || "N/A";
            const middleName = button.getAttribute("data-middlename") || "-";
            const lastName = button.getAttribute("data-lastname") || "N/A";
            const email = button.getAttribute("data-email") || "N/A";
            const mobile = button.getAttribute("data-mobile") || "Not Added";
            const dob = button.getAttribute("data-dob") || "Not Added";
            const address = button.getAttribute("data-address") || "Not Added";

            document.getElementById("modalFirstName").innerText = firstName;
            document.getElementById("modalMiddleName").innerText = middleName;
            document.getElementById("modalLastName").innerText = lastName;
            document.getElementById("modalEmail").innerText = email;
            document.getElementById("modalMobile").innerText = mobile;
            document.getElementById("modalDob").innerText = dob;
            document.getElementById("modalAddress").innerText = address;

        });
    }
});

// Delete Student
function confirmDelete(id) {

    Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {

        if (result.isConfirmed) {
            window.location.href = "/students/delete/" + id;
        }
    });
}

// Print Student Details
function printStudentDetails(id) {
    window.open("/students/print/" + id, "_blank");
}