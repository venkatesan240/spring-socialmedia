<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%
    String alertMessage = (String) request.getAttribute("alert");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
	<script type="text/javascript">
        window.onload = function() {
        alertMessage = "<%= alertMessage != null ? alertMessage : "" %>";
            if (alertMessage.trim() !== "") {
                alert(alertMessage);
            }
        }
    </script>
</head>
<style>

        .form-container {
            background-color: #fff;
            padding: 10px;
            border-radius: 15px;
            box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
            margin-left:410px;
            margin-top:10px;
            margin-bottom:10px;
        }

        .form-container h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }

        .form-container span {
            display: block;
            margin-bottom: 15px;
            text-align: left;
        }

        .form-container label {
            display: block;
            margin-bottom: 5px;
            font-size: 14px;
            color: #555;
        }

        .form-container input[type="text"],
        .form-container input[type="email"],
        .form-container input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            color: #333;
        }

        .form-container input[type="file"] {
            padding: 5px;
        }

        .form-container button {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 10px;
            font-size: 16px;
            font-weight: bold;
            color: #fff;
            background-color: #3e9852;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
        }

        .form-container button:hover {
           background-color: #7db87d;
            transform: scale(1.05);
        }
</style>
<body>
<%@include file="header.jsp" %>
	<div class="form-container">
        <h1>Profile Update</h1>
        <form action="UpdateProfile" method="post" enctype="multipart/form-data">
            <span>
                <label for="first-name">First Name:</label>
                <input type="text" id="first-name" name="first-name" value="<%= user.getFirstName() %>" required>
            </span>
            <span>
                <label for="last-name">Last Name:</label>
                <input type="text" id="last-name" name="last-name" value="<%= user.getLastName() %>" required>
            </span>
            <span>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
            </span>
            <span>
                <label for="profile-image">Set Profile:</label>
                <input type="file" id="profile-image" name="profile-image" accept="image/*">
            </span>
            <button type="submit">Update Profile</button>
        </form>
    </div>
</body>
</html>