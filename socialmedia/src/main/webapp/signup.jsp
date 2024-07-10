<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<title>Register</title>
<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700&family=Ubuntu:wght@300;400;500&display=swap');
* {
    padding: 0;
    margin: 0;
    outline: 0;
    text-decoration: none;
}

body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #fafafa;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

main {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    max-width: 800px;
}

.phoneImg {
    max-width: 500px;
    margin-right: 40px;
}

.form-div {
    max-width: 400px;
    width: 100%;
}

.form-div form {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.instaLogo {
    max-width: 150px;
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

#toggle-password {
    align-self: flex-start;
    margin-bottom: 10px;
}

.login-btn {
    width: 100%;
    background-color: #0095f6;
    color: white;
    border: none;
    padding: 10px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    margin-bottom: 20px;
}

.login-btn:hover {
    background-color: #0077cc;
}

.error-message {
    color: red;
    font-size: 14px;
    margin-bottom: 10px;
    text-align: center;
}

.error {
    color: red;
    font-size: 14px;
    margin-bottom: 10px;
    text-align: center;
}

.get-app {
    text-align: center;
    margin-top: 20px;
}

.store {
    display: flex;
    justify-content: center;
    gap: 10px;
}

#play, #microsoft {
    max-width: 100px;
    cursor: pointer;
}

#play:hover, #microsoft:hover {
    opacity: 0.8;
}


</style>
</head>
<body>
 <main class="main">
        <img src="img/social-media-5187243_1280.png.jpg" alt="instagram-logo-illustration.png" class="phoneImg">	
        <div class="form-div">
            <form action="signup" method="post" onsubmit="return validatePassword()">
                <img class="instaLogo" src="img/connect-high-resolution-logo-black.png" alt="logo">
                <input type="text" class="input" name="first-name" placeholder="first name" pattern="[a-zA-Z]{5,}"title="First name should contain only letters" required>
                <input type="text" class="input" name="last-name" placeholder="last name" pattern="[a-zA-Z]{1,}" title="Last name should contain only letters"  required>
                <input type="text" class="input" name="email" placeholder="email" required>
                 <input type="password" class="input" id="password" name="password" pattern="[a-zA-Z0-9@#]{4,}" title="Password must be at least 4 characters long" required placeholder="Password">
        <input type="password" class="input" id="confirm-password" name="confirm-password" pattern="[a-zA-Z0-9@#]{4,}" title="Password must be at least 4 characters long" required placeholder="Confirm Password">
        <input type="checkbox" id="toggle-password"> Show Password
        <div id="password-error" class="error-message"></div>
                <button class="login-btn">Register</button>
<% if (request.getAttribute("error") != null) { %>
        <div class="error">
            <%= request.getAttribute("error") %>
        </div>
    <% } %>

    <script type="text/javascript">
        window.onload = function() {
            var alertMessage = "<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>";
            if (alertMessage.trim() !== "") {
                alert(alertMessage);
            }
        }
    </script>
            </form>
            <div class="get-app">
                <p>Get the app</p>
                <div class="store">
                    <img id="play" src="img/playStore.png" alt="">
                    <img id="microsoft" src="img/getMicrosoft.png" alt="">
                </div>
            </div>
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
   </main>
</body>
</html>