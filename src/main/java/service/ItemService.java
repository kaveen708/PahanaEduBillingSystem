package service;

import dto.ItemDTO;
import mapper.ItemMapper;
import model.Item;
import persistence.ItemDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ItemService {
    private final ItemDAO dao = new ItemDAO();

    // ✅ Create
    public ItemDTO createItem(ItemDTO dto) throws SQLException {
        validate(dto);

        // 🔍 Check if item already exists by code
        Item existing = dao.findByCode(dto.getItemCode());
        if (existing != null) {
            throw new IllegalArgumentException("Item already exists!");
        }

        Item entity = ItemMapper.toEntity(dto);
        int id = dao.create(entity);
        dto.setId(id);
        return dto;
    }

    // ✅ Get All
    public List<ItemDTO> findAllItems() throws SQLException {
        List<Item> items = dao.findAll();
        return items.stream().map(ItemMapper::toDTO).collect(Collectors.toList());
    }

    // ✅ Delete
    public boolean deleteItem(String itemCode) throws SQLException {
        return dao.delete(itemCode);
    }

    // ✅ Update
    public boolean updateItem(ItemDTO dto) throws SQLException {
        validate(dto);
        Item entity = ItemMapper.toEntity(dto);
        return dao.update(entity);
    }

    // ✅ Search item by code
    public Item findByCode(String itemCode) throws SQLException {
        Item item = dao.findByCode(itemCode);
        if (item == null) return null;
        Item result = new Item();
        result.setId(item.getId());
        result.setItemCode(item.getItemCode());
        result.setItemName(item.getItemName());
        result.setPrice(item.getPrice());
        return result;
    }

    // ✅ Count Items
    public int countItems() throws Exception {
        String sql = "SELECT COUNT(*) AS total FROM items";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // ✅ New wrapper for dashboard
    public int getTotalItems() throws Exception {
        return countItems();
    }

    // ✅ Validate input
    private void validate(ItemDTO dto) {
        if (dto.getItemCode() == null || dto.getItemCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Item Code is required");
        }
        if (dto.getItemName() == null || dto.getItemName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item Name is required");
        }
        if (dto.getPrice() < 0) throw new IllegalArgumentException("Price must be >= 0");
        if (dto.getQuantity() < 0) throw new IllegalArgumentException("Quantity must be >= 0");
    }
}
