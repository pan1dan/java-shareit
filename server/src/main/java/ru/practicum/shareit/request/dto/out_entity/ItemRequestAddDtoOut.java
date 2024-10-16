package ru.practicum.shareit.request.dto.out_entity;

import lombok.*;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestAddDtoOut {
    long id;
    String description;
    UserGetDtoOut requester;
    LocalDateTime created;
}
