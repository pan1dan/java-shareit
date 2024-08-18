package ru.practicum.shareit.request.dto.outEntity;

import lombok.*;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

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
