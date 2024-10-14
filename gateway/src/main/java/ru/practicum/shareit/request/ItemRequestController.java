package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.InEntity.ItemRequestAddDtoIn;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> addItemRequest(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
                                         @RequestBody @Valid ItemRequestAddDtoIn itemRequestAddDtoIn) {
        log.info("POST /requests: {}, {}", userId, itemRequestAddDtoIn);
        ResponseEntity<Object> itemRequest = itemRequestClient.addItemRequest(userId, itemRequestAddDtoIn);
        log.info("POST /requests возвращает значение: {}", itemRequest);
        return itemRequest;
    }

    @GetMapping
    public ResponseEntity<Object> getUserItemsRequests(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId) {
        log.info("GET /requests: {}", userId);
        ResponseEntity<Object> itemsRequests = itemRequestClient.getUserItemsRequests(userId);
        log.info("GET /requests возвращает значение: {}", itemsRequests);
        return itemsRequests;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemsRequests() {
        log.info("GET /requests/all");
        ResponseEntity<Object> itemsRequests = itemRequestClient.getAllItemsRequests();
        log.info("GET /requests/all возвращает значение: {}", itemsRequests);
        return itemsRequests;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@PathVariable(name = "requestId") @Min(0) Long requestId) {
        log.info("GET /requests/{}", requestId);
        ResponseEntity<Object> itemRequest = itemRequestClient.getItemRequestById(requestId);
        log.info("GET /requests/{} возвращает значение: {}", requestId, itemRequest);
        return itemRequest;
    }
}
