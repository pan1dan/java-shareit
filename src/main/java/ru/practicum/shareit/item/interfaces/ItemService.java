package ru.practicum.shareit.item.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.dto.inEntity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.*;

import java.util.List;

public interface ItemService {

    ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoInDto);

    ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn);

    ItemGetDtoOut getItem(Long userId, Long itemId);

    List<ItemGetDtoOut> getUserItems(Long userId);

    List<ItemGetDtoOut> searchItem(String text, Pageable pageable);

    CommentAddDtoOut addComment(Long userId, Long itemId, CommentAddDtoIn commentAddDtoIn);
}
