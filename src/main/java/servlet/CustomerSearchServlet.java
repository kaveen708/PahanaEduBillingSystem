package servlet;

import dto.CustomerDTO;
import service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/searchCustomer")
public class CustomerSearchServlet extends HttpServlet {
    private final CustomerService service = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String account = req.getParameter("accountNumber");
        resp.setContentType("application/json");
        try {
            // You already had a findByAccountNumber in your service/dao
            CustomerDTO c = service.findByAccountNumber(account);
            if (c == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Customer not found\"}");
                return;
            }
            resp.getWriter().write(
                    String.format("{\"id\":%d,\"accountNumber\":\"%s\",\"name\":\"%s\"}",
                            c.getId(), c.getAccountNumber(), escape(c.getName()))
            );
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\""+escape(e.getMessage())+"\"}");
        }
    }

    private String escape(String s){ return s==null?"":s.replace("\"","\\\""); }
}
