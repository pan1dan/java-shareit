package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.out_entity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.out_entity.BookingGetDtoOut;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@SpringBootTest
public class BookingMapperTest {
    private final LocalDateTime start = LocalDateTime.now().minusDays(4);
    private final LocalDateTime end = LocalDateTime.now().minusDays(2);
    User booker = new User(1L, "user", "user@mail.ru");
    Item item = new Item(1L, "item", "item", true, booker, null);
    BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(1L, start, end);

    @Test
    void fromBookingAddDtoInToBookingTest() {
        Booking booking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);

        Assertions.assertEquals(bookingAddDtoIn.getStart(), booking.getStart());
        Assertions.assertEquals(bookingAddDtoIn.getEnd(), booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
    }

    @Test
    void fromBookingToBookingAddDtoOutTest() {
        Booking booking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        booking.setId(1L);
        booking.setBookingStatus(BookingStatus.APPROVED);
        BookingAddDtoOut bookingAddDtoOut = BookingMapper.fromBookingToBookingAddDtoOut(booking);

        Assertions.assertEquals(booking.getStart(), bookingAddDtoOut.getStart());
        Assertions.assertEquals(booking.getEnd(), bookingAddDtoOut.getEnd());
        Assertions.assertEquals(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()), bookingAddDtoOut.getItem());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()), bookingAddDtoOut.getBooker());
        Assertions.assertEquals(booking.getBookingStatus(), bookingAddDtoOut.getStatus());
    }

    @Test
    void fromBookingToBookingApproveDtoOutTest() {
        Booking booking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        booking.setId(1L);
        booking.setBookingStatus(BookingStatus.APPROVED);
        BookingAddDtoOut bookingAddDtoOut = BookingMapper.fromBookingToBookingAddDtoOut(booking);

        Assertions.assertEquals(booking.getStart(), bookingAddDtoOut.getStart());
        Assertions.assertEquals(booking.getEnd(), bookingAddDtoOut.getEnd());
        Assertions.assertEquals(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()), bookingAddDtoOut.getItem());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()), bookingAddDtoOut.getBooker());
        Assertions.assertEquals(booking.getBookingStatus(), bookingAddDtoOut.getStatus());
    }

    @Test
    void fromBookingToBookingGetDtoOutTest() {
        Booking booking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        booking.setId(1L);
        booking.setBookingStatus(BookingStatus.APPROVED);
        BookingGetDtoOut bookingGetDtoOut = BookingMapper.fromBookingToBookingGetDtoOut(booking);

        Assertions.assertEquals(booking.getStart(), bookingGetDtoOut.getStart());
        Assertions.assertEquals(booking.getEnd(), bookingGetDtoOut.getEnd());
        Assertions.assertEquals(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()), bookingGetDtoOut.getItem());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()), bookingGetDtoOut.getBooker());
        Assertions.assertEquals(booking.getBookingStatus(), bookingGetDtoOut.getStatus());
    }
}
