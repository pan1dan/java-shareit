package ru.practicum.shareit.item.InEntity;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemUpdateDtoIn {
    @Positive
    Long id;
    String name;
    String description;
    Boolean available;
    Long requestId;
}