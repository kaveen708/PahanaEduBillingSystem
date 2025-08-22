package mapper;

import dto.BillDTO;
import dto.BillItemDTO;
import model.Bill;
import model.BillItem;

import java.util.List;
import java.util.stream.Collectors;

public class BillMapper {

    public static Bill toEntity(BillDTO dto) {
        if (dto == null) return null;
        Bill bill = new Bill();
        bill.setId(dto.getId());
        bill.setCustomerId(dto.getCustomerId());
        bill.setTotalAmount(dto.getTotalAmount());
        bill.setPaidAmount(dto.getPaidAmount());
        bill.setBalance(dto.getBalance());
        bill.setPaymentMethod(dto.getPaymentMethod());
        bill.setCreatedAt(dto.getCreatedAt());
        if (dto.getItems() != null) {
            bill.setItems(dto.getItems().stream().map(BillMapper::toEntityItem).collect(Collectors.toList()));
        }
        return bill;
    }

    public static BillItem toEntityItem(BillItemDTO dto) {
        if (dto == null) return null;
        BillItem item = new BillItem();
        item.setId(dto.getId());
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        item.setSubtotal(dto.getSubtotal());
        return item;
    }

    public static BillDTO toDTO(Bill entity) {
        if (entity == null) return null;
        BillDTO dto = new BillDTO();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setPaidAmount(entity.getPaidAmount());
        dto.setBalance(entity.getBalance());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setCreatedAt(entity.getCreatedAt());
        if (entity.getItems() != null) {
            dto.setItems(entity.getItems().stream().map(BillMapper::toDTOItem).collect(Collectors.toList()));
        }
        return dto;
    }

    public static BillItemDTO toDTOItem(BillItem entity) {
        if (entity == null) return null;
        BillItemDTO dto = new BillItemDTO();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }

    public static List<BillDTO> toDTOList(List<Bill> bills) {
        if (bills == null) return null;
        return bills.stream().map(BillMapper::toDTO).collect(Collectors.toList());
    }
}
