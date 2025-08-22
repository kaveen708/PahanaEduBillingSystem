<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.UserDTO" %>
<%
  UserDTO loggedUser = (UserDTO) session.getAttribute("user");
  if (loggedUser == null) {
    response.sendRedirect("login.jsp");
    return;
  }
  String role = loggedUser.getRole();
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Dashboard - Pahana Edu</title>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  <style>
    *{box-sizing:border-box;margin:0;padding:0;}
    body{font-family:'Roboto',sans-serif;background:#121212;color:#e0e0e0;min-height:100vh;}
    .navbar{background:#1f1f1f;padding:15px 30px;display:flex;justify-content:space-between;align-items:center;box-shadow:0 4px 15px rgba(0,0,0,0.5);}
    .navbar h1{font-size:24px;letter-spacing:1px;color:#00ffc3;}
    .navbar a, .navbar span{color:#e0e0e0;text-decoration:none;margin-left:20px;font-weight:500;}
    .container{padding:40px 30px;}
    .summary{display:flex;gap:20px;flex-wrap:wrap;margin-bottom:30px;}
    .summary .summary-card{flex:1;min-width:200px;background:#1f1f2e;padding:20px;border-radius:10px;text-align:center;box-shadow:0 4px 15px rgba(0,0,0,0.5);}
    .summary .summary-card h3{color:#00ffc3;font-size:28px;margin-bottom:10px;}
    .summary .summary-card p{color:#aaa;font-size:14px;}
    .cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:25px;margin-top:30px;}
    .card{background:#1f1f2e;border-radius:12px;padding:25px 20px;box-shadow:0 6px 20px rgba(0,0,0,0.6);transition:transform 0.3s,box-shadow 0.3s;border-left:5px solid #00ffc3;}
    .card:hover{transform:translateY(-10px);box-shadow:0 10px 30px rgba(0,0,0,0.8);}
    .card h2{font-size:20px;color:#00ffc3;margin-bottom:10px;}
    .card p{font-size:14px;color:#aaa;margin-top:5px;line-height:1.5;}
    .card a{font-size:18px;font-weight:500;color:#00d1b2;text-decoration:none;}
    .card a:hover{color:#00ffc3;}
    .footer{text-align:center;padding:25px 10px;margin-top:50px;color:#888;font-size:13px;background:#1f1f1f;box-shadow:0 -2px 10px rgba(0,0,0,0.4);}
  </style>
</head>
<body>

<div class="navbar">
  <h1>📚 Pahana Edu Dashboard</h1>
  <div>
    <span>Welcome, <%= loggedUser.getUsername() %> (<%= role %>)</span>

    <a href="index.jsp">Logout</a>
  </div>
</div>

<div class="container">
  <h2>Dashboard Overview</h2>
  <p>Quick stats of your bookshop system.</p>

  <!-- Summary Cards -->
  <!-- <div class="summary">
    <% if ("admin".equalsIgnoreCase(role)) { %>
    <div class="summary-card">
      <h3><%= request.getAttribute("totalUsers") != null ? request.getAttribute("totalUsers") : 0 %></h3>
      <p>Total Registered Users</p>
    </div>
    <% } %>
    <div class="summary-card">
      <h3><%= request.getAttribute("totalCustomers") != null ? request.getAttribute("totalCustomers") : 0 %></h3>
      <p>Total Customers</p>
    </div>
    <div class="summary-card">
      <h3><%= request.getAttribute("totalBills") != null ? request.getAttribute("totalBills") : 0 %></h3>
      <p>Total Bills</p>
    </div>
    <div class="summary-card">
      <h3><%= request.getAttribute("totalItems") != null ? request.getAttribute("totalItems") : 0 %></h3>
      <p>Total Items</p>
    </div>
  </div>-->

  <!-- Role-Based Action Cards -->
  <div class="cards">
    <% if ("admin".equalsIgnoreCase(role) || "staff".equalsIgnoreCase(role)) { %>
    <div class="card">
      <a href="add_customer.jsp">Customer Accounts</a>
      <p>Manage customer accounts and details.</p>
    </div>
    <div class="card">
      <a href="billing.jsp">Billing</a>
      <p>Handle sales and invoices.</p>
    </div>
    <% } %>

    <% if ("admin".equalsIgnoreCase(role)) { %>
    <div class="card">
      <a href="bill_details.jsp">Bill Details</a>
      <p>View all generated bills with payment details.</p>
    </div>
    <% } %>


    <% if ("admin".equalsIgnoreCase(role)) { %>
    <div class="card">
      <a href="add_item.jsp">Add Item</a>
      <p>Manage book listings and prices.</p>
    </div>
    <div class="card">
      <a href="register.jsp">Create User Accounts</a>
      <p>Register new staff/admin users.</p>
    </div>
    <div class="card">
      <a href="help.jsp">HELP</a>
      <p>Configure system preferences and user roles.</p>
    </div>
    <% } %>
  </div>
</div>

<div class="footer">
  &copy; 2025 Pahana Edu Bookshop. All rights reserved.
</div>

</body>
</html>
