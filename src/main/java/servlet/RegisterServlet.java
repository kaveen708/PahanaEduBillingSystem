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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDTO user = new UserDTO();
            user.setUsername(username);
            user.setPassword(password);

            boolean success = service.register(user);

            if (success) {
                // ✅ registration success → set session success msg
                HttpSession session = req.getSession();
                session.setAttribute("successMsg", "🎉 Registration successful! Please login.");
                resp.sendRedirect("login.jsp");
            } else {
                // ❌ already exists / failed
                req.setAttribute("errorMsg", "⚠️ Username already exists or registration failed.");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
