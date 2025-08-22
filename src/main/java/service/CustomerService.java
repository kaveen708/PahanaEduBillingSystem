package service;

import dto.CustomerDTO;
import mapper.CustomerMapper;
import model.Customer;
import persistence.CustomerDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerDAO dao = new CustomerDAO();

    // ✅ Create Customer
    public CustomerDTO createCustomer(CustomerDTO dto) throws SQLException {
        validate(dto);
        Customer entity = CustomerMapper.toEntity(dto);
        int id = dao.create(entity);
        dto.setId(id);
        return dto;
    }

    // ✅ Update Customer
    public void updateCustomer(CustomerDTO dto) throws SQLException {
        validate(dto);
        Customer entity = CustomerMapper.toEntity(dto);
        dao.update(entity);
    }

    // ✅ Delete Customer
    public void deleteCustomer(int id) throws SQLException {
        dao.delete(id);
    }

    // ✅ Get All Customers
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<Customer> customers = dao.findAll();
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Find By Account Number
    public CustomerDTO findByAccountNumber(String accountNumber) throws SQLException {
        Customer customer = dao.findByAccountNumber(accountNumber);
        return customer != null ? CustomerMapper.toDTO(customer) : null;
    }

    // ✅ Count Customers (existing)
    public int countCustomers() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM customers";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // ✅ New method: getTotalCustomers() for dashboard
    public int getTotalCustomers() throws Exception {
        return countCustomers();
    }

    // ✅ Get by Account Number
    public CustomerDTO getCustomer(String accountNumber) throws SQLException {
        Customer c = dao.findByAccountNumber(accountNumber);
        return c != null ? CustomerMapper.toDTO(c) : null;
    }

    // ✅ New method: Get Customer Name by ID
    public String getCustomerNameById(int customerId) throws SQLException {
        String sql = "SELECT name FROM customers WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // not found
    }

    // ✅ Validation
    private void validate(CustomerDTO dto) {
        if (dto.getAccountNumber() == null || dto.getAccountNumber().trim().isEmpty())
            throw new IllegalArgumentException("Account Number is required");
        if (dto.getName() == null || dto.getName().trim().isEmpty())
            throw new IllegalArgumentException("Name is required");
        if (dto.getAddress() == null || dto.getAddress().trim().isEmpty())
            throw new IllegalArgumentException("Address is required");
    }
}
