package ru.practicum.shareit.item.dto.outEntity;

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
