package ru.practicum.shareit.item.interfaces;

import ru.practicum.shareit.item.dto.in.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.in.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.out.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.out.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.out.ItemUpdateDtoOut;

import java.util.List;

public interface ItemRepository {

    ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoIn);

    ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn);

    ItemGetDtoOut getItem(Long userId, Long itemId);

    List<ItemGetDtoOut> getUserItems(Long userId);

    List<ItemGetDtoOut> searchItem(String text);
}
