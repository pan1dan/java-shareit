package ru.practicum.shareit.item.dto.out_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;

@Data
@Builder
@AllArgsConstructor
public class ItemAddDtoOut {
    long id;
    String name;
    String description;
    boolean available;
    UserGetDtoOut owner;
    Long requestId;
}
