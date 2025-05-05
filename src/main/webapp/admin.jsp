<%--
  Created by IntelliJ IDEA.
  User: asrorbek
  Date: 5/2/25
  Time: 7:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .container {
            background: white;
            padding: 2rem 3rem;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
        }
        h1 {
            color: #343a40;
            margin-bottom: 2rem;
            font-size: 1.8rem;
            font-weight: 600;
        }
        .btn-primary {
            width: 100%;
            padding: 0.75rem;
            font-size: 1.1rem;
            border-radius: 5px;
            transition: background-color 0.3s, transform 0.2s;
            margin-bottom: 1rem;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }
        .btn-primary:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Admin Management Panel</h1>
    <a href="moderatorOption" type="button" class="btn btn-primary">Moderator Option</a>
    <a href="bookOption" type="button" class="btn btn-primary">Book Option</a>
</div>

<!-- Bootstrap JS (Optional for interactivity) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>