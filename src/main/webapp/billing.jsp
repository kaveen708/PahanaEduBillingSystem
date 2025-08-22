<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Billing</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');

    body {
      font-family: 'Roboto', sans-serif;
      background: #0f0f15;
      color: #eee;
      margin: 0;
      padding: 0 20px;
    }

    h2 {
      text-align: center;
      margin: 20px 0;
      color: #00d4ff;
    }

    fieldset {
      background: #1a1a2e;
      border-radius: 12px;
      padding: 20px;
      margin: 15px auto;
      max-width: 900px;
      box-shadow: 0 4px 15px rgba(0,0,0,0.5);
      border: none;
    }

    legend {
      font-weight: 500;
      font-size: 16px;
      color: #00d4ff;
    }

    input[type="text"], input[type="number"], select {
      padding: 10px 12px;
      border-radius: 8px;
      border: none;
      background: #27293d;
      color: #eee;
      font-size: 14px;
      margin-right: 10px;
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
      margin-top: 5px;
    }

    button:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0,255,255,0.3);
    }

    table {
      width: 100%;
      border-collapse: collapse;
      background: #27293d;
      border-radius: 12px;
      overflow: hidden;
      margin-top: 12px;
    }

    th, td {
      padding: 12px 15px;
      text-align: left;
      font-size: 14px;
    }

    th {
      background: linear-gradient(90deg, #27293d, #3b3f55);
      color: #00d4ff;
    }

    tr {
      border-bottom: 1px solid #333;
      transition: 0.2s;
    }

    tr:hover {
      background: #1e1e2f;
    }

    td.right {
      text-align: right;
    }

    .actions {
      display: flex;
      gap: 5px;
    }

    .actions button {
      padding: 6px 10px;
      font-size: 12px;
      border-radius: 6px;
    }

    .actions button.edit {
      background: #007bff;
    }

    .actions button.delete {
      background: #dc3545;
    }

    .message {
      text-align: center;
      margin-top: 10px;
      padding: 10px 20px;
      border-radius: 8px;
      max-width: 800px;
      margin-left: auto;
      margin-right: auto;
    }

    .message.success { background: #28a745; color: #fff; }
    .message.error { background: #dc3545; color: #fff; }

    /* Responsive */
    @media(max-width: 768px) {
      input, select, button { width: 100%; margin: 5px 0; }
      table { font-size: 12px; }
    }
  </style>
  <script>
    function searchCustomer() {
      const acc = document.getElementById("accountNumber").value.trim();
      if (!acc) { alert("Enter Account Number"); return; }

      fetch("<%=request.getContextPath()%>/searchCustomer?accountNumber=" + encodeURIComponent(acc))
              .then(r => r.json())
              .then(d => {
                if (d.error) { alert(d.error); return; }
                document.getElementById("customerId").value = d.id;
                document.getElementById("customerName").value = d.name;
              })
              .catch(() => alert("Customer search failed"));
    }

    function addRow() {
      const tbody = document.getElementById("itemsBody");
      const idx = tbody.children.length;

      const tr = document.createElement("tr");

      // Item Code + Search button
      const tdCode = document.createElement("td");
      const codeInput = document.createElement("input");
      codeInput.type = "text";
      codeInput.name = "itemCode";
      codeInput.id = "itemCode" + idx;
      codeInput.style.width = "90px";
      tdCode.appendChild(codeInput);

      const btn = document.createElement("button");
      btn.type = "button";
      btn.innerText = "Search";
      btn.onclick = () => searchItem(idx);
      tdCode.appendChild(btn);
      tr.appendChild(tdCode);

      // Item Name
      const tdName = document.createElement("td");
      const nameInput = document.createElement("input");
      nameInput.type = "text";
      nameInput.name = "itemName";
      nameInput.id = "itemName" + idx;
      nameInput.readOnly = true;
      tdName.appendChild(nameInput);
      tr.appendChild(tdName);

      // Price
      const tdPrice = document.createElement("td");
      const priceInput = document.createElement("input");
      priceInput.type = "number";
      priceInput.name = "price";
      priceInput.id = "price" + idx;
      priceInput.step = "0.01";
      priceInput.readOnly = true;
      tdPrice.appendChild(priceInput);
      tr.appendChild(tdPrice);

      // Quantity
      const tdQty = document.createElement("td");
      const qtyInput = document.createElement("input");
      qtyInput.type = "number";
      qtyInput.name = "quantity";
      qtyInput.id = "quantity" + idx;
      qtyInput.value = 1;
      qtyInput.min = 1;
      qtyInput.onchange = () => recalc(idx);
      tdQty.appendChild(qtyInput);
      tr.appendChild(tdQty);

      // Subtotal
      const tdSub = document.createElement("td");
      tdSub.className = "right";
      tdSub.id = "subtotal" + idx;
      tdSub.innerText = "0.00";
      tr.appendChild(tdSub);

      // Action
      const tdAct = document.createElement("td");
      const rmBtn = document.createElement("button");
      rmBtn.type = "button";
      rmBtn.innerText = "Remove";
      rmBtn.onclick = () => { tr.remove(); recalcTotal(); };
      tdAct.appendChild(rmBtn);
      tr.appendChild(tdAct);

      tbody.appendChild(tr);
    }

    function searchItem(row) {
      const code = document.getElementById("itemCode" + row).value.trim();
      if (!code) { alert("Enter item code"); return; }

      fetch("<%=request.getContextPath()%>/searchItem?q=" + encodeURIComponent(code))
              .then(r => r.json())
              .then(d => {
                if (d.error) { alert(d.error); return; }
                document.getElementById("itemName" + row).value = d.itemName;
                document.getElementById("price" + row).value = d.price;
                document.getElementById("quantity" + row).value = 1;
                recalc(row);
              })
              .catch(() => alert("Item search failed"));
    }

    function recalc(row) {
      const price = parseFloat(document.getElementById("price"+row).value || "0");
      const qty = parseInt(document.getElementById("quantity"+row).value || "1");
      const sub = price * qty;
      document.getElementById("subtotal"+row).innerText = sub.toFixed(2);
      recalcTotal();
    }

    function recalcTotal() {
      const tbody = document.getElementById("itemsBody");
      let total = 0;
      for (let i = 0; i < tbody.children.length; i++) {
        const cell = tbody.children[i].querySelector("td.right");
        total += parseFloat(cell.innerText || "0");
      }
      document.getElementById("totalAmount").value = total.toFixed(2);
      recalcBalance();
    }

    function recalcBalance() {
      const total = parseFloat(document.getElementById("totalAmount").value || "0");
      const paid = parseFloat(document.getElementById("paidAmount").value || "0");
      const bal = paid - total;
      document.getElementById("balance").value = bal.toFixed(2);
    }

    window.onload = () => addRow();
  </script>
</head>
<body>
<h2>Billing</h2>

<form action="<%=request.getContextPath()%>/billing" method="post">
  <!-- Customer -->
  <fieldset>
    <legend>Customer</legend>
    Account Number:
    <input type="text" id="accountNumber" placeholder="ACC-XXXX"/>
    <button type="button" onclick="searchCustomer()">Search</button>
    &nbsp;&nbsp;
    Customer ID: <input type="text" id="customerId" name="customerId" readonly style="width:80px;"/>
    Name: <input type="text" id="customerName" readonly style="width:240px;"/>
  </fieldset>

  <!-- Items -->
  <fieldset style="margin-top:12px;">
    <legend>Items</legend>
    <table>
      <thead>
      <tr>
        <th style="width:180px;">Item Code (Search)</th>
        <th>Item Name</th>
        <th style="width:110px;">Price</th>
        <th style="width:110px;">Qty</th>
        <th style="width:110px;">Subtotal</th>
        <th style="width:100px;">Action</th>
      </tr>
      </thead>
      <tbody id="itemsBody"></tbody>
    </table>
    <button type="button" onclick="addRow()">+ Add Item</button>
  </fieldset>

  <!-- Payment -->
  <fieldset style="margin-top:12px;">
    <legend>Payment</legend>
    Payment Method:
    <select name="paymentMethod">
      <option value="CASH" selected>Cash</option>
      <option value="CARD">Card</option>
    </select>
    &nbsp;&nbsp;
    Total: <input type="text" id="totalAmount" name="totalAmount" readonly style="width:120px;"/>
    &nbsp;&nbsp;
    Paid: <input type="number" id="paidAmount" name="paidAmount" step="0.01" oninput="recalcBalance()" style="width:120px;"/>
    &nbsp;&nbsp;
    Balance: <input type="text" id="balance" name="balance" readonly style="width:120px;"/>
  </fieldset>

  <div style="margin-top:14px;">
    <button type="submit">Create & Print Bill</button>
  </div>
</form>
</body>
</html>
