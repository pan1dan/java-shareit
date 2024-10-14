package ru.practicum.shareit.item.dto.outEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

@Data
@Builder
@AllArgsConstructor
public class ItemUpdateDtoOut {
    long id;
    String name;
    String description;
    boolean available;
    UserGetDtoOut owner;
    ItemRequest request;
}
