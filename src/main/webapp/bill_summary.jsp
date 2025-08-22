<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Supermarket Bill</title>
    <style>
        body {
            font-family: "Courier New", monospace;
            background: #f8f8f8;
            display: flex;
            justify-content: center;
            padding: 20px;
        }
        .bill-container {
            background: #fff;
            padding: 20px;
            width: 350px;
            border: 1px dashed #000;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }

        /* Back to Dashboard Button */
        .dashboard-btn {
            display: block;
            margin-bottom: 15px;
            padding: 6px 12px;
            background: black;
            color: white;
            font-weight: bold;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
        }
        .dashboard-btn:hover {
            background: #333;
        }

        .header, .footer {
            text-align: center;
            border-bottom: 1px dashed #000;
            padding-bottom: 8px;
            margin-bottom: 8px;
        }
        .footer {
            border-top: 1px dashed #000;
            border-bottom: none;
            margin-top: 10px;
            font-size: 13px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
        }
        th, td {
            padding: 4px;
        }
        th {
            text-align: left;
            border-bottom: 1px dashed #000;
        }
        td.right { text-align: right; }
        td.center { text-align: center; }
        .totals {
            margin-top: 10px;
            border-top: 1px dashed #000;
            padding-top: 8px;
            font-size: 14px;
        }
        .totals p {
            display: flex;
            justify-content: space-between;
            margin: 2px 0;
        }
        .print, .create {
            margin-top: 12px;
            text-align: center;
        }
        button {
            padding: 6px 14px;
            border: none;
            background: black;
            color: white;
            font-size: 13px;
            cursor: pointer;
        }
        button:disabled {
            background: #aaa;
            cursor: not-allowed;
        }
        button:hover:enabled {
            background: #333;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        function backToDashboard() {
            window.location.href = '${pageContext.request.contextPath}/dashboard.jsp';
        }
    </script>
</head>
<body>
<div class="bill-container">

    <!-- Back to Dashboard Button -->
    <button class="dashboard-btn" onclick="backToDashboard()">&#8592; Back to Dashboard</button>

    <!-- ❌ Error Message -->
    <c:if test="${not empty error}">
        <div style="color:red; font-weight:bold; text-align:center; margin-bottom:10px;">
                ${error}
        </div>
    </c:if>

    <c:if test="${not empty bill}">
        <div class="header">
            <h2>ABC Supermarket</h2>
            <p>Bill Receipt</p>
            <p>Bill ID: ${billId}</p>
            <p>Customer ID: ${bill.customerId}</p>
            <p>Payment: ${bill.paymentMethod}</p>
        </div>

        <table>
            <thead>
            <tr>
                <th>Item</th>
                <th class="center">Qty</th>
                <th class="right">Price</th>
                <th class="right">Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="i" items="${bill.items}">
                <tr>
                    <td>${i.itemName}</td>
                    <td class="center">${i.quantity}</td>
                    <td class="right">${i.price}</td>
                    <td class="right">${i.subtotal}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="totals">
            <p><strong>Total:</strong> <span>${bill.totalAmount}</span></p>
            <p><strong>Paid:</strong> <span>${bill.paidAmount}</span></p>
            <p><strong>Balance:</strong> <span>${bill.balance}</span></p>
        </div>

        <div class="create">
            <button <c:if test="${bill.paidAmount < bill.totalAmount}">disabled</c:if>>
                Create Bill
            </button>
        </div>

        <div class="print">
            <button onclick="window.print()" <c:if test="${bill.paidAmount < bill.totalAmount}">disabled</c:if>>
                🖨 Print Bill
            </button>
        </div>

        <div class="footer">
            <p>Thank you for shopping with us!</p>
            <p>Visit Again</p>
        </div>
    </c:if>
</div>

<!-- SweetAlert messages -->
<c:if test="${not empty message}">
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: '${message}',
            confirmButtonColor: '#3085d6'
        });
    </script>
</c:if>

<c:if test="${not empty error}">
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: '${error}',
            confirmButtonColor: '#d33'
        });
    </script>
</c:if>

</body>
</html>
