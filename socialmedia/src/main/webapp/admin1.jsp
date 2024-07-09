<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%@ page import="com.chainsys.socialmedia.model.UserReport" %>
<%@ page import="java.util.List" %>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context.getBean("userDao");
List<UserReport> userReport= userDao.getReport();
%>
<html>
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
</style>
</head>
<body>
	<table>
        <thead>
            <tr>
                <th>ID</th>
                <th>senderId</th>
                <th>reportedId</th>
                <th>report date</th>
                 <th>reason</th>
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
        </tr>  
        <%} %>      
        </tbody>
     </table>
</body>
</html>