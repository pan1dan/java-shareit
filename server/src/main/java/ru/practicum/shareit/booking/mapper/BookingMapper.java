package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.dto.inEntity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.outEntity.BookingAddDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingApproveDtoOut;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {


    public static Booking fromBookingAddDtoInToBooking(BookingAddDtoIn bookingAddDtoIn, Item item, User booker) {
        return Booking.builder()
                .start(bookingAddDtoIn.getStart())
                .end(bookingAddDtoIn.getEnd())
                .item(item)
                .booker(booker)
                .build();

    }

    public static BookingAddDtoOut fromBookingToBookingAddDtoOut(Booking booking) {
        return BookingAddDtoOut.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()))
                .booker(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()))
                .status(booking.getBookingStatus())
                .build();
    }

    public static BookingApproveDtoOut fromBookingToBookingApproveDtoOut(Booking booking) {
        return BookingApproveDtoOut.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()))
                .booker(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()))
                .status(booking.getBookingStatus())
                .build();
    }

    public static BookingGetDtoOut fromBookingToBookingGetDtoOut(Booking booking) {
        return BookingGetDtoOut.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(ItemMapper.fromItemToItemGetDtoOut(booking.getItem()))
                .booker(UserMapper.fromUserToUserGetDtoOut(booking.getBooker()))
                .status(booking.getBookingStatus())
                .build();
    }
}
