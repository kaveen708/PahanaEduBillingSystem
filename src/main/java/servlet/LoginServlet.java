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

                // ✅ Instead of redirecting directly to dashboard
                session.setAttribute("login_success", "Welcome " + user.getUsername() + "!");
                resp.sendRedirect("login.jsp");
            }
            else {
                // ❌ Wrong credentials
                HttpSession session = req.getSession();
                session.setAttribute("login_error", "Invalid username or password!");
                resp.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
