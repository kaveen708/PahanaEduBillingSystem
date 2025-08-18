package mapper;

import dto.BillDTO;
import dto.BillItemDTO;
import dto.ItemDTO;
import model.Bill;
import model.BillItem;
import model.Item;

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
        if (dto.getItems() != null) {
            List<BillItem> items = dto.getItems().stream()
                    .map(BillMapper::toEntityItem)
                    .collect(Collectors.toList());
            bill.setItems(items);
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
        if (entity.getItems() != null) {
            List<BillItemDTO> items = entity.getItems().stream()
                    .map(BillMapper::toDTOItem)
                    .collect(Collectors.toList());
            dto.setItems(items);
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

    // ✅ If you want Item <-> ItemDTO mapping inside BillMapper
    public static ItemDTO toItemDTO(Item item) {
        if (item == null) return null;
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setItemCode(item.getItemCode());
        dto.setItemName(item.getItemName());
        dto.setPrice(item.getPrice());
        return dto;
    }

    public static Item toItemEntity(ItemDTO dto) {
        if (dto == null) return null;
        Item item = new Item();
        item.setId(dto.getId());
        item.setItemCode(dto.getItemCode());
        item.setItemName(dto.getItemName());
        item.setPrice(dto.getPrice());
        return item;
    }

    // ✅ Convert List<Bill> to List<BillDTO>
    public static List<BillDTO> toDTOList(List<Bill> bills) {
        if (bills == null) return null;
        return bills.stream()
                .map(BillMapper::toDTO)
                .collect(Collectors.toList());
    }
}
