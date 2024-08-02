package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

@Data
@Builder
@AllArgsConstructor
public class Item {
    Long id;
    String name;
    String description;
    Boolean available;
    Long owner;
    ItemRequest request;
}
