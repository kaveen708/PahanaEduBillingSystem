package mapper;

import dto.ItemDTO;
import model.Item;

public class ItemMapper {
    public static Item toEntity(ItemDTO dto) {
        Item item = new Item();
        item.setItemCode(dto.getItemCode());
        item.setItemName(dto.getItemName());
        item.setDescription(dto.getDescription()); // ← add
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        return item;
    }

    public static ItemDTO toDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setItemCode(item.getItemCode());
        dto.setItemName(item.getItemName());
        dto.setDescription(item.getDescription()); // ← add
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}

