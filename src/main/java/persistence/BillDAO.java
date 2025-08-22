package persistence;

import dto.BillDTO;
import model.Bill;
import model.BillItem;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillDAO {

    private static final String INSERT_BILL =
            "INSERT INTO bills (customer_id, total_amount, paid_amount, balance, payment_method) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_BILL_ITEM =
            "INSERT INTO bill_items (bill_id, item_id, item_name, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CUSTOMER_UNITS =
            "UPDATE customers SET unit_consume = COALESCE(unit_consume,0) + ? WHERE id = ?";

    // Create bill
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

                // Insert bill items
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

                // Update customer unit consumption
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

    public List<Bill> searchBills(String customerId, String paymentMethod, String date) throws SQLException, ClassNotFoundException {
        List<Bill> bills = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT id, customer_id, total_amount, paid_amount, balance, payment_method, created_at FROM bills WHERE 1=1"
        );

        if (customerId != null && !customerId.isEmpty()) sql.append(" AND customer_id LIKE ?");
        if (paymentMethod != null && !paymentMethod.isEmpty()) sql.append(" AND payment_method = ?");
        if (date != null && !date.isEmpty()) sql.append(" AND DATE(created_at) = ?");
        sql.append(" ORDER BY created_at DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (customerId != null && !customerId.isEmpty()) ps.setString(index++, "%" + customerId + "%");
            if (paymentMethod != null && !paymentMethod.isEmpty()) ps.setString(index++, paymentMethod);
            if (date != null && !date.isEmpty()) ps.setString(index++, date);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setId(rs.getInt("id"));
                    bill.setCustomerId(rs.getInt("customer_id"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));
                    bill.setPaidAmount(rs.getDouble("paid_amount"));
                    bill.setBalance(rs.getDouble("balance"));
                    bill.setPaymentMethod(rs.getString("payment_method"));
                    bill.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                    // Do NOT load items here if you only want bills
                    // bill.setItems(getBillItems(bill.getId(), conn));

                    bills.add(bill);
                }
            }
        }

        return bills;
    }


    public List<Bill> getAllBills() {
        return null;
    }

    public int saveBill(BillDTO bill) {
        return 0;
    }
}
