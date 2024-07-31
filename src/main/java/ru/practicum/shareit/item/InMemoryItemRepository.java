package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> itemStorage = new HashMap<>();
    private long id = 0;

    public ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoIn) {
        Item item = Item.builder()
                        .id(++id)
                        .name(itemAddDtoIn.getName())
                        .description(itemAddDtoIn.getDescription())
                        .available(itemAddDtoIn.getAvailable())
                        .owner(userId)
                        .build();
        itemStorage.put(item.getId(), item);
        return ItemMapper.fromItemToItemAddDtoOut(itemStorage.get(item.getId()));
    }

    public ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn) {
        if (!itemStorage.containsKey(itemUpdateDtoIn.getId())) {
            throw new NotFoundException("Предмет с id = " + itemUpdateDtoIn.getId() + " не был найден");
        }
        if (!userId.equals(itemStorage.get(itemUpdateDtoIn.getId()).getOwner())) {
            throw new ForbiddenException("Только хозяин предмета может обновить информацию о нём");
        }
        Item item = itemStorage.get(itemUpdateDtoIn.getId());
        if (itemUpdateDtoIn.getName() != null && !itemUpdateDtoIn.getName().isBlank()) {
            item.setName(itemUpdateDtoIn.getName());
        }
        if (itemUpdateDtoIn.getDescription() != null && !itemUpdateDtoIn.getDescription().isBlank()) {
            item.setDescription(itemUpdateDtoIn.getDescription());
        }
        if (itemUpdateDtoIn.getAvailable() != null) {
            item.setAvailable(itemUpdateDtoIn.getAvailable());
        }
        itemStorage.put(item.getId(), item);
        return ItemMapper.fromItemToItemUpdateDtoOut(item);
    }

    public ItemGetDtoOut getItem(Long userId, Long itemId) {
        if (!itemStorage.containsKey(itemId)) {
            throw new NotFoundException("Предмет с id = " + itemId + " не был найден");
        }
        return ItemMapper.fromItemToItemGetDtoOut(itemStorage.get(itemId));
    }

    public List<ItemGetDtoOut> getUserItems(Long userId) {
        List<Item> userItems = itemStorage.values()
                                             .stream()
                                             .filter(item -> Objects.equals(item.getOwner(), userId))
                                             .toList();
        return userItems.stream()
                        .map(ItemMapper::fromItemToItemGetDtoOut)
                        .toList();
    }

    public List<ItemGetDtoOut> searchItem(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        String lowerCaseText = text.toLowerCase();
        List<Item> items = itemStorage.values()
                .stream()
                .filter(item -> item.getAvailable()
                                && (item.getName().toLowerCase().contains((lowerCaseText))
                                    || item.getDescription().toLowerCase().contains(lowerCaseText)))
                .toList();
        return items.stream()
                    .map(ItemMapper::fromItemToItemGetDtoOut)
                    .toList();
    }
}
