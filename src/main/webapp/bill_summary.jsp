<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Bill Summary</title>
    <style>
        body { font-family: Arial, sans-serif; }
        table { border-collapse: collapse; width: 100%; margin-top: 10px; }
        th, td { border: 1px solid #ddd; padding: 8px; }
        th { background: #f4f4f4; }
        .right { text-align: right; }
        .center { text-align: center; }
        .print { margin-top: 12px; }
    </style>
</head>
<body>
<h2>Bill Generated</h2>
<p><strong>Bill ID:</strong> ${billId}</p>
<p><strong>Customer ID:</strong> ${bill.customerId}</p>
<p><strong>Payment Method:</strong> ${bill.paymentMethod}</p>

<h3>Items</h3>
<table>
    <thead>
    <tr>
        <th>Item ID</th>
        <th>Item Name</th>
        <th class="right">Price</th>
        <th class="center">Qty</th>
        <th class="right">Subtotal</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="i" items="${bill.items}">
        <tr>
            <td>${i.itemId}</td>
            <td>${i.itemName}</td>
            <td class="right">${i.price}</td>
            <td class="center">${i.quantity}</td>
            <td class="right">${i.subtotal}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h3>Totals</h3>
<p><strong>Total:</strong> ${bill.totalAmount}</p>
<p><strong>Paid:</strong> ${bill.paidAmount}</p>
<p><strong>Balance:</strong> ${bill.balance}</p>

<div class="print">
    <button onclick="window.print()">Print Bill</button>
</div>
</body>
</html>
