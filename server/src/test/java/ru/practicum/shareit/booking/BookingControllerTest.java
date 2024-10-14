package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.inEntity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.outEntity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.model.BookingState;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.Utility.X_SHARER_USER_ID;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @MockBean
    BookingService bookingService;

    BookingAddDtoIn bookingAddDtoIn;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        long itemId = 1;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        bookingAddDtoIn = new BookingAddDtoIn(itemId, start, end);

    }

    @Test
    void create() throws Exception {
        long userId = 1L;
        when(bookingService.addBooking(userId, bookingAddDtoIn)).thenReturn(new BookingAddDtoOut());
        mockMvc.perform(post("/bookings")
                        .header(X_SHARER_USER_ID, userId)
                        .content(mapper.writeValueAsString(bookingAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new BookingAddDtoOut())));
        verify(bookingService, times(1)).addBooking(userId, bookingAddDtoIn);
    }

    @Test
    void testGetAllPastBookings() throws Exception {
        String state = "PAST";
        long userId = 1L;

        when(bookingService.getBookingsWithState(userId, BookingState.PAST.name())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsWithState(userId, BookingState.PAST.name());
    }

    @Test
    void testGetAllCurrentBookings() throws Exception {
        String state = "CURRENT";
        long userId = 1L;

        when(bookingService.getBookingsWithState(userId, BookingState.CURRENT.name())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsWithState(userId, BookingState.CURRENT.name());
    }

    @Test
    void testGetAllFutureBookings() throws Exception {
        String state = "FUTURE";
        long userId = 1L;

        when(bookingService.getBookingsWithState(userId, BookingState.FUTURE.name())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsWithState(userId, BookingState.FUTURE.name());
    }

    @Test
    void testGetAllRejectedBookings() throws Exception {
        String state = "REJECTED";
        long userId = 1L;

        when(bookingService.getBookingsWithState(userId, BookingState.REJECTED.name())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsWithState(userId, BookingState.REJECTED.name());
    }

    @Test
    void testGetAllWaitingBookings() throws Exception {
        String state = "WAITING";
        long userId = 1L;

        when(bookingService.getBookingsWithState(userId, BookingState.WAITING.name())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsWithState(userId, BookingState.WAITING.name());
    }

    @Test
    void testGetById() throws Exception {
        long bookingId = 1L;
        long userId = 1L;

        when(bookingService.getBookingById(userId, bookingId)).thenReturn(new BookingGetDtoOut());
        mockMvc.perform(get("/bookings/{bookingId}", bookingId)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new BookingGetDtoOut())));
        verify(bookingService, times(1)).getBookingById(userId, bookingId);
    }

    @Test
    void testGetOwnerBookings() throws Exception {
        BookingState bookingState = BookingState.CURRENT;
        String state = bookingState.name();

        long userId = 1L;

        when(bookingService.getBookingsByOwner(userId, state)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/bookings/owner")
                        .param("state", state)
                        .header(X_SHARER_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
        verify(bookingService, times(1)).getBookingsByOwner(userId, state);
        verify(bookingService, atMostOnce()).getBookingsByOwner(anyLong(), anyString());
    }

    @Test
    void testApprovedBooking() throws Exception {
        long userId = 1L;
        long bookingId = 1L;
        boolean approve = true;
        when(bookingService.approveBooking(userId, bookingId, approve)).thenReturn(new BookingApproveDtoOut());
        mockMvc.perform(patch("/bookings/{bookingId}", bookingId)
                        .param("approved", String.valueOf(approve))
                        .header(X_SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new BookingApproveDtoOut())));
        verify(bookingService, times(1)).approveBooking(userId, bookingId, approve);
    }

    @Test
    void testNotApprovedBooking() throws Exception {
        long userId = 1L;
        long bookingId = 1L;
        boolean approve = false;
        when(bookingService.approveBooking(userId, bookingId, approve)).thenReturn(new BookingApproveDtoOut());
        mockMvc.perform(patch("/bookings/{bookingId}", bookingId)
                        .param("approved", String.valueOf(approve))
                        .header(X_SHARER_USER_ID, userId)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new BookingApproveDtoOut())));
    }

}