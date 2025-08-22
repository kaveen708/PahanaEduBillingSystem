package mapper;

import dto.CustomerDTO;
import model.Customer;

import java.time.LocalDateTime;

public class CustomerMapper {
    public static Customer toEntity(CustomerDTO dto) {
        if (dto == null) return null;
        Customer c = new Customer();
        c.setId(dto.getId());
        c.setAccountNumber(dto.getAccountNumber());
        c.setName(dto.getName());
        c.setAddress(dto.getAddress());
        c.setPhoneNumber(dto.getPhoneNumber());
        c.setUnitConsume((int) dto.getUnitConsume());
        c.setCreatedAt(LocalDateTime.now());
        return c;
    }

    public static CustomerDTO toDTO(Customer entity) {
        if (entity == null) return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setId(entity.getId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setUnitConsume(entity.getUnitConsume());
        
        return dto;
    }
}
