<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Bill Details</title>
    <style>
        /* General Body */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #121212;
            color: #e0e0e0;
            padding: 20px;
        }

        /* Heading */
        h2 {
            color: #00ffc3;
            margin-bottom: 20px;
            font-size: 28px;
        }

        /* Back to Dashboard Button */
        .dashboard-btn {
            display: inline-block;
            margin-bottom: 20px;
            padding: 10px 20px;
            background: #00ffc3;
            color: #121212;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .dashboard-btn:hover {
            background: #00d1b2;
            color: #fff;
        }

        /* Filter Form */
        .filter-form {
            background: #1f1f1f;
            padding: 20px;
            border-radius: 10px;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: center;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
        }

        .filter-form label {
            display: flex;
            flex-direction: column;
            font-weight: 500;
            font-size: 14px;
            color: #e0e0e0;
        }

        .filter-form input[type="text"],
        .filter-form input[type="date"],
        .filter-form select {
            padding: 8px 10px;
            margin-top: 5px;
            border: 1px solid #333;
            border-radius: 6px;
            background: #121212;
            color: #e0e0e0;
        }

        .filter-form input::placeholder {
            color: #888;
        }

        .filter-form .btn {
            padding: 10px 18px;
            background: #00ffc3;
            color: #121212;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 500;
            transition: background 0.3s ease, color 0.3s ease;
        }

        .filter-form .btn:hover {
            background: #00d1b2;
            color: #fff;
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
            background: #1f1f1f;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
        }

        th, td {
            padding: 12px 15px;
            text-align: center;
            font-size: 14px;
            color: #e0e0e0;
            border-bottom: 1px solid #333;
        }

        th {
            background: #00ffc3;
            color: #121212;
            font-weight: 600;
        }

        tr:nth-child(even) {
            background: #272727;
        }

        tr:hover {
            background: #00ffc3;
            color: #121212;
            transition: background 0.3s ease, color 0.3s ease;
        }

        /* No Data Row */
        td[colspan="7"] {
            text-align: center;
            font-style: italic;
            color: #888;
            padding: 20px;
        }

        /* Responsive */
        @media screen and (max-width: 768px) {
            .filter-form {
                flex-direction: column;
                align-items: flex-start;
            }

            th, td {
                font-size: 13px;
                padding: 10px;
            }
        }
    </style>

    <script>
        function backToDashboard() {
            window.location.href = '${pageContext.request.contextPath}/dashboard.jsp';
        }
    </script>
</head>
<body>

<!-- Back to Dashboard Button -->
<button class="dashboard-btn" onclick="backToDashboard()">&#8592; Back to Dashboard</button>

<h2>📊 Bill Details</h2>

<!-- Filter Form -->
<form class="filter-form" method="get" action="billDetails">
    <label>Customer ID: <input type="text" name="customerId" value="${param.customerId}"></label>
    <label>Date: <input type="date" name="date" value="${param.date}"></label>
    <label>Payment Method:
        <select name="paymentMethod">
            <option value="">All</option>
            <option value="Cash" ${param.paymentMethod=="Cash"?"selected":""}>Cash</option>
            <option value="Card" ${param.paymentMethod=="Card"?"selected":""}>Card</option>
        </select>
    </label>
    <button type="submit" class="btn">Search</button>
</form>

<!-- Bill Table -->
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Customer ID</th>
        <th>Total</th>
        <th>Paid</th>
        <th>Balance</th>
        <th>Payment Method</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bill" items="${bills}">
        <tr>
            <td>${bill.id}</td>
            <td>${bill.customerId}</td>
            <td>${bill.totalAmount}</td>
            <td>${bill.paidAmount}</td>
            <td>${bill.balance}</td>
            <td>${bill.paymentMethod}</td>
            <td>${bill.createdAt}</td>
        </tr>
    </c:forEach>
    <c:if test="${empty bills}">
        <tr><td colspan="7">No bills found.</td></tr>
    </c:if>
    </tbody>
</table>
</body>
</html>
