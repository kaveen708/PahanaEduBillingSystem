package servlet;

import dto.UserDTO;
import service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserDTO user = service.login(username, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                // set success message
                session.setAttribute("successMsg", "✅ Login successful! Welcome " + user.getUsername());
                resp.sendRedirect("dashboard.jsp");
            } else {
                // set error message
                req.setAttribute("errorMsg", "❌ Invalid username or password!");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
