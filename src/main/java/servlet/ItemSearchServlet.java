package servlet;

import model.Item;
import service.ItemService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/searchItem")
public class ItemSearchServlet extends HttpServlet {
    private final ItemService service = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getParameter("q");
        resp.setContentType("application/json");

        if (code == null || code.trim().isEmpty()) {
            resp.getWriter().write("{\"error\":\"Enter item code\"}");
            return;
        }

        try {
            Item item = service.findByCode(code);
            if (item == null) {
                resp.getWriter().write("{\"error\":\"Item not found\"}");
                return;
            }

            // Only return itemName and price
            String json = String.format(
                    "{\"itemName\":\"%s\", \"price\":%.2f}",
                    escape(item.getItemName()),
                    item.getPrice()
            );
            resp.getWriter().write(json);

        } catch (SQLException e) {
            resp.getWriter().write("{\"error\":\"" + escape(e.getMessage()) + "\"}");
        }
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }
}
