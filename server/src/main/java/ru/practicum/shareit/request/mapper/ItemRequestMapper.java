package ru.practicum.shareit.request.mapper;

import ru.practicum.shareit.item.dto.out_entity.ItemForItemRequestGetDtoOut;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestGetDtoOut;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public class ItemRequestMapper {
    public static ItemRequest fromItemAddDtoInToItemRequest(User user, ItemRequestAddDtoIn itemRequestAddDtoIn) {
        return ItemRequest.builder()
                          .requester(user)
                          .description(itemRequestAddDtoIn.getDescription())
                          .build();
    }

    public static ItemRequestAddDtoOut fromItemRequestToItemRequestAddDtoOut(ItemRequest itemRequest) {
        return ItemRequestAddDtoOut.builder()
                                   .id(itemRequest.getId())
                                   .description(itemRequest.getDescription())
                                   .created(itemRequest.getCreated())
                                   .requester(UserMapper.fromUserToUserGetDtoOut(itemRequest.getRequester()))
                                   .build();
    }

    public static ItemRequestGetDtoOut fromItemRequestToItemRequestGetDtoOut(ItemRequest itemRequest, List<Item> items) {
        List<ItemForItemRequestGetDtoOut> itemForItemRequestGetDtoOut = items.stream()
                .map(ItemMapper::fromItemToItemForItemRequestGetDtoOut)
                .toList();
        return ItemRequestGetDtoOut.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemForItemRequestGetDtoOut)
                .build();
    }
}
