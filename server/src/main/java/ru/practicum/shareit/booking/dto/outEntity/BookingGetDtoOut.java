package ru.practicum.shareit.booking.dto.outEntity;

import lombok.*;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

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