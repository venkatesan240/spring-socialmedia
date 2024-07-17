<%@ page import="java.util.List" %>
<%@ page import="com.chainsys.socialmedia.model.User" %>
<% 
	List<User> users=(List<User>)request.getAttribute("users");
	for(User user:users){
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>search users</title>
</head>
<body>
<form action="search" method="post">
<input class="input" type="text" name="search" value="enter to search">
<button type="submit"><i class="fa-solid fa-magnifying-glass nav-icon"></i></button>
<div class="card" onclick="javascript:window.location='${pageContext.request.contextPath}/viewmessage?receiverId=<%= user.getUserId() %>';">
                <div class="card-body">
					<div class="profile">
						 <span><%=user.getProfile() %><%=user.getFirstName() %></span>
					</div>
                </div>
</div>
</form>
<%} %>
</body>
</html>