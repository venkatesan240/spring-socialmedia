<html>
<head>
<title>admin main</title>
<style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .navbar {
            background-color: #333;
            overflow: hidden;
            border-radius: 8px;
        }
        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 17px;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar a.active {
            background-color: #04AA6D;
            color: white;
        }
        .container {
            text-align: center;
        }
        .container h1 {
            margin-bottom: 40px;
            color: #333;
        }
    </style>
</head>
<body>
	<div class="container">
        <div class="navbar">
            <a href="admin.jsp">View Users</a>
            <a href="admin1.jsp">View Reports</a>
            <a href="signin.jsp">Logout</a>
        </div>
    </div>
</body>
</html>