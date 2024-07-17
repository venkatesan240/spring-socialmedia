<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%@ page import="com.chainsys.socialmedia.model.User" %>
<%@ page import="com.chainsys.socialmedia.model.Post" %>
<%@ page import="java.util.List" %>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context.getBean("userDao");
	List<User> users=userDao.getUsers();
	List<Post> posts=userDao.getAllPosts();
%>
<!DOCTYPE html>
<html lang="en">
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
 .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            background-color: #333;
            color: #f2f2f2;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }
        .back-button:hover {
            background-color: #ddd;
            color: black;
        }
</style>
</head>
<body>
<a href="adminmain.jsp" class="back-button">Back</a>
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
                        <form action="delete" method="post">
                       <input type="hidden" name="userid" value="<%= user.getUserId() %>">
                       <input type="submit" value="block">
                   </form>
                    </td>
                </tr>
           <%} %>
        </tbody>
    </table>
    <table>
  	<thead>
  	<tr>
 	<th>Post Id</th>
 	<th>user Id</th>
 	<th>description</th>
 	<th>post</th>
 	<th>User Name</th>
 	<th>content</th>
 	<th>Action</th>
  	</tr>
  	</thead>
  	<tbody>
  	 <%
        if(posts != null)
            for (Post post : posts) {
        %>
  	<tr>
  	<td><%= post.getId() %></td>
  	<td><%= post.getUserId() %></td>
  	<td><%= post.getDescription() %></td>
  	<td><%= post.getImage() %></td>
  	<td><%= post.getUserName() %></td>
  	<td><%= post.getContentType() %></td>
  	<td> <form action="deletepost" method="post">
                       <input type="hidden" name="postid" value="<%= post.getId() %>">
                       <input type="submit" value="delete">
                   </form></td>
  	</tr>
  	<%} %>
  	</tbody>
    </table>
</body>
</html>
