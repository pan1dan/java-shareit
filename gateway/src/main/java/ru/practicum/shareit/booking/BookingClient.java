package ru.practicum.shareit.booking;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.in_entity.BookingParams;
import ru.practicum.shareit.base.client.BaseClient;

@Service
@Slf4j
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addBooking(long userId, BookingAddDtoIn bookingAddDtoIn) {
        return post("", userId, bookingAddDtoIn);
    }

    public ResponseEntity<Object> approveBooking(BookingParams bookingParams) {
        Map<String, Object> parameters = Map.of(
                "bookingId", bookingParams.getId(),
                "approved", bookingParams.getApproved().toString()
        );
        return patch("/{bookingId}?approved={approved}", bookingParams.getUserId(), parameters, bookingParams);
    }

    public ResponseEntity<Object> getBookingById(long userId, long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getBookingsWithState(long userId, String bookingState) {
        Map<String, Object> parameters = Map.of(
                "state", bookingState
        );
        return get("?state={state}", userId, parameters);
    }


    public ResponseEntity<Object> getBookingsByOwner(long ownerId, String bookingState) {
        Map<String, Object> parameters = Map.of(
                "state", bookingState
        );
        return get("/owner?state={state}", ownerId, parameters);
    }


}
