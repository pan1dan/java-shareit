package ru.practicum.shareit.booking.interfaces;

import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.out_entity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.out_entity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.out_entity.BookingGetDtoOut;

import java.util.List;

public interface BookingService {
    BookingAddDtoOut addBooking(long userId, BookingAddDtoIn bookingAddDtoIn);

    BookingApproveDtoOut approveBooking(long userId, long bookingId, boolean isApproved);

    BookingGetDtoOut getBookingById(long userId, long bookingId);

    List<BookingGetDtoOut> getBookingsWithState(long userId, String bookingState);

    List<BookingGetDtoOut> getBookingsByOwner(long ownerId, String bookingState);
}
