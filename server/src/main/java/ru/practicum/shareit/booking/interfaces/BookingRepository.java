package ru.practicum.shareit.booking.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartDesc(
                                                                             long userId,
                                                                             LocalDateTime start,
                                                                             LocalDateTime end);

    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(long userId, LocalDateTime localDateTime);

    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(long userId, LocalDateTime localDateTime);

    List<Booking> findAllByBookerIdAndBookingStatusOrderByStartDesc(long userId, BookingStatus status);

    List<Booking> findAllByBookerIdOrderByStartDesc(long userId);

    List<Booking> findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartDesc(long ownerId,
                                                                                LocalDateTime start,
                                                                                LocalDateTime end);

    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime localDateTime);

    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(long ownerId, LocalDateTime localDateTime);

    List<Booking> findAllByItemOwnerIdAndBookingStatusOrderByStartDesc(long ownerId, BookingStatus status);

    List<Booking> findAllByItemOwnerIdOrderByStartDesc(long ownerId);

    List<Booking> findAllByItemIdAndBookingStatus(Long itemId, BookingStatus bookingStatus);

    List<Booking> findAllByBookerIdAndItemIdAndEndBefore(Long bookerId,
                                                                         Long itemId,
                                                                         LocalDateTime localDateTime);
}
