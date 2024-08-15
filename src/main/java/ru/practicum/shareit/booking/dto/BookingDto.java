package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

import java.time.LocalDateTime;

public class BookingDto {
    Long id;
    LocalDateTime start;
    LocalDateTime end;
    ItemGetDtoOut item;
    UserGetDtoOut booker;
    BookingStatus status;
}
