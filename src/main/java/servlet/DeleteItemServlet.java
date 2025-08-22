package servlet;

import service.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {

    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        HttpSession session = req.getSession();

        try {
            boolean deleted = itemService.deleteItem(itemCode);
            if (deleted) {
                session.setAttribute("message", "Item deleted successfully!");
            } else {
                session.setAttribute("error", "Item not found or could not be deleted.");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Error deleting item: " + e.getMessage());
        }

        // Always redirect to avoid form resubmission
        resp.sendRedirect(req.getContextPath() + "/items");
    }
}
