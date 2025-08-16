package service;

import dto.CustomerDTO;
import mapper.CustomerMapper;
import model.Customer;
import persistence.CustomerDAO;

import java.sql.SQLException;

public class CustomerService {
    private final CustomerDAO dao = new CustomerDAO();

    public CustomerDTO createCustomer(CustomerDTO dto) throws SQLException {
        validate(dto);
        Customer entity = CustomerMapper.toEntity(dto);
        int id = dao.create(entity);
        dto.setId(id);
        return dto;
    }

    private void validate(CustomerDTO dto) {
        if (dto.getAccountNumber() == null || dto.getAccountNumber().trim().isEmpty())
            throw new IllegalArgumentException("Account Number is required");
        if (dto.getName() == null || dto.getName().trim().isEmpty())
            throw new IllegalArgumentException("Name is required");
        if (dto.getAddress() == null || dto.getAddress().trim().isEmpty())
            throw new IllegalArgumentException("Address is required");
    }

    public CustomerDTO findByAccountNumber(String accountNumber) throws SQLException {
        Customer customer = dao.findByAccountNumber(accountNumber);
        return customer != null ? CustomerMapper.toDTO(customer) : null;
    }

    public CustomerDTO getCustomerByAccount(String accountNumber) {
        return null;
    }

    public CustomerDTO getByAccountNumber(String account) {
        return null;
    }
}
