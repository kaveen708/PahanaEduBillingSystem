<%--
  Created by IntelliJ IDEA.
  User: Kaveen
  Date: 2025-08-04
  Time: 8:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index Page</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Segoe UI", sans-serif;
        }

        body, html {
            height: 100%;
        }

        .main-container {
            height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .site-header {
            background-color: rgb(17, 1, 1);
            color: #ffffff;
            padding: 20px 0;
            text-align: center;
        }

        .hero {
            flex: 1;
            background-image: url("images/2.jpg"); /* ✅ check this path */
            background-size: cover;
            background-position: center;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .overlay {
            background-color: rgba(0, 0, 0, 0.6);
            padding: 50px;
            border-radius: 1px;
            text-align: center;
            color: #fff;
        }

        .overlay h2 {
            font-size: 32px;
            margin-bottom: 20px;
        }

        .btn {
            background-color: #ff7300;
            color: #000;
            padding: 12px 25px;
            text-decoration: none;
            font-weight: bold;
            border-radius: 1px;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #ffc400;
        }
    </style>
</head>
<body>

<div class="main-container">
    <header class="site-header">
        <h1>📚 Pahana Edu Bookshop</h1>
        <p>Your Trusted Educational Partner in Colombo</p>
    </header>

    <section class="hero">
        <div class="overlay">
            <h2>Welcome to the Customer Management System</h2>
            <a href="dashboard.jsp" class="btn">Login to System</a>
        </div>
    </section>
</div>

</body>
</html>
