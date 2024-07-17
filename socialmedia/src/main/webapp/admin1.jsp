<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%@ page import="com.chainsys.socialmedia.model.UserReport" %>
<%@ page import="com.chainsys.socialmedia.model.Message" %>
<%@page import="com.chainsys.socialmedia.model.ReportPost" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Base64" %>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context.getBean("userDao");
List<UserReport> userReport= userDao.getReport();
List<ReportPost> reportPost=userDao.getPostReport();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>view report details</title>
<style>
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
        .product-image {
        width: 100px; /* Adjust width as needed */
        height: auto; /* Maintain aspect ratio */
        border: 1px solid #ccc; /* Optional border */
        padding: 5px; /* Optional padding */
        box-shadow: 0 0 5px rgba(0,0,0,0.1); /* Optional shadow */
        border-radius: 5px; /* Optional rounded corners */
    }

    td {
        text-align: center; /* Center the image in the cell */
        vertical-align: middle; /* Vertically align the image in the middle of the cell */
    }
</style>
</head>
<body>
<a href="adminmain.jsp" class="back-button">Back</a>
	<table>
        <thead>
            <tr>
                <th>ID</th>
                <th>senderId</th>
                <th>reportedId</th>
                <th>report date</th>
                 <th>reason</th>
                 <th>content</th>
            </tr>
        </thead>
        <tbody>
         <%
        if(userReport != null)
            for (UserReport user : userReport) {
        %>
        <tr>
        	<td><%= user.getId() %></td>
        	<td><%= user.getSenderId() %></td>
        	<td><%= user.getReportedId() %></td>
        	<td><%= user.getReportDate() %></td>
        	<td><%= user.getReason() %></td>
        	<td><%= user.getContent() %></td>
        </tr>  
        <%} %>      
        </tbody>
     </table>
     <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>postId</th>
                <th>userId</th>
                <th>report date</th>
                <th>Reason</th>
                 <th>post</th>
            </tr>
        </thead>
        <tbody>
        <%
if (reportPost != null) {
    for (ReportPost user : reportPost) {
        String contentType = user.getContent();
%>
        <tr>
            <td><%= user.getId() %></td>
            <td><%= user.getPostId() %></td>
            <td><%= user.getUserId() %></td>
            <td><%= user.getReportDate() %></td>
            <td><%= user.getReason() %></td>
            <%
            if (contentType != null) {
                if (contentType.startsWith("image")) {
            %>
            <td><img class="product-image" src="data:<%= contentType %>;base64,<%= Base64.getEncoder().encodeToString(user.getImage()) %>" alt=""></td>
            <%
                } else if (contentType.startsWith("video")) {
                	System.out.println(contentType);
            %>
            <td>
                <video width="500" height="300" controls>
                    <source src="data:<%= contentType %>;base64,<%= Base64.getEncoder().encodeToString(user.getImage()) %>" type="<%= contentType %>">
                </video>
            </td>
            <%
                }
            }
            %>
        </tr>
<%
    }
}
%>
        </tbody>
     </table>
</body>
</html>