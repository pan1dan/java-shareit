package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestGetDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestService;

import java.util.List;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestAddDtoOut addItemRequest(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                               @RequestBody ItemRequestAddDtoIn itemRequestAddDtoIn) {
        log.info("POST /requests: {}, {}", userId, itemRequestAddDtoIn);
        ItemRequestAddDtoOut itemRequestAddDtoOut = itemRequestService.addItemRequest(userId, itemRequestAddDtoIn);
        log.info("POST /requests возвращает значение: {}", itemRequestAddDtoOut);
        return itemRequestAddDtoOut;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestGetDtoOut> getUserItemsRequests(@RequestHeader(X_SHARER_USER_ID) Long userId) {
        log.info("GET /requests: {}", userId);
        List<ItemRequestGetDtoOut> itemRequestGetDtoOutList = itemRequestService.getUserItemsRequests(userId);
        log.info("GET /requests возвращает значение: {}", itemRequestGetDtoOutList);
        return itemRequestGetDtoOutList;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestGetDtoOut> getAllItemsRequests() {
        log.info("GET /requests/all");
        List<ItemRequestGetDtoOut> itemRequestGetDtoOutList = itemRequestService.getAllItemsRequests();
        log.info("GET /requests/all возвращает значение: {}", itemRequestGetDtoOutList);
        return itemRequestGetDtoOutList;
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestGetDtoOut getItemRequestById(@PathVariable(name = "requestId") Long requestId) {
        log.info("GET /requests/{}", requestId);
        ItemRequestGetDtoOut itemRequestGetDtoOut = itemRequestService.getItemRequestById(requestId);
        log.info("GET /requests/{} возвращает значение: {}", requestId, itemRequestGetDtoOut);
        return itemRequestGetDtoOut;
    }

}
