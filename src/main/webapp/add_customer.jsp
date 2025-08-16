<!DOCTYPE html>
<html>
<head>
    <title>Add New Customer</title>
</head>
<body>
<h2>Add New Customer</h2>
<form action="addCustomer" method="post">
    Account Number: <input type="text" name="accountNumber" required><br>
    Name: <input type="text" name="name" required><br>
    Address: <input type="text" name="address"><br>
    Telephone: <input type="text" name="telephone"><br>
    Units Consumed: <input type="number" name="unitsConsumed" min="0"><br>
    <input type="submit" value="Add Customer">
</form>

<p style="color:green;">
    ${message}
</p>
</body>
</html>
