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
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("delete".equals(action)) {
                String id = req.getParameter("id");
                try {
                    customerService.deleteCustomer(Integer.parseInt(id));
                    req.setAttribute("message", "Customer deleted successfully!");
                    req.setAttribute("messageType", "success");
                } catch (SQLException e) {
                    req.setAttribute("message", "Error deleting customer: " + e.getMessage());
                    req.setAttribute("messageType", "error");
                }
            }
            // default: show list
            List<CustomerDTO> customers = customerService.getAllCustomers();
            req.setAttribute("customers", customers);
            req.getRequestDispatcher("add_customer.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        CustomerDTO dto = new CustomerDTO();
        dto.setId(id != null && !id.isEmpty() ? Integer.parseInt(id) : 0);
        dto.setAccountNumber(req.getParameter("accountNumber"));
        dto.setName(req.getParameter("name"));
        dto.setAddress(req.getParameter("address"));
        dto.setPhoneNumber(req.getParameter("phoneNumber"));
        dto.setUnitConsume(Integer.parseInt(req.getParameter("unitConsume")));

        try {
            if (dto.getId() == 0) {
                customerService.createCustomer(dto);
                req.setAttribute("message", "Customer added successfully!");
                req.setAttribute("messageType", "success");
            } else {
                customerService.updateCustomer(dto);
                req.setAttribute("message", "Customer updated successfully!");
                req.setAttribute("messageType", "success");
            }
        } catch (SQLException | IllegalArgumentException e) {
            req.setAttribute("message", "Error: " + e.getMessage());
            req.setAttribute("messageType", "error");
        }

        // Always reload the list and forward to JSP to show messages
        try {
            List<CustomerDTO> customers = customerService.getAllCustomers();
            req.setAttribute("customers", customers);
            req.getRequestDispatcher("add_customer.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
