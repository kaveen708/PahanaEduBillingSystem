<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.ItemDTO" %>

<html>
<head>
    <title>Manage Items</title>
    <style>
        table {
            border-collapse: collapse;
            width: 90%;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        .highlight {
            background-color: #d4edda;
        }
        form.inline-form {
            display: inline-block;
            margin: 0;
        }
        input[type="text"], input[type="number"] {
            width: 100px;
        }
    </style>
</head>
<body>

<h2>Add New Item</h2>
<form method="post" action="items">
    Code: <input type="text" name="itemCode" required><br>
    Name: <input type="text" name="itemName" required><br>
    Description: <input type="text" name="description"><br>
    Price: <input type="text" name="price" required><br>
    Quantity: <input type="number" name="quantity" required><br>
    <button type="submit">Add Item</button>
</form>

<% if (request.getAttribute("message") != null) { %>
<p style="color:green;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

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
        </td>
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

            <form action="deleteItem" method="get" class="inline-form" style="margin:0;"
                  onsubmit="return confirm('Are you sure you want to delete this item?');">
                <input type="hidden" name="itemCode" value="<%= item.getItemCode() %>">
                <button type="submit" style="color:red;">Delete</button>
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

</body>
</html>
