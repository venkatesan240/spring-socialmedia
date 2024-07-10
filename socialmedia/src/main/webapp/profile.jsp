<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.Base64" %>
<%@ page import="com.chainsys.socialmedia.model.User" %>
<%@ page import="com.chainsys.socialmedia.dao.*" %>
<%
	String base64Image = "";
    ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    UserDAO userDao = (UserDAO) context.getBean("userDao");
    User user = userDao.getUserById((Integer)(session.getAttribute("userid")));
    if (user != null && user.getProfile() != null) {
        base64Image = Base64.getEncoder().encodeToString(user.getProfile());
    }
%> 
<!DOCTYPE html>
<html lang="en">
<style>
/* Profile Page Styles */
 body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .profile-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 300px;
        }

        .profile-image {
            border-radius: 50%;
            width: 110px;
            height: 110px;
            object-fit: cover;
            margin-bottom: 20px;
        }

        .profile-info p {
            margin: 10px 0;
            font-size: 16px;
            color: #333;
        }

        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px 5px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            transition: background-color 0.3s, transform 0.3s;
        }

        .btn:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        .edit-profile {
            background-color: #28a745;
        }

        .edit-profile:hover {
            background-color: #218838;
        }

        .logout {
            background-color: #dc3545;
        }

        .logout:hover {
            background-color: #c82333;
        }
</style>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
      <div class="profile-container">
      <h1>User Profile</h1>
        <img src="data:image/jpeg;base64,<%= base64Image %>" alt="Profile" class="profile-image">
        <div class="profile-info">
            <p>Username: <%= user.getFirstName() %></p>
            <p>Email: <%= user.getEmail() %></p>
        </div>
        <a href="update.jsp" class="btn edit-profile">Edit profile</a>
        <a href="logout" class="btn logout">Logout</a>
    </div>
</body>
</html>