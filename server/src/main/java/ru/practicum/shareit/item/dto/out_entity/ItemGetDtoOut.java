package ru.practicum.shareit.item.dto.out_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.out_entity.BookingGetDtoOut;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ItemGetDtoOut {
    long id;
    String name;
    String description;
    boolean available;
    UserGetDtoOut owner;
    ItemRequest request;
    BookingGetDtoOut lastBooking;
    BookingGetDtoOut nextBooking;
    List<CommentGetDtoOut> comments;
}
