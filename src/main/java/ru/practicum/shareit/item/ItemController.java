package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.RepositoryManager;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.user.InMemoryUserRepository;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Slf4j
@Validated
public class ItemController {
    ItemService itemService;

    @Autowired
    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemAddDtoOut addItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @RequestBody @Valid ItemAddDtoIn itemAddDtoIn) {
        log.info("POST /items: {}, {}", userId, itemAddDtoIn);
        ItemAddDtoOut itemAddDtoOut = itemService.addItem(userId, itemAddDtoIn);
        log.info("POST /items возвращает значение: {}", itemAddDtoOut);
        return itemAddDtoOut;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemUpdateDtoOut updateItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestBody @Valid ItemUpdateDtoIn itemUpdateDtoIn,
                                       @PathVariable("id") Long itemId) {
        log.info("PATCH /items/{}: {}, {}",itemId, userId, itemUpdateDtoIn);
        itemUpdateDtoIn.setId(itemId);
        ItemUpdateDtoOut itemUpdateDtoOut = itemService.updateItem(userId, itemUpdateDtoIn);
        log.info("PATCH /items/{} возвращает значение: {}", itemId, itemUpdateDtoOut);
        return itemUpdateDtoOut;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemGetDtoOut getItem(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @PathVariable("id") Long itemId) {
        log.info("GET /items/{}: {}", itemId, userId);
        ItemGetDtoOut itemGetDtoOut = itemService.getItem(userId, itemId);
        log.info("GET /items/{} возвращает значение {}", itemId, itemGetDtoOut);
        return itemGetDtoOut;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemGetDtoOut> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
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

}
