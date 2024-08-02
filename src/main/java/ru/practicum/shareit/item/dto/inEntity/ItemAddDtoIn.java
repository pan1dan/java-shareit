package ru.practicum.shareit.item.dto.inEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemAddDtoIn {
    @NotBlank(message = "Поле name не может быть пустым")
    @NotNull(message = "Поле name не может быть null")
    String name;
    @NotBlank(message = "Поле description не может быть пустым")
    @NotNull(message = "Поле description не может быть null")
    String description;
    @NotNull(message = "Поле available не может быть null")
    Boolean available;
}
