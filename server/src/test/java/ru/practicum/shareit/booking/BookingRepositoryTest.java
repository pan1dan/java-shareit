package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingRepositoryTest {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private LocalDateTime start = LocalDateTime.now().minusDays(4);
    private LocalDateTime end = LocalDateTime.now().minusDays(2);

    @Test
    void findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDescTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                          .findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(booker.getId(),
                                                                                      LocalDateTime.now(),
                                                                                      LocalDateTime.now().minusDays(5));

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByBookerIdAndEndBeforeOrderByStartDescTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null,
                                      start.minusDays(3),
                                      end.minusDays(3),
                                      item,
                                      booker,
                                      BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                .findAllByBookerIdAndEndBeforeOrderByStartDesc(booker.getId(), LocalDateTime.now());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByBookerIdAndStartAfterOrderByStartDescTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        LocalDateTime futureStart = LocalDateTime.now().plusDays(3);
        LocalDateTime futureEnd = LocalDateTime.now().plusDays(5);
        Booking booking = new Booking(null, futureStart, futureEnd, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                .findAllByBookerIdAndStartAfterOrderByStartDesc(booker.getId(), LocalDateTime.now());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByBookerIdAndBookingStatusOrderByStartDescTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.REJECTED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository.findAllByBookerIdAndBookingStatusOrderByStartDesc(booker.getId(),
                                                                                                BookingStatus.REJECTED);

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByBookerIdOrderByStartDescTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository.findAllByBookerIdOrderByStartDesc(booker.getId());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDescTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                       .findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(owner.getId(),
                                                                                      LocalDateTime.now(),
                                                                                      LocalDateTime.now().minusDays(5));

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemOwnerIdAndEndBeforeOrderByStartDescTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null,
                                      start.minusDays(3),
                                      end.minusDays(3),
                                      item,
                                      booker,
                                      BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                                                 .findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(owner.getId(),
                                                                                                   LocalDateTime.now());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemOwnerIdAndStartAfterOrderByStartDescTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        LocalDateTime futureStart = LocalDateTime.now().plusDays(3);
        LocalDateTime futureEnd = LocalDateTime.now().plusDays(5);
        Booking booking = new Booking(null, futureStart, futureEnd, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                                                .findAllByItemOwnerIdAndStartAfterOrderByStartDesc(owner.getId(),
                                                                                                   LocalDateTime.now());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemOwnerIdAndBookingStatusOrderByStartDescTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.REJECTED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                                          .findAllByItemOwnerIdAndBookingStatusOrderByStartDesc(owner.getId(),
                                                                                                BookingStatus.REJECTED);

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemOwnerIdOrderByStartDescTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(owner.getId());

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByItemIdAndBookingStatusTest() {
        User owner = new User(null, "owner", "owner@mail.ru");
        owner = userRepository.save(owner);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, owner, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository.findAllByItemIdAndBookingStatus(item.getId(),
                                                                                      BookingStatus.APPROVED);

        Assertions.assertEquals(List.of(booking), bookingList);
    }

    @Test
    void findAllByBookerIdAndItemIdAndEndBeforeTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null,
                                      LocalDateTime.now().minusDays(3),
                                      LocalDateTime.now().minusDays(1),
                                      item,
                                      booker,
                                      BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        List<Booking> bookingList = bookingRepository
                          .findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(booker.getId(),
                                                                                      LocalDateTime.now(),
                                                                                      LocalDateTime.now().minusDays(4));

        Assertions.assertEquals(List.of(booking), bookingList);
    }
}
