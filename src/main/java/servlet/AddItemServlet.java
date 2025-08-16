package servlet;

import dto.ItemDTO;
import service.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/items")
public class AddItemServlet extends HttpServlet {

    private final ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load items only if action=show parameter is present
        String action = req.getParameter("action");
        if ("show".equalsIgnoreCase(action)) {
            loadItems(req);
        }
        req.getRequestDispatcher("/add_item.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String description = req.getParameter("description");
        double price = 0;
        int quantity = 0;

        try {
            price = Double.parseDouble(req.getParameter("price"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number format for price or quantity.");
            loadItems(req);
            req.getRequestDispatcher("/add_item.jsp").forward(req, resp);
            return;
        }

        ItemDTO dto = new ItemDTO(itemCode, itemName, description, price, quantity);

        try {
            itemService.createItem(dto);
            req.setAttribute("message", "Item added successfully!");
        } catch (Exception e) {
            req.setAttribute("error", "Error adding item: " + e.getMessage());
        }

        loadItems(req);
        req.getRequestDispatcher("/add_item.jsp").forward(req, resp);
    }

    private void loadItems(HttpServletRequest req) {
        try {
            List<ItemDTO> items = itemService.findAllItems();
            req.setAttribute("items", items);
        } catch (SQLException e) {
            req.setAttribute("error", "Failed to load items: " + e.getMessage());
        }
    }
}
