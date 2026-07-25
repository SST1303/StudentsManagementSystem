document.addEventListener("DOMContentLoaded", () => {

    const userName =
        document.getElementById("userProfileName").textContent.trim();

    const avatar =
        document.getElementById("avatarBadge");

    if(userName){

        const words = userName.split(" ");

        let initials = "";

        words.forEach(word => {

            initials += word.charAt(0).toUpperCase();

        });

        avatar.textContent = initials.substring(0,2);

    }

});