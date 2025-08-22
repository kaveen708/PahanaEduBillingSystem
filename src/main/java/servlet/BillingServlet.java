package servlet;

import dto.BillDTO;
import dto.BillItemDTO;
import service.BillService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {
    private final BillService service = new BillService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // --- Get customer and payment info ---
            int customerId = Integer.parseInt(req.getParameter("customerId"));
            String paymentMethod = req.getParameter("paymentMethod");

            double paidAmount = 0.0;
            try {
                paidAmount = Double.parseDouble(req.getParameter("paidAmount"));
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Please enter a valid paid amount!");
                req.getRequestDispatcher("/bill_summary.jsp").forward(req, resp);
                return;
            }


            // --- Get item data ---
            String[] itemIds = req.getParameterValues("itemId");
            String[] itemNames = req.getParameterValues("itemName");
            String[] prices = req.getParameterValues("price");
            String[] quantities = req.getParameterValues("quantity");

            // --- Calculate total ---
            List<BillItemDTO> items = new ArrayList<>();
            double total = 0.0;

            for (int i = 0; itemNames != null && i < itemNames.length; i++) {
                if (itemNames[i] == null || itemNames[i].trim().isEmpty()) continue;

                BillItemDTO it = new BillItemDTO();
                it.setItemName(itemNames[i]);

                double price = (prices[i] == null || prices[i].isEmpty()) ? 0.0 : Double.parseDouble(prices[i]);
                int qty = (quantities[i] == null || quantities[i].isEmpty()) ? 1 : Integer.parseInt(quantities[i]);
                double subtotal = price * qty;

                it.setPrice(price);
                it.setQuantity(qty);
                it.setSubtotal(subtotal);

                total += subtotal;
                items.add(it);
            }


            // --- Check for insufficient payment BEFORE creating the bill ---
            if (paidAmount < total) {
                req.setAttribute("error", "Insufficient payment! Total is " + total + ", but you paid " + paidAmount);
                req.setAttribute("bill", null);
                req.getRequestDispatcher("/bill_summary.jsp").forward(req, resp);
                return; // Stop further processing
            }

            // --- Create BillDTO ---
            double balance = paidAmount - total;
            BillDTO bill = new BillDTO();
            bill.setCustomerId(customerId);
            bill.setItems(items);
            bill.setTotalAmount(total);
            bill.setPaidAmount(paidAmount);
            bill.setBalance(balance);
            bill.setPaymentMethod((paymentMethod == null || paymentMethod.isEmpty()) ? "CASH" : paymentMethod);

            // --- Save bill ---
            int billId;
            billId = service.createBill(bill);

            // --- Forward success ---
            req.setAttribute("message", "Bill created successfully! (Bill ID: " + billId + ")");
            req.setAttribute("billId", billId);
            req.setAttribute("bill", bill);
            req.getRequestDispatcher("/bill_summary.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Billing failed: " + e.getMessage());
            req.setAttribute("bill", null);
            req.getRequestDispatcher("/bill_summary.jsp").forward(req, resp);
        }
    }
}
