package ru.practicum.shareit.booking.dto.inEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingAddDtoIn {
    @NotNull
    Long itemId;
    @NotNull
    LocalDateTime start;
    @NotNull
    LocalDateTime end;

}
