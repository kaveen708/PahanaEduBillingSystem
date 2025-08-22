package service;

import dto.BillDTO;
import dto.BillItemDTO;
import mapper.BillMapper;
import model.Bill;
import persistence.BillDAO;

import java.util.List;

public class BillService {
    private final BillDAO dao = new BillDAO();

    public int createBill(BillDTO dto) throws Exception {
        calculateBalance(dto);
        Bill entity = BillMapper.toEntity(dto);
        int billId = dao.createBill(entity);
        dto.setId(billId);
        return billId;
    }

    public BillDTO getBillById(int billId) throws Exception {
        Bill bill = dao.searchBills(String.valueOf(billId), null, null)
                .stream().findFirst().orElse(null);
        return BillMapper.toDTO(bill);
    }

    public List<BillDTO> getAllBills() throws Exception {
        List<Bill> bills = dao.getAllBills();
        return BillMapper.toDTOList(bills);
    }

    public List<BillDTO> searchBills(String customerId, String paymentMethod, String date) throws Exception {
        List<Bill> bills = dao.searchBills(customerId, paymentMethod, date);
        return BillMapper.toDTOList(bills);
    }

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
        double balance = dto.getPaidAmount() - total;
        dto.setBalance(balance);
    }

    public int getTotalBills() throws Exception {
        return getAllBills().size();
    }


}
