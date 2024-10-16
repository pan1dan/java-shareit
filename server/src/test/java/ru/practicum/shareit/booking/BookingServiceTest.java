package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.out_entity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.out_entity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.out_entity.BookingGetDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.interfaces.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BookingServiceTest {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final BookingService bookingService;

    private Item item;
    private User owner;
    private User booker;
    private ItemRequest itemRequest;
    private final LocalDateTime start = LocalDateTime.now().plusDays(1);
    private final LocalDateTime end = LocalDateTime.now().plusDays(2);

    @BeforeEach
    void initialization() {
        booker = new User(null, "James", "qe_qe@mail.ru");
        booker = userRepository.save(booker);
        owner = new User(null, "Bob", "bob@mail.ru");
        owner = userRepository.save(owner);
        itemRequest = new ItemRequest(null, "itemRequest", owner, start);
        itemRequest = itemRequestRepository.save(itemRequest);
        item = new Item(null, "book", "red", true, owner, itemRequest);
        item = itemRepository.save(item);
    }

    @AfterEach
    void clean() {
        bookingRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddBooking() {
        BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(item.getId(),start, end);
        BookingAddDtoOut bookingAddDtoOut = bookingService.addBooking(owner.getId(), bookingAddDtoIn);
        Optional<Booking> bookingFromData = bookingRepository.findById(bookingAddDtoOut.getId());
        Booking booking = bookingFromData.get();

        Assertions.assertEquals(bookingAddDtoOut.getId(), booking.getId());
        Assertions.assertEquals(bookingAddDtoOut.getStart(), booking.getStart());
        Assertions.assertEquals(bookingAddDtoOut.getEnd(), booking.getEnd());
        Assertions.assertEquals(bookingAddDtoOut.getItem().getId(), booking.getItem().getId());
        Assertions.assertEquals(bookingAddDtoOut.getBooker().getId(), booking.getBooker().getId());
    }

    @Test
    public void testApproveBooking() {
        BookingAddDtoIn bookingAddDtoIn = new BookingAddDtoIn(item.getId(),start, end);
        BookingAddDtoOut bookingAddDtoOut = bookingService.addBooking(owner.getId(), bookingAddDtoIn);

        BookingApproveDtoOut bookingApproveDtoOut1 = bookingService.approveBooking(owner.getId(),
                                                                                  bookingAddDtoOut.getId(),
                                                                                  true);
        Booking booking1 = bookingRepository.findById(bookingAddDtoOut.getId()).get();
        Assertions.assertEquals(BookingStatus.APPROVED, booking1.getBookingStatus());

        BookingApproveDtoOut bookingApproveDtoOut2 = bookingService.approveBooking(owner.getId(),
                                                                                   bookingAddDtoOut.getId(),
                                                                                   false);
        Booking booking2 = bookingRepository.findById(bookingAddDtoOut.getId()).get();
        Assertions.assertEquals(BookingStatus.REJECTED, booking2.getBookingStatus());
    }

    @Test
    public void testGetBookingById() {
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        BookingGetDtoOut bookingGetDtoOut = bookingService.getBookingById(owner.getId(), booking.getId());

        Assertions.assertEquals(booking.getId(), bookingGetDtoOut.getId());
        Assertions.assertEquals(booking.getStart(), bookingGetDtoOut.getStart());
        Assertions.assertEquals(booking.getEnd(), bookingGetDtoOut.getEnd());
        Assertions.assertEquals(booking.getItem().getId(), bookingGetDtoOut.getItem().getId());
        Assertions.assertEquals(booking.getBooker().getId(), bookingGetDtoOut.getBooker().getId());
        Assertions.assertEquals(booking.getBookingStatus(), bookingGetDtoOut.getStatus());
    }

    @Test
    void getBookingsWithStateTest() {
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<BookingGetDtoOut> bookingList = bookingService.getBookingsWithState(booker.getId(),
                                                                                 BookingState.ALL.name());

        Assertions.assertEquals(booking.getId(), bookingList.getFirst().getId());
    }

    @Test
    void getBookingsByOwnerTest() {
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<BookingGetDtoOut> bookingList = bookingService.getBookingsByOwner(item.getOwner().getId(),
                                                                               BookingState.ALL.name());
        Assertions.assertEquals(booking.getId(), bookingList.getFirst().getId());
    }
}
