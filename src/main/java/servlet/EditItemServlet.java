package servlet;

import dto.ItemDTO;
import service.ItemService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {

    private final ItemService itemService = new ItemService();

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
            req.getRequestDispatcher("/items").forward(req, resp);
            return;
        }

        ItemDTO dto = new ItemDTO(itemCode, itemName, description, price, quantity);

        try {
            itemService.updateItem(dto);
            req.setAttribute("message", "Item updated successfully!");
        } catch (Exception e) {
            req.setAttribute("error", "Error updating item: " + e.getMessage());
        }

        req.getRequestDispatcher("/items").forward(req, resp);
    }
}
