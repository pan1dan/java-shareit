package ru.practicum.shareit.item.dto.inEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemUpdateDtoIn {
    Long id;
    String name;
    String description;
    Boolean available;
    Long requestId;
}
