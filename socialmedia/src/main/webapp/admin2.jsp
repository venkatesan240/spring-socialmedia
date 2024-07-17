<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO" %>
<%@page import="com.chainsys.socialmedia.model.ReportPost" %>
<%@ page import="java.util.List" %>
<%
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context.getBean("userDao");
List<ReportPost> reportPost=userDao.getPostReport();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>view report details</title>
</head>
<body>
<a href="adminmain.jsp" class="back-button">Back</a>
	<table>
        <thead>
            <tr>
                <th>ID</th>
                <th>postId</th>
                <th>userId</th>
                <th>report date</th>
                 <th>post</th>
            </tr>
        </thead>
        <tbody>
         <%
        if(reportPost != null)
            for (ReportPost user : reportPost) {
        %>
        <tr>
        	<td><%= user.getId() %></td>
        	<td><%= user.getPostId() %></td>
        	<td><%= user.getUserId() %></td>
        	<td><%= user.getReportDate() %></td>
        	<td><%= user.getImage() %></td>
        </tr>  
        <%} %>      
        </tbody>
     </table>
</body>
</html>