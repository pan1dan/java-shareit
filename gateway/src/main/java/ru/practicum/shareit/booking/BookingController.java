package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.in_entity.BookingParams;

import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> addBooking(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
											 @Valid @RequestBody BookingAddDtoIn bookingAddDtoIn) {
		log.info("POST /bookings: {}, {}", userId, bookingAddDtoIn);
		ResponseEntity<Object> booking = bookingClient.addBooking(userId, bookingAddDtoIn);
		log.info("POST /bookings возвращает значение: {}", booking);
		return booking;
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> approveBooking(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
												 @PathVariable(name = "bookingId") @Min(0) Long bookingId,
											     @RequestParam(name = "approved") Boolean approved) {
		log.info("PATCH /bookings/{}?approved={}: {}", bookingId, approved, userId);
		BookingParams bookingParams = BookingParams.builder()
												   .id(bookingId)
												   .userId(userId)
												   .approved(approved)
												   .build();
		ResponseEntity<Object> booking = bookingClient.approveBooking(bookingParams);
		log.info("PATCH /bookings/{}?approved={} возвращает значение: {}", bookingId,
				approved,
				booking);
		return booking;
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBookingById(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
												 @PathVariable(name = "bookingId") @Min(0) Long bookingId) {
		log.info("GET /bookings/{}: {}", bookingId, userId);
		ResponseEntity<Object> booking = bookingClient.getBookingById(userId, bookingId);
		log.info("GET /bookings/{} возвращает значение: {}", bookingId, booking);
		return booking;
	}

	@GetMapping
	public ResponseEntity<Object> getBookingsWithState(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long userId,
													   @RequestParam(value = "state",
															   defaultValue = "ALL") String bookingState) {
		log.info("GET /bookings?state={}: {}", bookingState, userId);
		ResponseEntity<Object> bookings = bookingClient.getBookingsWithState(userId, bookingState);
		log.info("GET /bookings?state={} возвращает значение: {}", bookingState, bookings);
		return bookings;
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getBookingsByOwner(@RequestHeader(X_SHARER_USER_ID) @Min(0) Long ownerId,
													 @RequestParam(value = "state",
															 defaultValue = "ALL") String bookingState
	) {
		log.info("GET /bookings/owner?state={}: {}", bookingState, ownerId);
		ResponseEntity<Object> bookings = bookingClient.getBookingsByOwner(ownerId, bookingState);
		log.info("GET /bookings/owner?state={} возвращает значение: {}", bookingState, bookings);
		return bookings;
	}
}
