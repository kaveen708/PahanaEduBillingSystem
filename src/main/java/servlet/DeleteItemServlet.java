package servlet;

import service.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {

    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        try {
            itemService.deleteItem(itemCode);
            // Redirect to the items page to reload the list after deletion
            resp.sendRedirect(req.getContextPath() + "/items");
        } catch (Exception e) {
            req.setAttribute("error", "Error deleting item: " + e.getMessage());
            req.getRequestDispatcher("/items").forward(req, resp);
        }
    }
}
