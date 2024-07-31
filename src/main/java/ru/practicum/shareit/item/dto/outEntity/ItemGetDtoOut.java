package ru.practicum.shareit.item.dto.outEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

@Data
@Builder
@AllArgsConstructor
public class ItemGetDtoOut {
    long id;
    String name;
    String description;
    Boolean available;
    long owner;
    ItemRequest request;
}
