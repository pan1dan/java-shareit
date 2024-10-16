package ru.practicum.shareit.item.interfaces;

import ru.practicum.shareit.item.dto.in_entity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.out_entity.*;

import java.util.List;

public interface ItemService {

    ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoInDto);

    ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn);

    ItemGetDtoOut getItem(Long userId, Long itemId);

    List<ItemGetDtoOut> getUserItems(Long userId);

    List<ItemGetDtoOut> searchItem(String text);

    CommentAddDtoOut addComment(Long userId, Long itemId, CommentAddDtoIn commentAddDtoIn);
}
