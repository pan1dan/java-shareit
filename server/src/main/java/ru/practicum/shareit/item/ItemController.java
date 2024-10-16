package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.in_entity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.out_entity.*;
import ru.practicum.shareit.item.interfaces.ItemService;

import java.util.List;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemAddDtoOut addItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                 @RequestBody ItemAddDtoIn itemAddDtoIn) {
        log.info("POST /items: {}, {}", userId, itemAddDtoIn);
        ItemAddDtoOut itemAddDtoOut = itemService.addItem(userId, itemAddDtoIn);
        log.info("POST /items возвращает значение: {}", itemAddDtoOut);
        return itemAddDtoOut;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemUpdateDtoOut updateItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                       @RequestBody ItemUpdateDtoIn itemUpdateDtoIn,
                                       @PathVariable("id") Long itemId) {
        log.info("PATCH /items/{}: {}, {}",itemId, userId, itemUpdateDtoIn);
        itemUpdateDtoIn.setId(itemId);
        ItemUpdateDtoOut itemUpdateDtoOut = itemService.updateItem(userId, itemUpdateDtoIn);
        log.info("PATCH /items/{} возвращает значение: {}", itemId, itemUpdateDtoOut);
        return itemUpdateDtoOut;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemGetDtoOut getItem(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                       @PathVariable("id") Long itemId) {
        log.info("GET /items/{}: {}", itemId, userId);
        ItemGetDtoOut itemGetDtoOut = itemService.getItem(userId, itemId);
        log.info("GET /items/{} возвращает значение {}", itemId, itemGetDtoOut);
        return itemGetDtoOut;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemGetDtoOut> getUserItems(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("GET /items: {}", userId);
        List<ItemGetDtoOut> items = itemService.getUserItems(userId);
        log.info("GET /items возвращает значение: {}", items);
        return items;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemGetDtoOut> searchItem(@RequestParam("text") String text) {
        log.info("GET /items/search?text={}", text);
        List<ItemGetDtoOut> items = itemService.searchItem(text);
        log.info("GET /items/search?text={} возвращает значение: {}", text, items);
        return items;
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentAddDtoOut addComment(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                       @PathVariable("itemId") Long itemId,
                                       @RequestBody CommentAddDtoIn commentAddDtoIn) {
        log.info("POST /items/{}/comment: {}, {}", itemId, userId, commentAddDtoIn);
        CommentAddDtoOut commentAddDtoOut = itemService.addComment(userId, itemId, commentAddDtoIn);
        log.info("POST /items/{}/comment возвращает значение: {}", itemId, commentAddDtoOut);
        return commentAddDtoOut;
    }
}
