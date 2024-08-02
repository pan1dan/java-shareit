package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.request.ItemRequest;

public class ItemDto {
    Long id;
    String name;
    String description;
    Boolean available;
    String owner;
    ItemRequest request;
}
