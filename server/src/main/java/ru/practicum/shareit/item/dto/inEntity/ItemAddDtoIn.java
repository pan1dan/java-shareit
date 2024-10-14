package ru.practicum.shareit.item.dto.inEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemAddDtoIn {
    String name;
    String description;
    Boolean available;
    Long requestId;
}
