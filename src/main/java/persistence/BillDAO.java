package persistence;

import model.Bill;
import model.BillItem;
import util.DBConnection;

import java.sql.*;
import java.util.List;

public class BillDAO {

    private static final String INSERT_BILL =
            "INSERT INTO bills (customer_id, total_amount, paid_amount, balance, payment_method) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_BILL_ITEM =
            "INSERT INTO bill_items (bill_id, item_id, item_name, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CUSTOMER_UNITS =
            "UPDATE customers SET unit_consume = COALESCE(unit_consume,0) + ? WHERE id = ?";

    public int createBill(Bill bill) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int billId;

                try (PreparedStatement ps = conn.prepareStatement(INSERT_BILL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, bill.getCustomerId());
                    ps.setDouble(2, bill.getTotalAmount());
                    ps.setDouble(3, bill.getPaidAmount());
                    ps.setDouble(4, bill.getBalance());
                    ps.setString(5, bill.getPaymentMethod());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Bill ID not generated");
                        billId = rs.getInt(1);
                    }
                }

                // bill items
                try (PreparedStatement ps = conn.prepareStatement(INSERT_BILL_ITEM)) {
                    for (BillItem it : bill.getItems()) {
                        ps.setInt(1, billId);
                        ps.setInt(2, it.getItemId());
                        ps.setString(3, it.getItemName());
                        ps.setDouble(4, it.getPrice());
                        ps.setInt(5, it.getQuantity());
                        ps.setDouble(6, it.getSubtotal());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                // update customer's unit_consume by total qty
                int totalUnits = bill.getItems().stream().mapToInt(BillItem::getQuantity).sum();
                try (PreparedStatement ps = conn.prepareStatement(UPDATE_CUSTOMER_UNITS)) {
                    ps.setInt(1, totalUnits);
                    ps.setInt(2, bill.getCustomerId());
                    ps.executeUpdate();
                }

                conn.commit();
                return billId;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public Bill getBillById(int billId) {
        return null;
    }

    public List<Bill> getAllBills() {
        return null;
    }
}
