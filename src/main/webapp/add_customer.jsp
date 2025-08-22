<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Customer Management</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style>
        /* Dark Theme */
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background: #0f0f15;
            color: #eee;
        }
        h2 { color: #00d4ff; }

        .dashboard-btn {
            margin-bottom: 20px;
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
            margin-bottom: 30px;
            padding: 20px;
            border-radius: 8px;
            background: #1a1a2e;
            width: 450px;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
            color: #00d4ff;
        }
        input[type=text], input[type=number] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #444;
            border-radius: 4px;
            background: #12121f;
            color: #eee;
        }
        input[type=submit], button {
            margin-top: 15px;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: #000;
            font-weight: bold;
        }
        input[type=submit] { background: #00d4ff; }
        input[type=submit]:hover { background: #00a3cc; }
        button.clear-btn { background: #6c757d; color: #fff; }
        button.clear-btn:hover { background: #5a6268; }
        button.show-btn { background: #007bff; color: #fff; }
        button.show-btn:hover { background: #0056b3; }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
            border-radius: 8px;
            overflow: hidden;
            background: #1a1a2e;
        }
        th, td { padding: 10px; text-align: left; }
        th { background: #27293d; color: #00d4ff; }
        td { border-bottom: 1px solid #333; }
        tr:hover { background: #22223b; }

        button.edit-btn, a.delete-link {
            padding: 4px 8px;
            border: none;
            cursor: pointer;
            border-radius: 3px;
            margin-right: 5px;
            font-weight: bold;
        }
        button.edit-btn { background: #007bff; color: #fff; }
        button.edit-btn:hover { background: #0056b3; }
        a.delete-link {
            background: #dc3545;
            color: #fff;
            text-decoration: none;
            padding: 4px 8px;
        }
        a.delete-link:hover { background: #a71d2a; }

        /* SweetAlert2 Custom Styling */
        .my-popup {
            background: #1a1a2e !important;
            color: #000 !important;
            border-radius: 10px !important;
        }
        .my-title {
            color: #00d4ff !important;
            font-size: 22px !important;
        }
        .my-content {
            color: #ccc !important;
            font-size: 16px !important;
        }
        .my-confirm {
            background-color: #00d4ff !important;
            color: #000 !important;
            border-radius: 6px !important;
            font-weight: bold !important;
        }

        /* Scrollbar */
        ::-webkit-scrollbar { width: 10px; }
        ::-webkit-scrollbar-track { background: #1a1a2e; }
        ::-webkit-scrollbar-thumb { background: #444; border-radius: 5px; }
        ::-webkit-scrollbar-thumb:hover { background: #666; }
    </style>

    <script>
        function editCustomer(id, accountNumber, name, address, phone, unitConsume) {
            document.getElementById("id").value = id;
            document.getElementById("accountNumber").value = accountNumber;
            document.getElementById("name").value = name;
            document.getElementById("address").value = address;
            document.getElementById("phoneNumber").value = phone;
            document.getElementById("unitConsume").value = unitConsume;
            document.getElementById("submitBtn").value = "Update Customer";
        }
        function resetForm() {
            document.getElementById("id").value = "";
            document.getElementById("accountNumber").value = "";
            document.getElementById("name").value = "";
            document.getElementById("address").value = "";
            document.getElementById("phoneNumber").value = "";
            document.getElementById("unitConsume").value = "";
            document.getElementById("submitBtn").value = "Add Customer";
        }
        function showCustomers() {
            window.location.href = '${pageContext.request.contextPath}/customers';
        }
        function backToDashboard() {
            window.location.href = '${pageContext.request.contextPath}/dashboard.jsp';
        }
    </script>
</head>
<body>

<!-- Back to Dashboard Button -->
<button class="dashboard-btn" onclick="backToDashboard()">&#8592; Back to Dashboard</button>

<h2>Add / Update Customer</h2>

<form method="post" action="${pageContext.request.contextPath}/customers">
    <input type="hidden" name="id" id="id"/>

    <label>Account Number:</label>
    <input type="text" name="accountNumber" id="accountNumber" required/>

    <label>Name:</label>
    <input type="text" name="name" id="name" required/>

    <label>Address:</label>
    <input type="text" name="address" id="address"/>

    <label>Phone Number:</label>
    <input type="text" name="phoneNumber" id="phoneNumber"/>

    <label>Unit Consume:</label>
    <input type="number" name="unitConsume" id="unitConsume" min="0"/>

    <input type="submit" id="submitBtn" value="Add Customer"/>
    <button type="button" class="clear-btn" onclick="resetForm()">Clear</button>
    <button type="button" class="show-btn" onclick="showCustomers()">Show Customers</button>
</form>

<h2>Customer List</h2>
<c:if test="${not empty customers}">
    <table>
        <tr>
            <th>ID</th>
            <th>Account No</th>
            <th>Name</th>
            <th>Address</th>
            <th>Phone</th>
            <th>Unit Consume</th>
            <th>Action</th>
        </tr>
        <c:forEach var="c" items="${customers}">
            <tr>
                <td>${c.id}</td>
                <td>${c.accountNumber}</td>
                <td>${c.name}</td>
                <td>${c.address}</td>
                <td>${c.phoneNumber}</td>
                <td>${c.unitConsume}</td>
                <td>
                    <button class="edit-btn"
                            onclick="editCustomer('${c.id}', '${c.accountNumber}', '${c.name}', '${c.address}', '${c.phoneNumber}', '${c.unitConsume}')">
                        Edit
                    </button>
                    <a href="${pageContext.request.contextPath}/customers?action=delete&id=${c.id}" class="delete-link"
                       onclick="return confirm('Are you sure to delete this customer?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty customers}">
    <p>No customers to display. Click <strong>Show Customers</strong> to load the list.</p>
</c:if>

<!-- SweetAlert2 Popups -->
<c:if test="${not empty message}">
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: '${message}',
            customClass: {
                popup: 'my-popup',
                title: 'my-title',
                content: 'my-content',
                confirmButton: 'my-confirm'
            }
        });
    </script>
</c:if>

<c:if test="${not empty error}">
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: '${error}',
            customClass: {
                popup: 'my-popup',
                title: 'my-title',
                content: 'my-content',
                confirmButton: 'my-confirm'
            }
        });
    </script>
</c:if>

<c:if test="${not empty idExists}">
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Duplicate ID',
            text: '${idExists}',
            customClass: {
                popup: 'my-popup',
                title: 'my-title',
                content: 'my-content',
                confirmButton: 'my-confirm'
            }
        });
    </script>
</c:if>

</body>
</html>
