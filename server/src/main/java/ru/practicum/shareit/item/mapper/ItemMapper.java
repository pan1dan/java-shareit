package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.in_entity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.out_entity.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public class ItemMapper {
    public static ItemAddDtoOut fromItemToItemAddDtoOut(Item item) {
        ItemAddDtoOut itemAddDtoOut = ItemAddDtoOut.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.fromUserToUserGetDtoOut(item.getOwner()))
                .build();
        if (item.getRequest() != null) {
            itemAddDtoOut.setRequestId(item.getRequest().getId());
        }
        return itemAddDtoOut;
    }

    public static ItemUpdateDtoOut fromItemToItemUpdateDtoOut(Item item) {
        return ItemUpdateDtoOut.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.fromUserToUserGetDtoOut(item.getOwner()))
                .request(item.getRequest())
                .build();
    }

    public static ItemGetDtoOut fromItemToItemGetDtoOut(Optional<Booking> lastBooking,
                                                        Optional<Booking> nextBooking,
                                                        List<CommentGetDtoOut> commentGetDtoOutList,
                                                        Item item) {
        ItemGetDtoOut itemGetDtoOut = ItemGetDtoOut.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.fromUserToUserGetDtoOut(item.getOwner()))
                .request(item.getRequest())
                .comments(commentGetDtoOutList)
                .build();
        lastBooking.ifPresent(booking ->
                itemGetDtoOut.setLastBooking(BookingMapper.fromBookingToBookingGetDtoOut(lastBooking.get())));
        nextBooking.ifPresent(booking ->
                itemGetDtoOut.setNextBooking(BookingMapper.fromBookingToBookingGetDtoOut(nextBooking.get())));
        return itemGetDtoOut;
    }

    public static ItemGetDtoOut fromItemToItemGetDtoOut(Item item) {
        return ItemGetDtoOut.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .owner(UserMapper.fromUserToUserGetDtoOut(item.getOwner()))
                .request(item.getRequest())
                .build();
    }

    public static Item fromItemAddDtoInToItem(ItemAddDtoIn itemAddDtoIn, User owner) {
        return Item.builder()
                .name(itemAddDtoIn.getName())
                .description(itemAddDtoIn.getDescription())
                .available(itemAddDtoIn.getAvailable())
                .owner(owner)
                .build();
    }

    public static ItemForItemRequestGetDtoOut fromItemToItemForItemRequestGetDtoOut(Item item) {
        return ItemForItemRequestGetDtoOut.builder()
                                          .ownerId(item.getOwner().getId())
                                          .id(item.getId())
                                          .name(item.getName())
                                          .build();

    }
}
