package persistence;

import model.Item;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private static final String INSERT_SQL = "INSERT INTO items (item_code, item_name, description, price, quantity) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM items ORDER BY id DESC";
    private static final String DELETE_SQL = "DELETE FROM items WHERE item_code = ?";
    private static final String UPDATE_SQL = "UPDATE items SET item_name = ?, description = ?, price = ?, quantity = ? WHERE item_code = ?";

    // ✅ Create Item
    public int create(Item item) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getItemCode());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, item.getQuantity());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Creating item failed, no rows affected.");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    item.setId(id);
                    return id;
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ✅ Get All Items
    public List<Item> findAll() throws SQLException {
        List<Item> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setItemCode(rs.getString("item_code"));
                item.setItemName(rs.getString("item_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                list.add(item);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // ✅ Delete Item by code
    public boolean delete(String itemCode) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setString(1, itemCode);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ✅ Update Item
    public boolean update(Item item) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, item.getItemName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getItemCode());

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // ✅ Search item by item code only
    public Item findByCode(String itemCode) throws SQLException {
        String sql = "SELECT id, item_code, item_name, price FROM items WHERE item_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, itemCode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("id"));                  // set item id
                    item.setItemCode(rs.getString("item_code"));  // set item code
                    item.setItemName(rs.getString("item_name"));  // set item name
                    item.setPrice(rs.getDouble("price"));        // set price
                    return item;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null; // not found
    }
}
