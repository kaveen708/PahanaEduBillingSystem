package persistence;

import model.User;
import util.DBConnection;

import java.sql.*;

public class UserDAO {

    // Register new user
    public boolean register(User user) throws Exception {
        String sql = "INSERT INTO users (user_id, username, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUserId());   // custom ID like U001, A001
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Validate login
    public User login(String username, String password) throws Exception {
        String sql = "SELECT user_id, username, password, role FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getString("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null;
    }
}
