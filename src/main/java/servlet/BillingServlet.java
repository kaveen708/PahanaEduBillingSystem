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
            int customerId = Integer.parseInt(req.getParameter("customerId"));
            String paymentMethod = req.getParameter("paymentMethod"); // "CASH" by UI
            double paidAmount = Double.parseDouble(req.getParameter("paidAmount"));

            String[] itemIds = req.getParameterValues("itemId");
            String[] itemNames = req.getParameterValues("itemName");
            String[] prices = req.getParameterValues("price");
            String[] quantities = req.getParameterValues("quantity");

            List<BillItemDTO> items = new ArrayList<>();
            double total = 0;

            if (itemIds != null) {
                for (int i = 0; i < itemIds.length; i++) {
                    if (itemIds[i] == null || itemIds[i].trim().isEmpty()) continue;

                    BillItemDTO it = new BillItemDTO();
                    it.setItemId(Integer.parseInt(itemIds[i]));
                    it.setItemName(itemNames[i]);
                    it.setPrice(Double.parseDouble(prices[i]));
                    int qty = (quantities[i] == null || quantities[i].isEmpty()) ? 1 : Integer.parseInt(quantities[i]);
                    it.setQuantity(qty);
                    it.setSubtotal(it.getPrice() * it.getQuantity());
                    total += it.getSubtotal();
                    items.add(it);
                }
            }

            double balance = paidAmount - total;

            BillDTO bill = new BillDTO();
            bill.setCustomerId(customerId);
            bill.setItems(items);
            bill.setTotalAmount(total);
            bill.setPaidAmount(paidAmount);
            bill.setBalance(balance);
            bill.setPaymentMethod(paymentMethod == null ? "CASH" : paymentMethod);

            int billId = service.createBill(bill);

            req.setAttribute("billId", billId);
            req.setAttribute("bill", bill);
            req.getRequestDispatcher("/bill_summary.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Billing failed: " + e.getMessage(), e);
        }
    }
}
