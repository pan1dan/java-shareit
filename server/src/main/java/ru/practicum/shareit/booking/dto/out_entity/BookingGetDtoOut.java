package ru.practicum.shareit.booking.dto.out_entity;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.out_entity.ItemGetDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingGetDtoOut {
    long id;
    LocalDateTime start;
    LocalDateTime end;
    ItemGetDtoOut item;
    UserGetDtoOut booker;
    BookingStatus status;
}
