package service;

import dto.UserDTO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    // 🔹 Register user with userId, username, password, and role
    public boolean register(UserDTO dto) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            // Check if username exists
            PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username=?");
            check.setString(1, dto.getUsername());
            ResultSet rs = check.executeQuery();
            if (rs.next()) return false; // username exists

            // Insert new user
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO users (user_id, username, password, role) VALUES (?,?,?,?)"
            );
            insert.setString(1, dto.getUserId());
            insert.setString(2, dto.getUsername());
            insert.setString(3, dto.getPassword()); // ⚠️ TODO: hash password in real apps
            insert.setString(4, dto.getRole());
            insert.executeUpdate();

            return true;
        }
    }

    // 🔹 Generate unique userId like staff001, admin001
    public String generateUserId(String role) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE role=?");
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("count");
            }
            count++; // next number
            return role + String.format("%03d", count);
        }
    }

    // 🔹 Login method
    public UserDTO login(String username, String password) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password); // ⚠️ TODO: hash check
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getString("user_id"));
                dto.setUsername(rs.getString("username"));
                dto.setRole(rs.getString("role"));
                return dto;
            }
            return null; // invalid
        }
    }

    // 🔹 New: Get total users for dashboard summary
    public int getTotalUsers() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM users");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        }
    }
}
