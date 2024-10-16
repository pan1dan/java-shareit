package ru.practicum.shareit.request.dto.out_entity;

import lombok.*;
import ru.practicum.shareit.item.dto.out_entity.ItemForItemRequestGetDtoOut;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestGetDtoOut {
    long id;
    String description;
    LocalDateTime created;
    List<ItemForItemRequestGetDtoOut> items;
}
