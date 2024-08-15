package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.inEntity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.outEntity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public BookingAddDtoOut addBooking(long userId, BookingAddDtoIn bookingAddDtoIn) {
        User booker = userRepository.findById(userId)
                                .orElseThrow(() -> new NotFoundException("При создании бронирования не был найден " +
                                "user с id = " + userId));
        Item item = itemRepository.findById(bookingAddDtoIn.getItemId())
                                  .orElseThrow(() -> new NotFoundException("При создании бронирования не был найден " +
                                                                            "item с id = " +
                                                                                         bookingAddDtoIn.getItemId()));
        if (!item.getAvailable()) {
            throw new BadRequestException("Нельзя забронировать уже забронированный предмет");
        }
        Booking newBooking = BookingMapper.fromBookingAddDtoInToBooking(bookingAddDtoIn, item, booker);
        newBooking.setBookingStatus(BookingStatus.WAITING);
        return BookingMapper.fromBookingToBookingAddDtoOut(bookingRepository.save(newBooking));
    }

    @Override
    public BookingApproveDtoOut approveBooking(long userId, long bookingId, boolean isApproved) {
        Booking booking = bookingRepository.findById(bookingId)
                                           .orElseThrow(() -> new NotFoundException("Не найдено бронироваине с id = "
                                                                                                          + bookingId));
        userRepository.findById(userId)
                      .orElseThrow(() -> new BadRequestException("При апруве бронирования не был найден " +
                                                                                              "user с id = " + userId));
        if (isApproved) {
            booking.setBookingStatus(BookingStatus.APPROVED);
        } else {
            booking.setBookingStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.fromBookingToBookingApproveDtoOut(bookingRepository.save(booking));
    }

    @Override
    public BookingGetDtoOut getBookingById(long userId, long bookingId) {
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new NotFoundException("При создании бронирования не был найден " +
                                                                                              "user с id = " + userId));
        Booking booking = bookingRepository.findById(bookingId)
                                           .orElseThrow(() -> new NotFoundException("Не было найдено бронирование " +
                                                                                                "с id = " + bookingId));
        return BookingMapper.fromBookingToBookingGetDtoOut(booking);
    }

    @Override
    public List<BookingGetDtoOut> getBookingsWithState(long userId, String bookingState) {
        BookingState bookingStateEnum = BookingState.valueOf(bookingState);
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("При получении бронирования с состоянием не был найден " +
                                                                                            "user с id = " + userId));
        List<Booking> bookings;
        if (bookingStateEnum == BookingState.CURRENT) {
            bookings = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(userId, LocalDateTime.now(), LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.PAST) {
            bookings = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.FUTURE) {
            bookings = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.WAITING) {
            bookings = bookingRepository.findAllByBookerIdAndBookingStatusOrderByStartDesc(userId, BookingStatus.WAITING);
        } else if (bookingStateEnum == BookingState.REJECTED) {
            bookings = bookingRepository.findAllByBookerIdAndBookingStatusOrderByStartDesc(userId, BookingStatus.REJECTED);
        } else {
            bookings = bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
        }
        return bookings.stream().map(BookingMapper::fromBookingToBookingGetDtoOut).toList();
    }

    @Override
    public  List<BookingGetDtoOut> getBookingsByOwner(long ownerId, String bookingState) {
        BookingState bookingStateEnum = BookingState.valueOf(bookingState);
        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("При получении бронирования с состоянием не был найден " +
                                                                                            "user с id = " + ownerId));
        List<Booking> bookings;
        if (bookingStateEnum == BookingState.CURRENT) {
            bookings = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(ownerId,
                    LocalDateTime.now(), LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.PAST) {
            bookings = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(ownerId, LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.FUTURE) {
            bookings = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now());
        } else if (bookingStateEnum == BookingState.WAITING) {
            bookings = bookingRepository.findAllByItemOwnerIdAndBookingStatusOrderByStartDesc(ownerId, BookingStatus.WAITING);
        } else if (bookingStateEnum == BookingState.REJECTED) {
            bookings = bookingRepository.findAllByItemOwnerIdAndBookingStatusOrderByStartDesc(ownerId, BookingStatus.REJECTED);
        } else {
            bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(ownerId);
        }
        return bookings.stream().map(BookingMapper::fromBookingToBookingGetDtoOut).toList();
    }
}
