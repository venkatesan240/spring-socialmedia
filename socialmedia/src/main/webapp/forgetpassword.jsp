<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
/* General Styles */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f2f2f2;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.reset-password-container {
    background-color: white;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 400px;
}

.reset-password-container h2 {
    text-align: center;
    margin-bottom: 20px;
}

.input {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-sizing: border-box;
}

.error-message {
    color: red;
    font-size: 14px;
    margin-bottom: 10px;
    text-align: center;
}

.checkbox-container {
    display: flex;
    align-items: center;
    margin-bottom: 20px;
}

#toggle-password {
    margin-right: 10px;
}

.submit-btn {
    width: 100%;
    background-color: #0095f6; /* Adjust button color as needed */
    color: white;
    border: none;
    padding: 10px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.submit-btn:hover {
    background-color: #0077cc; /* Darker color on hover */
}
</style>
</head>
<body>
<div class="reset-password-container">
        <form action="resetpassword" method="post" onsubmit="return validatePassword()">
            <h2>Reset Password</h2>
            <input type="text" class="input" name="email" placeholder="Email" required>
            <input type="password" class="input" id="password" name="password" required placeholder="Password">
            <input type="password" class="input" id="confirm-password" name="confirm-password" required placeholder="Confirm Password">
            <div id="password-error" class="error-message"></div>
            <div class="checkbox-container">
                <input type="checkbox" id="toggle-password"> Show Password
            </div>
            <button type="submit" class="submit-btn">Submit</button>
        </form>
    </div>
    <script>
        function validatePassword() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirm-password").value;
            var errorDiv = document.getElementById("password-error");

            if (password !== confirmPassword) {
                errorDiv.textContent = "Passwords do not match. Please try again.";
                return false;
            } else {
                errorDiv.textContent = "";
                return true;
            }
        }
    </script>
    <script>
        const togglePassword = document.querySelector('#toggle-password');
        const password = document.querySelector('#password');
        const confirmPassword = document.querySelector('#confirm-password');

        togglePassword.addEventListener('change', function (e) {
            const type = e.target.checked ? 'text' : 'password';
            password.type = type;
            confirmPassword.type = type;
        });
    </script>
</body>
</body>
</html>