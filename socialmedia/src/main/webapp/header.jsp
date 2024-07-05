<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Base64" %>
<%@ page import="com.chainsys.socialmedia.model.User" %>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%
    String base64Image = "";
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    UserDAO userDAO = (UserDAO) context.getBean("userDao");
    User user = userDAO.getUserById((Integer) session.getAttribute("userid"));
    if (user != null && user.getProfile() != null) {
        base64Image = Base64.getEncoder().encodeToString(user.getProfile());
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Page</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"> 
<style>
@charset "ISO-8859-1";
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: sans-serif;
}
header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 5%;
    border-bottom: 1px solid gray;
    background-color: white;
    position: sticky;
    top: 0;
    z-index: 10;
}
header .logo img {
    height: 80px;
    object-fit: contain; 
}
header .search-box {
    flex: 1;
    max-width: 400px;
    display: flex;
    align-items: center;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-right: 20px;
}
header .search-box input[type="search"] {
    flex: 1;
    border: none;
    outline: none;
    padding: 5px;
    font-size: 14px;
}
header nav ul {
    display: flex;
    list-style: none;
    align-items: center;
}
header nav ul li {
    margin-right: 60px;
    padding: 5px;
}
header nav ul li a {
    display: flex;
    align-items: center;
}
header nav ul li a img, header nav ul li a i {
    width: 22px;
    height: 22px;
    object-fit: cover;
    color: rgb(39, 29, 29);
}
.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
    right: 0;
}
.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}
.dropdown-content a:hover {
    background-color: #f1f1f1;
}
.profile-icon:hover .dropdown-content {
    display: block;
}
img {
    border-radius: 50%;
}
.name {
    margin: 10px;
}
@media (max-width: 768px) {
    header {
        flex-direction: column;
        align-items: flex-start;
    }
    header .search-box {
        margin: 10px 0;
        width: 100%;
    }
    header nav ul {
        flex-direction: column;
        width: 100%;
    }
    header nav ul li {
        margin: 5px 0;
    }
</style>
</head>
<body>
<div class="insta">
    <header>
        <div class="logo">
            <img src="img/connect-high-resolution-logo-black.png" alt="insta">
        </div>
        <%
    String alert = (String) session.getAttribute("alert");
%>
        <script type="text/javascript">
        window.onload = function() {
        	var alertMessage = "<%= alert != null ? alert : "" %>";
            if (alertMessage.trim() !== "") {
                alert(alertMessage);
                session.removeAttribute("alert");
            }
        }
    </script>
       <nav>
        <ul>
        	<li><a href="search.jsp"><i class="fa-solid fa-magnifying-glass"></i></a></li>
            <li><a href="home.jsp"><i class="fa-solid fa-house nav-icon"></i></a></li>
            <li><a href="${pageContext.request.contextPath}/userlist"><i class="fa-solid fa-message nav-icon"></i></a></li>
            <li><a href="post.jsp"><i class="fa-solid fa-square-plus nav-icon"></i></a></li>
            <li><a href="#"><i class="fa-solid fa-heart nav-icon"></i></a></li>
            <li class="nav-item profile-icon">
                <a href="profile.jsp">
                    <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Profile">
                </a>
                <div class="dropdown-content">
                    <div class="name">
                        <a><%= user.getFirstName() + " " + user.getLastName() %></a>
                    </div>
                    <a href="logout">Logout</a>
                    <a href="profile.jsp">Profile</a>
                </div>
            </li>
        </ul>
    </nav>
    </header>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

