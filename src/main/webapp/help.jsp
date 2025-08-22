<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Supermarket Billing System - Help</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 30px auto;
            background: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1, h2 {
            color: #333;
        }
        h1 { text-align: center; margin-bottom: 20px; }
        h2 { margin-top: 20px; }
        p {
            line-height: 1.6;
            margin: 10px 0;
        }
        ul {
            margin: 10px 0 20px 20px;
        }
        .section {
            margin-bottom: 30px;
        }
        .note {
            background: #fef3c7;
            padding: 10px;
            border-left: 4px solid #f59e0b;
            margin: 10px 0;
        }
        a {
            color: #2563eb;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Supermarket Billing System - Help</h1>

    <div class="section">
        <h2>For New Users (Staff)</h2>
        <p>Welcome! This section explains how to use the system if you are a staff user.</p>
        <ul>
            <li><strong>Login:</strong> Enter your username and password provided by the admin.</li>
            <li><strong>Create Bill:</strong>
                <ul>
                    <li>Select a customer by entering the Customer ID.</li>
                    <li>Add items by selecting item codes or names.</li>
                    <li>Enter the quantity for each item.</li>
                    <li>Enter the amount paid by the customer.</li>
                    <li>Click "Create Bill" to save. If payment is insufficient, an error will appear.</li>
                </ul>
            </li>
            <li><strong>Print Bill:</strong> Click the print button after creating the bill to generate a receipt.</li>
            <li><strong>View Bills:</strong> Navigate to the bills section to check all bills created.</li>
        </ul>
    </div>

    <div class="section">
        <h2>For Admin Users</h2>
        <p>Admins have additional features:</p>
        <ul>
            <li><strong>Manage Users:</strong> Add, edit, or delete staff accounts.</li>
            <li><strong>Manage Customers:</strong> Add new customers, edit details, or delete customers.</li>
            <li><strong>Manage Items:</strong> Add, update, or remove items in the system.</li>
            <li><strong>View All Bills:</strong> Filter by date or customer to view all billing history.</li>
            <li><strong>Reports:</strong> Generate reports for sales and payments.</li>
        </ul>
    </div>

    <div class="section">
        <h2>Tips for Using the System</h2>
        <ul>
            <li>Always check the payment amount before creating a bill.</li>
            <li>Use correct customer IDs to avoid errors.</li>
            <li>Maintain accurate item prices for correct billing.</li>
            <li>Admin users should periodically update item stock and prices.</li>
        </ul>
    </div>

    <div class="section note">
        <strong>Note:</strong> For any issues or errors while using the system, contact your system administrator.
    </div>

    <p style="text-align:center;"><a href="dashboard.jsp">Back to Dashboard</a></p>
</div>
</body>
</html>
