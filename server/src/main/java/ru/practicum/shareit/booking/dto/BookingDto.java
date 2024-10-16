package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDto {
    LocalDateTime start;
    LocalDateTime end;
    Long itemId;
    User booker;
}
