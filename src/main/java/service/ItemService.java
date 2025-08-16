package service;

import dto.ItemDTO;
import mapper.ItemMapper;
import model.Item;
import persistence.ItemDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {
    private final ItemDAO dao = new ItemDAO();

    public ItemDTO createItem(ItemDTO dto) throws SQLException {
        validate(dto);
        Item entity = ItemMapper.toEntity(dto);
        int id = dao.create(entity);
        dto.setId(id);
        return dto;
    }

    private void validate(ItemDTO dto) {
        if (dto.getItemCode() == null || dto.getItemCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Item Code is required");
        }
        if (dto.getItemName() == null || dto.getItemName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item Name is required");
        }
        if (dto.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        if (dto.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
    }

    public List<ItemDTO> findAllItems() throws SQLException {
        List<Item> items = dao.findAll();
        return items.stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());
    }


    // ✅ Added method to match "getAllItems()" usage
    public List<ItemDTO> getAllItems() throws SQLException {
        return findAllItems();
    }

    public boolean deleteItem(String itemCode) throws SQLException {
        return dao.delete(itemCode);
    }

    public boolean updateItem(ItemDTO dto) throws SQLException {
        validate(dto);
        Item entity = ItemMapper.toEntity(dto);
        return dao.update(entity);
    }

    public ItemDTO findByCodeOrName(String searchTerm) throws SQLException {
        Item item = dao.findByCodeOrName(searchTerm);
        return item != null ? ItemMapper.toDTO(item) : null;
    }

    public ItemDTO getItemByCode(String itemCode) {
        return null;
    }
}

