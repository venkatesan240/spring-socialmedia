<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.chainsys.socialmedia.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
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
    background-color: #fafafa; /* Adjust background color as needed */
}

.main {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.phoneImg {
    max-width: 600px;
    margin-right: 40px;
}

.form-div {
    background-color: white;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 400px;
}

.instaLogo {
    width: 200px;
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

.login-btn {
    width: 100%;
    background-color: #0095f6; /* Adjust button color as needed */
    color: white;
    border: none;
    padding: 10px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

.login-btn:hover {
    background-color: #0077cc; /* Darker color on hover */
}

.forgetpassword {
    background-color: transparent;
    border: none;
    padding: 0;
    margin: 0;
}

.forgetpassword a {
    text-decoration: none;
    color: #262626; /* Link color */
    font-size: 14px;
    cursor: pointer;
}

.forgetpassword a:hover {
    text-decoration: underline;
}

.sign-up {
    text-align: center;
    margin-top: 20px;
    font-size: 14px;
}

.sign-link {
    color: #0095f6; /* Link color */
    text-decoration: none;
}

.sign-link:hover {
    text-decoration: underline;
}

.get-app {
    margin-top: 20px;
    text-align: center;
}

.store {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 10px;
}

#play, #microsoft {
    width: 120px;
    margin: 0 10px;
}

    </style>
</head>
<body>
    <% 
    if(session == null){
        response.sendRedirect("signin.jsp");
    }
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setHeader("Expires", "0"); // Proxies.
    %>
    <main class="main">
        <img src="img/social-media-5187243_1280.png.jpg" alt="" class="phoneImg">
        <div class="form-div">
            <form id="login-form" action="signin" method="post">
                <img class="instaLogo" src="img/connect-high-resolution-logo-black.png" alt="logo">
                <input type="text" class="input" name="email" placeholder="phone number, username, or email" required>
                <input type="password" class="input" name="password" required placeholder="Password">
                <button type="submit" class="login-btn">Log In</button>
                <button type="button" id="forgot-password" class="forgetpassword">
                    <a href="forgetpassword.jsp">Forgot Password?</a>
                </button>

                <% 
                // Check if there's an error message
                String errorMessage = (String) request.getAttribute("error");
                if (errorMessage != null) {
                %>
                <script type="text/javascript">
                    alert("<%= errorMessage %>");
                </script>
                <%
                }
                %>
                <%
                // Check if there's a success message
                String Message = (String) request.getAttribute("Message");
                if (Message != null) {
                %>
                <script type="text/javascript">
                    alert("<%= Message %>");
                </script>
                <%
                }
                %>
                <c:if test="${not empty rmsg}">
                    <div class="alert alert-success">${rmsg}</div>
                </c:if>
                <div class="sign-up">
                    <p>Don't have an account? <a href="signup.jsp" class="sign-link">Sign up</a></p>
                </div>
                <div class="get-app">
                    <p>Get the app</p>
                    <div class="store">
                        <img id="play" src="img/playStore.png" alt="">
                        <img id="microsoft" src="img/getMicrosoft.png" alt="">
                    </div>
                </div>
            </form>
        </div>
    </main>
</body>
</html>