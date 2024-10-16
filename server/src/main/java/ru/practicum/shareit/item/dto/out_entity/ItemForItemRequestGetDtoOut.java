package ru.practicum.shareit.item.dto.out_entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemForItemRequestGetDtoOut {
    long id;
    String name;
    long ownerId;
}
