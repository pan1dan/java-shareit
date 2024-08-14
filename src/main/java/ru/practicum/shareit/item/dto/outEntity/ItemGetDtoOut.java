package ru.practicum.shareit.item.dto.outEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ItemGetDtoOut {
    long id;
    String name;
    String description;
    Boolean available;
    UserGetDtoOut owner;
    ItemRequest request;
    BookingGetDtoOut lastBooking;
    BookingGetDtoOut nextBooking;
    List<CommentGetDtoOut> comments;
}
