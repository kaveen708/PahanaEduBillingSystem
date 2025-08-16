package persistence;

import dto.CustomerDTO;
import model.Customer;
import util.DBConnection;

import java.sql.*;

public class CustomerDAO {
    private static final String INSERT_SQL =
            "INSERT INTO customers (account_number, name, address, phone_number, unit_consume) VALUES (?, ?, ?, ?, ?)";

    public int create(Customer customer) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getAccountNumber());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());
            ps.setDouble(5, customer.getUnitConsume());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Creating customer failed, no rows affected.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    customer.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Customer findByAccountNumber(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM customers WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setId(rs.getInt("id"));
                    c.setAccountNumber(rs.getString("account_number"));
                    c.setName(rs.getString("name"));
                    c.setAddress(rs.getString("address"));
                    c.setPhoneNumber(rs.getString("phone_number"));
                    c.setUnitConsume(rs.getInt("unit_consume"));
                    return c;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Customer getCustomerById(int id) {
        return null;
    }

    public class CustomerDTOBuilder {
        public CustomerDTO findByAccountNumber(String accountNumber) throws Exception {
            String sql = "SELECT account_number, name FROM customers WHERE account_number = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, accountNumber);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    CustomerDTO c = new CustomerDTO();
                    c.setAccountNumber(rs.getString("account_number"));
                    c.setName(rs.getString("name"));
                    return c;
                }
                return null;
            }
        }
    }
}
