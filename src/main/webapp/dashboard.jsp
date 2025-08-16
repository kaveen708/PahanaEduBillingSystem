<%--
  Created by IntelliJ IDEA.
  User: Kaveen
  Date: 2025-08-05
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard - Pahana Edu</title>
  <style>
    body {
      margin: 0;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background-color: #f0f2f5;
    }

    .navbar {
      background-color: #007bff;
      padding: 15px 30px;
      color: white;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .navbar h1 {
      margin: 0;
      font-size: 22px;
    }

    .navbar a {
      color: white;
      text-decoration: none;
      margin-left: 20px;
      font-weight: bold;
    }

    .container {
      padding: 30px;
    }

    .cards {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
      gap: 20px;
      margin-top: 30px;
    }

    .card {
      background-color: white;
      border-radius: 10px;
      padding: 20px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.1);
      transition: transform 0.2s ease;
    }

    .card:hover {
      transform: translateY(-5px);
    }

    .card h2 {
      font-size: 18px;
      margin: 0 0 10px;
      color: #333;
    }

    .card p {
      font-size: 14px;
      color: #666;
    }

    .footer {
      text-align: center;
      margin-top: 50px;
      color: #999;
      font-size: 13px;
    }
  </style>
</head>
<body>

<div class="navbar">
  <h1>📚 Pahana Edu Dashboard</h1>
  <div>
    <a href="profile.jsp">Profile</a>
    <a href="logout.jsp">Logout</a>
  </div>
</div>

<div class="container">
  <h2>Welcome, Admin!</h2>
  <p>Here is a quick overview of your bookshop system.</p>

  <div class="cards">
    <div class="card">
      <a href="add_customer.jsp">customer</a>
      <p>Manage customer accounts and details.</p>
    </div>
    <div class="card">
      <a href="add_item.jsp">add item</a>
      <p>View and manage recent orders and invoices.</p>
    </div>
    <div class="card">
      <a href="billing.jsp">billing</a>
      <p>Add or update book listings and prices.</p>
    </div>
    <div class="card">
      <h2>Settings</h2>
      <p>Configure system preferences and user roles.</p>
    </div>
  </div>
</div>

<div class="footer">
  &copy; 2025 Pahana Edu Bookshop. All rights reserved.
</div>

</body>
</html>
