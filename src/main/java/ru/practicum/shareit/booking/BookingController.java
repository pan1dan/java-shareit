package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.inEntity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.outEntity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingService;

import java.util.List;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@RestController
@RequestMapping(path = "/bookings")
@Slf4j
@Validated
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BookingAddDtoOut addBooking(@RequestHeader(X_SHARER_USER_ID) long userId,
                                       @Valid @RequestBody BookingAddDtoIn bookingAddDtoIn) {
        log.info("POST /bookings: {}, {}", userId, bookingAddDtoIn);
        BookingAddDtoOut bookingAddDtoOut = bookingService.addBooking(userId, bookingAddDtoIn);
        log.info("POST /bookings возвращает значение: {}", bookingAddDtoOut);
        return bookingAddDtoOut;
    }

    @PatchMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingApproveDtoOut approveBooking(@RequestHeader(X_SHARER_USER_ID) long userId,
                                               @PathVariable(name = "bookingId") long bookingId,
                                               @RequestParam(name = "approved") boolean approved) {
        log.info("PATCH /bookings/{}?approved={}: {}", bookingId, approved, userId);
        BookingApproveDtoOut bookingApproveDtoOut = bookingService.approveBooking(userId, bookingId, approved);
        log.info("PATCH /bookings/{}?approved={} возвращает значение: {}", bookingId,
                                                                           approved,
                                                                           bookingApproveDtoOut);
        return bookingApproveDtoOut;
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingGetDtoOut getBookingById(@RequestHeader(X_SHARER_USER_ID) long userId,
                                           @PathVariable(name = "bookingId") long bookingId) {
        log.info("GET /bookings/{}: {}", bookingId, userId);
        BookingGetDtoOut bookingGetDtoOut = bookingService.getBookingById(userId, bookingId);
        log.info("GET /bookings/{} возвращает значение: {}", bookingId, bookingGetDtoOut);
        return bookingGetDtoOut;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingGetDtoOut> getBookingsWithState(@RequestHeader(X_SHARER_USER_ID) long userId,
                                                           @RequestParam(value = "state",
                                                                         defaultValue = "ALL") String bookingState) {
        log.info("GET /bookings?state={}: {}", bookingState, userId);
        List<BookingGetDtoOut> bookingGetDtoOutList = bookingService.getBookingsWithState(userId, bookingState);
        log.info("GET /bookings?state={} возвращает значение: {}", bookingState, bookingGetDtoOutList);
        return bookingGetDtoOutList;
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingGetDtoOut> getBookingsByOwner(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                                                     @RequestParam(value = "state",
                                                                   defaultValue = "ALL") String bookingState) {
        log.info("GET /bookings/owner?state={}: {}", bookingState, ownerId);
        List<BookingGetDtoOut> bookingGetDtoOutList = bookingService.getBookingsByOwner(ownerId, bookingState);
        log.info("GET /bookings/owner?state={} возвращает значение: {}", bookingState, bookingGetDtoOutList);
        return bookingGetDtoOutList;
    }
}
