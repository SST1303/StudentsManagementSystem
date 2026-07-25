document.addEventListener("DOMContentLoaded", function() {
    const mobileInput = document.getElementById("mobileNumber");
    const profileForm = document.getElementById("profileForm");

    mobileInput.addEventListener("input", function(e) {
        this.value = this.value.replace(/[^0-9]/g, '');
    });

    profileForm.addEventListener("submit", function(e) {
        const mobileValue = mobileInput.value.trim();
        
        if (mobileValue.length > 0 && mobileValue.length !== 10) {
            alert("Please enter a valid 10-digit mobile number.");
            e.preventDefault(); 
        }
    });
});