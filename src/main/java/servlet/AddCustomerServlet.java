package servlet;

import dto.CustomerDTO;
import service.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String accountNumber = req.getParameter("accountNumber");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        String unitsConsumedStr = req.getParameter("unitsConsumed");
        double unitsConsumed = 0;

        if (unitsConsumedStr != null && !unitsConsumedStr.trim().isEmpty()) {
            try {
                unitsConsumed = Double.parseDouble(unitsConsumedStr.trim());
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Units Consumed must be a valid number");
                req.getRequestDispatcher("/add_customer.jsp").forward(req, resp);
                return; // Stop further processing
            }
        }

        CustomerDTO dto = new CustomerDTO(accountNumber, name, address, telephone, unitsConsumed);

        try {
            CustomerDTO created = customerService.createCustomer(dto);
            req.setAttribute("customer", created);
            req.setAttribute("message", "Customer added successfully!");
            req.getRequestDispatcher("/add_customer.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/add_customer.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/add_customer.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add_customer.jsp").forward(req, resp);
    }
}
