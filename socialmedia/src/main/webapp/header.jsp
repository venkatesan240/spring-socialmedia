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
/* General styles */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f0f2f5;
}

/* Header container */
.insta header {
    background-color: #fff;
    padding: 10px 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
}

/* Logo styles */
.logo img {
    height: 80px;
}

/* Navigation bar styles */
nav ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    display: flex;
    align-items: center;
}

nav ul li {
    margin: 0 40px;
}

nav ul li a {
    color: #333;
    text-decoration: none;
    font-size: 18px;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
}

nav ul li a:hover {
    color: #007bff;
}

nav ul li a i {
    font-size: 20px;
}

/* Profile icon styles */
.profile-icon {
    position: relative;
}

.profile-icon img {
    border-radius: 50%;
    width: 35px;
    height: 35px;
    object-fit: cover;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #fff;
    min-width: 150px;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    z-index: 5;
    right: 0;
    border-radius: 5px;
}

.profile-icon:hover .dropdown-content {
    display: block;
}

.dropdown-content a {
    color: #333;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    font-size: 14px;
}

.dropdown-content a:hover {
    background-color: #f1f1f1;
}

.dropdown-content .name {
    padding: 12px 16px;
    font-weight: bold;
    border-bottom: 1px solid #ddd;
}

.nav-icon {
    margin: 0;
}
nav {
    position: relative;
}

ul {
    list-style: none;
    margin: 0;
    padding: 0;
}

li {
    display: inline-block;
    position: relative;
}

.nav-icon {
    font-size: 24px;
    cursor: pointer;
}

.dropdown {
    display: none;
    position: absolute;
    top: 100%;
    left: 0;
    background-color: white;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    z-index: 1;
    min-width: 160px;
}

.dropdown li {
    display: block;
}

.dropdown li a {
    display: block;
    padding: 8px 16px;
    text-decoration: none;
    color: black;
}

.dropdown li a:hover {
    background-color: #ddd;
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
          <!--  <a href="" id="search-icon"><i class="fa-solid fa-magnifying-glass nav-icon"></i></a> -->
                <ul class="dropdown" id="dropdown-menu">
                    <li><a href="#">User 1</a></li>
                    <li><a href="#">User 2</a></li>
                    <li><a href="#">User 3</a></li>
                </ul>
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
<script >
document.getElementById('search-icon').addEventListener('click', function(event) {
    event.preventDefault();
    var searchContainer = document.getElementById('search-container');
    if (searchContainer.style.display === 'block') {
        searchContainer.style.display = 'none';
    } else {
        searchContainer.style.display = 'block';
    }
});

document.getElementById('search-input').addEventListener('input', function() {
    var searchQuery = this.value;
    if (searchQuery.length > 0) {
        fetch('/api/search', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                name: searchQuery
            })
        })
        .then(response => response.json())
        .then(data => {
            var dropdownMenu = document.getElementById('dropdown-menu');
            dropdownMenu.innerHTML = '';
            data.forEach(user => {
                var listItem = document.createElement('li');
                var link = document.createElement('a');
                link.href = '#';
                link.textContent = user.name; 
                listItem.appendChild(link);
                dropdownMenu.appendChild(listItem);
            });
        });
    } else {
        document.getElementById('dropdown-menu').innerHTML = '';
    }
});

document.addEventListener('click', function(event) {
    var isClickInside = document.getElementById('search-icon').contains(event.target) || document.getElementById('search-container').contains(event.target);
    if (!isClickInside) {
        document.getElementById('search-container').style.display = 'none';
    }
});

</script>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>

