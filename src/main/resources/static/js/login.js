document.addEventListener("DOMContentLoaded", function () {
    
    
    if (typeof isError !== 'undefined' && isError) {
        Swal.fire({
            icon: 'error',
            title: 'Login Failed',
            text: 'Invalid email address or password!',
            confirmButtonColor: '#3085d6'
        });
    }

    
    if (typeof isLogout !== 'undefined' && isLogout) {
        Swal.fire({
            icon: 'info',
            title: 'Logged Out',
            text: 'You have been successfully logged out.',
            timer: 2500,
            showConfirmButton: false
        });
    }

  
    if (typeof isRegSuccess !== 'undefined' && isRegSuccess) {
        Swal.fire({
            icon: 'success',
            title: 'Registration Successful',
            text: 'Please login with your credentials.',
            timer: 3000,
            showConfirmButton: false
        });
    }

   
    if (typeof isResetSuccess !== 'undefined' && isResetSuccess) {
        Swal.fire({
            icon: 'success',
            title: 'Password Updated',
            text: 'Your password has been changed successfully! Please login.',
            timer: 3000,
            showConfirmButton: false
        });
    }
});