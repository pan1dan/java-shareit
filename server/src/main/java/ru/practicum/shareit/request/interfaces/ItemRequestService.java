package ru.practicum.shareit.request.interfaces;

import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestGetDtoOut;

import java.util.List;

public interface ItemRequestService {
    ItemRequestAddDtoOut addItemRequest(Long userId, ItemRequestAddDtoIn itemRequestAddDtoIn);

    List<ItemRequestGetDtoOut> getUserItemsRequests(Long userId);

    List<ItemRequestGetDtoOut> getAllItemsRequests();

    ItemRequestGetDtoOut getItemRequestById(Long requestId);
}
