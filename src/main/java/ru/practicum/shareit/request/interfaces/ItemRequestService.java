package ru.practicum.shareit.request.interfaces;

import ru.practicum.shareit.request.dto.inEntity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.outEntity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.outEntity.ItemRequestGetDtoOut;

import java.util.List;

public interface ItemRequestService {
    ItemRequestAddDtoOut addItemRequest(Long userId, ItemRequestAddDtoIn itemRequestAddDtoIn);

    List<ItemRequestGetDtoOut> getUserItemsRequests(Long userId);

    List<ItemRequestGetDtoOut> getAllItemsRequests();

    ItemRequestGetDtoOut getItemRequestById(Long requestId);
}
