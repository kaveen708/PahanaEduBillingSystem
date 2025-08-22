package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.UserService;
import service.CustomerService;
import service.BillService;
import service.ItemService;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final CustomerService customerService = new CustomerService();
    private final BillService billService = new BillService();
    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int totalUsers = userService.getTotalUsers();
            int totalCustomers = customerService.getTotalCustomers();
            int totalBills = billService.getTotalBills();
            int totalItems = itemService.getTotalItems();

            req.setAttribute("totalUsers", totalUsers);
            req.setAttribute("totalCustomers", totalCustomers);
            req.setAttribute("totalBills", totalBills);
            req.setAttribute("totalItems", totalItems);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error loading dashboard: " + e.getMessage());
        }

        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }
}
