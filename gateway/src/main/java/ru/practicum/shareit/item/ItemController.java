package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.InEntity.CommentAddDtoIn;
import ru.practicum.shareit.item.InEntity.CommentParams;
import ru.practicum.shareit.item.InEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.InEntity.ItemUpdateDtoIn;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@RestController
@RequestMapping("/items")
@Slf4j
@Validated
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
                                          @RequestBody @Valid ItemAddDtoIn itemAddDtoIn) {
        log.info("POST /items: {}, {}", userId, itemAddDtoIn);
        ResponseEntity<Object> item = itemClient.addItem(userId, itemAddDtoIn);
        log.info("POST /items возвращает значение: {}", item);
        return item;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
                                             @RequestBody @Valid ItemUpdateDtoIn itemUpdateDtoIn,
                                             @PathVariable("id") @Min(0) Long itemId) {
        log.info("PATCH /items/{}: {}, {}",itemId, userId, itemUpdateDtoIn);
        itemUpdateDtoIn.setId(itemId);
        ResponseEntity<Object> item = itemClient.updateItem(userId, itemUpdateDtoIn, itemId);
        log.info("PATCH /items/{} возвращает значение: {}", itemId, item);
        return item;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
                                          @PathVariable("id") @Min(0) Long itemId) {
        log.info("GET /items/{}: {}", itemId, userId);
        ResponseEntity<Object> item = itemClient.getItem(userId, itemId);
        log.info("GET /items/{} возвращает значение {}", itemId, item);
        return item;
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId) {
        log.info("GET /items: {}", userId);
        ResponseEntity<Object> items = itemClient.getUserItems(userId);
        log.info("GET /items возвращает значение: {}", items);
        return items;
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam("text") String text) {
        log.info("GET /items/search?text={}", text);
        ResponseEntity<Object> items = itemClient.searchItem(text);
        log.info("GET /items/search?text={} возвращает значение: {}", text, items);
        return items;
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
                                             @PathVariable("itemId") @Min(0) Long itemId,
                                             @RequestBody @Valid CommentAddDtoIn commentAddDtoIn) {
        log.info("POST /items/{}/comment: {}, {}", itemId, userId, commentAddDtoIn);
        CommentParams commentParams = CommentParams.builder()
                .authorId(userId)
                .text(commentAddDtoIn.getText())
                .itemId(itemId)
                .build();
        ResponseEntity<Object> comment = itemClient.addComment(commentParams);
        log.info("POST /items/{}/comment возвращает значение: {}", itemId, comment);
        return comment;
    }


}
