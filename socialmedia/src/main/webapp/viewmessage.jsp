<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="java.util.ArrayList"%>
<%@ page import="com.chainsys.socialmedia.dao.UserDAO"%>
<%@ page import="com.chainsys.socialmedia.model.Message" %>
<%@ page import="java.util.List" %>
<%
String base64Image1 = "";
int senderId = Integer.parseInt(session.getAttribute("userid").toString());
int receiverId = (int)request.getAttribute("receiverId");
System.out.println(receiverId);ApplicationContext context1 = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
UserDAO userDao = (UserDAO) context1.getBean("userDao");
User user1 = userDao.getUserById(receiverId);
if (user1 != null && user1.getProfile() != null) {
    base64Image1 = Base64.getEncoder().encodeToString(user1.getProfile());
}
Message msg = new Message();
msg.setSenderId(senderId);
msg.setReceiverId(receiverId);
List<Message> messages = new ArrayList<>();
	messages = userDao.getMessage(msg);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
<style>
.type_msg {
    padding: 20px;
    background-color: #f6f6f6;
}

.input_msg_write {
    width: 100%;
    position: relative;
}

.input_msg_write input[type="text"] {
    width: calc(100% - 40px); /* Adjust the width as needed */
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    outline: none;
}

.input_msg_write button {
    position: absolute;
    right: 0;
    top: 0;
    width: 40px;
    height: 100%;
    border: none;
    background-color: transparent;
    cursor: pointer;
}

.input_msg_write button i {
    font-size: 20px;
    color: #007bff; /* Adjust the color as needed */
}

.input_msg_write button:hover i {
    color: #0056b3; /* Adjust the hover color as needed */
}
.message-container {
    background-color: #fff;
    margin: 20px auto;
    padding: 0;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 80%;
    position: relative;
}
.message-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
 background-color:#fff;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
    position: sticky;
    top: 0;
    z-index: 1000;
}

.profile {
    display: flex;
    align-items: center;
   
}

.profile img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 10px;
}

.name {
    font-size: 16px;
    font-weight: bold;
}

.menu {
    position: relative;
    
}

.menu .fa-ellipsis-v {
    font-size: 20px;
    cursor: pointer;
}

.dropdown-menu {
    display: none;
    position: absolute;
    top: 100%;
    background-color: #fff;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    border-radius: 8px;
    overflow: hidden;
    min-width: 160px;
    z-index: 1000;
}

.dropdown-menu a {
    display: block;
    padding: 12px 20px;
    text-decoration: none;
    color: #333;
    font-size: 14px;
    transition: background-color 0.3s, color 0.3s;
}

.dropdown-menu a:hover {
    background-color: #ff9800;
    color: #fff;
}
</style>
</head>
<body>
<%@include file="header.jsp" %>
<div class="message-container">
<div class="message-header">
			<div class="profile">
				<img src="data:image/jpeg;base64,<%= base64Image1 %>" alt="Profile">
				<span class="name"><%= user1.getFirstName() %><%= user1.getLastName() %></span>
			</div>
			<div class="menu">
				<i class="fa fa-ellipsis-v" style="padding-right: 20px;" onclick="toggleMenu(this)"></i>
				<div class="dropdown-menu">
					<a href="#" onclick="reportUser()">Report</a>
				</div>
			</div>
		</div>
	<div class="container">
		
		<%
        if (messages != null && messages.size() == 0) {
    %>
		<h4 style="text-align: center; color: #ffffff;">No Messages.</h4>
		<%
        }

        if (messages != null) {
            for (Message message : messages) {
                String currentUserId = String.valueOf(message.getSenderId());
                if (currentUserId.equals(session.getAttribute("userid").toString())) {
    %>
		<div class="row justify-content-end">
			<div class="col-6 alert alert-primary" role="alert">
				<h5>
					me <a
						href="${pageContext.request.contextPath}/deleteChat?id=<%= receiverId %>&delete=<%= message.getId() %>"
						class="card-link" style="float: right;"><i style="color: red;"
						class="far fa-trash-alt"></i></a>
				</h5>
				<%= message.getMessage() %>
				<p style="text-align: right;"><%= message.getTimestamp() %></p>
			</div>
		</div>
		<%
    String status = (String) request.getAttribute("status");
    if ("success".equals(status)) {
%>
		<div class="alert alert-success">Message deleted successfully.</div>
		<%
    } else if ("error".equals(status)) {
%>
		<div class="alert alert-danger">Error deleting message.</div>
		<%
    } else if ("missing_parameters".equals(status)) {
%>
		<div class="alert alert-warning">Missing parameters for
			deletion.</div>
		<%
    }
%>

		<%
                } else {
    %>
		<div class="row justify-content-start">
			<div class="col-6 alert alert-secondary" role="alert">
				<h5>
					<%= userDao.getUserById(message.getSenderId()).getFirstName() %>
					<a
						href="${pageContext.request.contextPath}/deleteChat?id=<%= receiverId %>&delete=<%= message.getId() %>"
						class="card-link" style="float: right;"><i style="color: red;"
						class="far fa-trash-alt"></i></a>
				</h5>
				<%= message.getMessage() %>
				<p style="text-align: right;"><%= message.getTimestamp() %></p>
			</div>
		</div>
		<%
                }
            }
        }
    %>
	</div>
	<div class="type_msg">
		<form method="post" action="Chat">
			<div class="input_msg_write d-flex">
				<input type="hidden" name="senderId"
					value="<%= session.getAttribute("userid") %>" /> <input
					type="hidden" name="receiverId" value="<%= receiverId %>" /> <input
					type="text" name="message" class="write_msg"
					placeholder="Type a message" required pattern="^[a-zA-Z0-9\s]+$"
					title="Message should only contain letters, numbers, and spaces." />
				<button class="msg_send_btn ml-2" type="submit">
					<i class="fa-solid fa-paper-plane"></i>
				</button>
			</div>
		</form>
	</div>
</div>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
function toggleMenu(element) {
    const dropdownMenu = element.nextElementSibling;
    dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
}

function reportUser() {
    alert('User reported');
    // Implement your report user functionality here
}
</script>
</body>
</html>