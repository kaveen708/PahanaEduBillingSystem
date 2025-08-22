package servlet;

import dto.UserDTO;
import service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role"); // new role field

        try {
            // Generate unique ID based on role
            String userId = service.generateUserId(role);
            // e.g., staff001, admin001

            UserDTO user = new UserDTO();
            user.setUserId(userId);
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);

            boolean success = service.register(user); // register method should save to DB

            if (success) {
                req.getSession().setAttribute("reg_success",
                        "Registration successful! Your ID is: " + userId);
                resp.sendRedirect(req.getContextPath() + "/register.jsp");
            } else {
                req.setAttribute("reg_error", "Username already exists!");
                req.getRequestDispatcher("/register.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            req.setAttribute("reg_error", "Registration failed: " + e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
