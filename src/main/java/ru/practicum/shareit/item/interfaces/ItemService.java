package ru.practicum.shareit.item.interfaces;

import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;

import java.util.List;

public interface ItemService {

    ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoInDto);

    ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn);

    ItemGetDtoOut getItem(Long userId, Long itemId);

    List<ItemGetDtoOut> getUserItems(Long userId);

    List<ItemGetDtoOut> searchItem(String text);
}
