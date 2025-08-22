package servlet;

import dto.BillDTO;
import service.BillService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/billDetails")
public class BillDetailsServlet extends HttpServlet {
    private final BillService billService = new BillService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerId = req.getParameter("customerId");
        String paymentMethod = req.getParameter("paymentMethod");
        String date = req.getParameter("date"); // format: yyyy-MM-dd

        try {
            List<BillDTO> bills = billService.searchBills(customerId, paymentMethod, date);
            req.setAttribute("bills", bills);
        } catch (Exception e) {
            req.setAttribute("error", "Error fetching bills: " + e.getMessage());
        }

        req.getRequestDispatcher("bill_details.jsp").forward(req, resp);
    }
}
