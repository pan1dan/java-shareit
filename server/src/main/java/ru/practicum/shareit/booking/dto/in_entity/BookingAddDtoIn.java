package ru.practicum.shareit.booking.dto.in_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class BookingAddDtoIn {
    Long itemId;
    LocalDateTime start;
    LocalDateTime end;

}
