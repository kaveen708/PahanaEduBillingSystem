package service;

import dto.BillDTO;
import dto.BillItemDTO;
import mapper.BillMapper;
import model.Bill;
import model.BillItem;
import persistence.BillDAO;

import java.util.List;

/**
 * Service layer for handling billing logic.
 * Converts DTO to entity, calls DAO to persist,
 * and applies business logic like balance calculation.
 */
public class BillService {
    private final BillDAO dao = new BillDAO();

    /**
     * Creates a new bill with items, saves to DB, and updates customer usage.
     *
     * @param dto the bill data transfer object
     * @return generated bill ID
     * @throws Exception if something goes wrong
     */
    public int createBill(BillDTO dto) throws Exception {
        // Ensure business logic is applied
        calculateBalance(dto);

        // Convert DTO to Entity
        Bill entity = BillMapper.toEntity(dto);

        // Persist using DAO
        int billId = dao.createBill(entity);

        // Update generated ID back to DTO
        dto.setId(billId);
        return billId;
    }

    /**
     * Retrieves a bill by ID including its items.
     *
     * @param billId bill identifier
     * @return BillDTO with items
     * @throws Exception if not found or DB error
     */
    public BillDTO getBillById(int billId) throws Exception {
        Bill bill = dao.getBillById(billId);
        return BillMapper.toDTO(bill);
    }

    /**
     * Business logic to calculate total, balance, etc.
     */
    private void calculateBalance(BillDTO dto) {
        double total = 0.0;
        if (dto.getItems() != null) {
            for (BillItemDTO item : dto.getItems()) {
                double subtotal = item.getPrice() * item.getQuantity();
                item.setSubtotal(subtotal);
                total += subtotal;
            }
        }
        dto.setTotalAmount(total);

        // Balance = Paid - Total
        double balance = dto.getPaidAmount() - total;
        dto.setBalance(balance);
    }

    /**
     * Lists all bills (optional).
     */
    public List<BillDTO> getAllBills() throws Exception {
        List<Bill> bills = dao.getAllBills();
        return BillMapper.toDTOList(bills);
    }
}
