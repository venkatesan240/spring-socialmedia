<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%@ page import="com.chainsys.socialmedia.model.User" %>
<%@ page import="java.util.List" %>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context.getBean("userDao");
	List<User> users=userDao.getUsers();
%>
<html>
<head>
    <title>Admin Page</title>
<style>
/* General styles */
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background-color: #f5f5f5;
}

/* Header and navigation */
header {
    background-color: #333;
    padding: 10px 0;
}

nav ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
}

nav ul li {
    margin: 0 15px;
}

nav ul li a {
    color: #fff;
    text-decoration: none;
    font-size: 20px;
}

nav ul li a:hover {
    color: #ff9800;
}

.nav-icon {
    margin-right: 5px;
}

/* Tables */
table {
    width: 80%;
    margin: 20px auto;
    border-collapse: collapse;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

table th, table td {
    padding: 12px 15px;
    border: 1px solid #ddd;
    text-align: left;
}

table th {
    background-color: #333;
    color: #fff;
}

table tr:nth-child(even) {
    background-color: #f2f2f2;
}

table tr:hover {
    background-color: #f1f1f1;
}

/* Forms */
form {
    display: inline;
}

input[type="submit"] {
    background-color: #ff9800;
    border: none;
    color: #fff;
    padding: 5px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    margin: 4px 2px;
    cursor: pointer;
    border-radius: 5px;
}

input[type="submit"]:hover {
    background-color: #e68a00;
}

</style>
</head>
<body>
<h2>Users</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
        if(users != null)
            for (User user : users) {
        %>
                <tr>
                    <td><%= user.getUserId() %></td>
                    <td><%= user.getFirstName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td>
                        <form action="Example1" method="post">
                       <input type="hidden" name="email" value="<%= user.getEmail() %>">
                       <input type="hidden" value="delete" name="action">
                       <input type="submit" value="delete">
                   </form>
                    </td>
                </tr>
           <%} %>
        </tbody>
    </table>
    <h2>Messages</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Sender</th>
                <th>Receiver</th>
                <th>Content</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td>${message.id}</td>
                    <td>${message.sender.name}</td>
                    <td>${message.receiver.name}</td>
                    <td>${message.content}</td>
                    <td>
                        <a href="<c:url value='/deleteMessage?id='${message.id}"/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
