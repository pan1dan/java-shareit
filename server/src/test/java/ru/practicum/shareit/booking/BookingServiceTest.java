package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.out_entity.BookingAddDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookingServiceTest {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private Item item;
    private User owner;
    private User booker;
    private final LocalDateTime start = LocalDateTime.now().plusDays(1);
    private final LocalDateTime end = LocalDateTime.now().plusDays(2);

    @BeforeEach
    void initialization() {
        booker = new User(null, "James", "qe_qe@mail.ru");
        owner = new User(null, "Bob", "bob@mail.ru");
        item = new Item(null, "book", "red", true, owner, new ItemRequest());
        owner = userRepository.save(owner);
        booker = userRepository.save(booker);
        itemRepository.save(item);
    }

    @Test
    public void testAddBooking() {
        BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(item.getId(),start, end);
        Booking newBooking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        newBooking.setBookingStatus(BookingStatus.WAITING);
        BookingAddDtoOut bookingAddDtoOut = BookingMapper.fromBookingToBookingAddDtoOut(bookingRepository.save(newBooking));

        Assertions.assertEquals(bookingAddDtoOut.getStart(), start);
        Assertions.assertEquals(bookingAddDtoOut.getEnd(), end);
        Assertions.assertEquals(bookingAddDtoOut.getItem().getId(), item.getId());
        Assertions.assertEquals(bookingAddDtoOut.getBooker().getId(), booker.getId());
        Assertions.assertEquals(bookingAddDtoOut.getStatus(), BookingStatus.WAITING);
    }

    @Test
    public void testApproveBooking() {
        BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(item.getId(),start, end);
        Booking newBooking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        newBooking.setBookingStatus(BookingStatus.WAITING);
        Booking booking = bookingRepository.save(newBooking);

        booking.setBookingStatus(BookingStatus.APPROVED);
        Booking updateBooking = bookingRepository.save(booking);
        Assertions.assertEquals(updateBooking.getBookingStatus(), BookingStatus.APPROVED);
    }

    @Test
    public void testGetBookingById() {
        BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(item.getId(),start, end);
        Booking newBooking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        newBooking.setBookingStatus(BookingStatus.WAITING);
        bookingRepository.save(newBooking);

        Booking bookingGet = bookingRepository.findById(newBooking.getId())
                .orElseThrow(() -> new NotFoundException("Не было найдено бронирование " +
                        "с id = " + newBooking.getId()));
        Assertions.assertEquals(newBooking.getId(), bookingGet.getId());
    }
}
