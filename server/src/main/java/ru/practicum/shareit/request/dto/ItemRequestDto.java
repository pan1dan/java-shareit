package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRequestDto {
    Long id;
    String description;
    LocalDateTime created;
    List<Item> items;
}
