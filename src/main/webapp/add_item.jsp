<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.ItemDTO" %>

<html>
<head>
    <title>Manage Items</title>
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        /* ===== Modern Dark Dashboard Theme ===== */
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

        body {
            margin: 0;
            font-family: 'Roboto', sans-serif;
            background: #0f0f15;
            color: #eee;
        }

        h2, h3 {
            text-align: center;
            margin-top: 20px;
            color: #fff;
        }

        .dashboard-btn {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background: #00d4ff;
            color: #000;
            font-weight: bold;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .dashboard-btn:hover { background: #00a3cc; }

        form {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 15px;
            background: #1a1a2e;
            padding: 20px;
            border-radius: 12px;
            margin: 20px auto;
            max-width: 800px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
        }

        input[type="text"], input[type="number"] {
            padding: 10px 12px;
            border-radius: 8px;
            border: none;
            background: #27293d;
            color: #eee;
            font-size: 14px;
            flex: 1 1 120px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            background: linear-gradient(45deg, #007bff, #00d4ff);
            color: #fff;
            cursor: pointer;
            transition: 0.3s;
            font-weight: 500;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,255,255,0.3);
        }

        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background: #1a1a2e;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            font-size: 14px;
        }

        th {
            background: linear-gradient(90deg, #27293d, #3b3f55);
            color: #00d4ff;
            font-weight: 500;
        }

        tr {
            border-bottom: 1px solid #333;
            transition: 0.2s;
        }

        tr:hover {
            background: #27293d;
        }

        .actions {
            display: flex;
            gap: 10px;
        }

        .actions button {
            padding: 6px 10px;
            font-size: 12px;
            border-radius: 6px;
        }

        .actions button.edit { background: #007bff; }
        .actions button.delete { background: #dc3545; }
        .actions button:hover { opacity: 0.9; }
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

<h2>Add New Item</h2>
<form method="post" action="items">
    Code: <input type="text" name="itemCode" required><br>
    Name: <input type="text" name="itemName" required><br>
    Description: <input type="text" name="description"><br>
    Price: <input type="text" name="price" required><br>
    Quantity: <input type="number" name="quantity" required><br>
    <button type="submit">Add Item</button>
</form>

<%-- SweetAlert messages --%>
<%
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
    if (message != null) {
%>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: '<%= message %>',
        confirmButtonColor: '#00d4ff'
    });
</script>
<%
} else if (error != null) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Error!',
        text: '<%= error %>',
        confirmButtonColor: '#dc3545'
    });
</script>
<%
    }
%>

<h3>Show Items</h3>
<form method="get" action="items">
    <input type="hidden" name="action" value="show">
    <button type="submit">Show Items</button>
</form>

<%
    List<ItemDTO> items = (List<ItemDTO>) request.getAttribute("items");
    if (items != null && !items.isEmpty()) {
%>
<h3>Items List</h3>
<table>
    <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Actions</th>
    </tr>
    <%
        for (ItemDTO item : items) {
    %>
    <tr>
        <td><%= item.getItemCode() %></td>
        <td>
            <form action="editItem" method="post" class="inline-form" style="margin:0;">
                <input type="hidden" name="itemCode" value="<%= item.getItemCode() %>">
                <input type="text" name="itemName" value="<%= item.getItemName() %>" required>

        <td>
            <input type="text" name="description" value="<%= item.getDescription() != null ? item.getDescription() : "" %>">
        </td>
        <td>
            <input type="number" step="0.01" name="price" value="<%= item.getPrice() %>" required>
        </td>
        <td>
            <input type="number" name="quantity" value="<%= item.getQuantity() %>" required>
        </td>
        <td>
            <button type="submit">Update</button>
            </form>
            <form action="deleteItem" method="get" class="inline-form delete-form" style="margin:0;">
                <input type="hidden" name="itemCode" value="<%= item.getItemCode() %>">
                <button type="submit" style="background:#dc3545;">Delete</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else if (items != null) {
%>
<p>No items found.</p>
<%
    }
%>

<!-- SweetAlert2 Delete Confirm -->
<script>
    document.querySelectorAll(".delete-form").forEach(form => {
        form.addEventListener("submit", function(e) {
            e.preventDefault();
            Swal.fire({
                title: 'Are you sure?',
                text: "This item will be permanently deleted!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#dc3545',
                cancelButtonColor: '#6c757d',
                confirmButtonText: 'Yes, delete it!'
            }).then((result) => {
                if (result.isConfirmed) {
                    form.submit(); // proceed with deletion
                }
            });
        });
    });
</script>

</body>
</html>
