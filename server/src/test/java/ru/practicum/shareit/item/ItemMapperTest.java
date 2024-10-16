package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.in_entity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.out_entity.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ItemMapperTest {
    User user = new User(1L, "user", "user@mail.ru");
    ItemRequest itemRequest = new ItemRequest(1L, "itemRequest", user, LocalDateTime.now());
    Item item = new Item(1L, "item", "item", true, user, itemRequest);
    private final LocalDateTime start = LocalDateTime.now().minusDays(4);
    private final LocalDateTime end = LocalDateTime.now().minusDays(2);
    Booking booking = new Booking(1L, start, end, item, user, BookingStatus.APPROVED);
    Optional<Booking> optBooking = Optional.of(booking);

    @Test
    void fromItemToItemAddDtoOut() {
        ItemAddDtoOut itemAddDtoOut = ItemMapper.fromItemToItemAddDtoOut(item);

        Assertions.assertEquals(item.getId(), itemAddDtoOut.getId());
        Assertions.assertEquals(item.getName(), itemAddDtoOut.getName());
        Assertions.assertEquals(item.getDescription(), itemAddDtoOut.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemAddDtoOut.isAvailable());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(item.getOwner()), itemAddDtoOut.getOwner());
        Assertions.assertEquals(item.getRequest().getId(), itemAddDtoOut.getRequestId());
    }

    @Test
    void fromItemToItemUpdateDtoOutTest() {
        ItemUpdateDtoOut itemUpdateDtoOut = ItemMapper.fromItemToItemUpdateDtoOut(item);

        Assertions.assertEquals(item.getId(), itemUpdateDtoOut.getId());
        Assertions.assertEquals(item.getName(), itemUpdateDtoOut.getName());
        Assertions.assertEquals(item.getDescription(), itemUpdateDtoOut.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemUpdateDtoOut.isAvailable());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(item.getOwner()), itemUpdateDtoOut.getOwner());
        Assertions.assertEquals(item.getRequest(), itemUpdateDtoOut.getRequest());
    }

    @Test
    void fromItemToItemGetDtoOutWithBookingsTest() {
        List<CommentGetDtoOut> commentGetDtoOutList = List.of(new CommentGetDtoOut());
        ItemGetDtoOut itemGetDtoOut = ItemMapper.fromItemToItemGetDtoOut(optBooking, optBooking, commentGetDtoOutList, item);

        Assertions.assertEquals(item.getId(), itemGetDtoOut.getId());
        Assertions.assertEquals(item.getName(), itemGetDtoOut.getName());
        Assertions.assertEquals(item.getDescription(), itemGetDtoOut.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemGetDtoOut.isAvailable());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(item.getOwner()), itemGetDtoOut.getOwner());
        Assertions.assertEquals(item.getRequest(), itemGetDtoOut.getRequest());
        Assertions.assertNotNull(itemGetDtoOut.getNextBooking());
        Assertions.assertNotNull(itemGetDtoOut.getLastBooking());
        Assertions.assertEquals(commentGetDtoOutList, itemGetDtoOut.getComments());
    }

    @Test
    void fromItemToItemGetDtoOutTest() {
        ItemGetDtoOut itemGetDtoOut = ItemMapper.fromItemToItemGetDtoOut(item);

        Assertions.assertEquals(item.getId(), itemGetDtoOut.getId());
        Assertions.assertEquals(item.getName(), itemGetDtoOut.getName());
        Assertions.assertEquals(item.getDescription(), itemGetDtoOut.getDescription());
        Assertions.assertEquals(item.getAvailable(), itemGetDtoOut.isAvailable());
        Assertions.assertEquals(UserMapper.fromUserToUserGetDtoOut(item.getOwner()), itemGetDtoOut.getOwner());
        Assertions.assertEquals(item.getRequest(), itemGetDtoOut.getRequest());
    }

    @Test
    void fromItemAddDtoInToItemTest() {
        ItemAddDtoIn itemAddDtoIn = new ItemAddDtoIn("item", "item", true, 1L);
        Item item1 = ItemMapper.fromItemAddDtoInToItem(itemAddDtoIn, user);

        Assertions.assertNull(item1.getId());
        Assertions.assertEquals(itemAddDtoIn.getName(), item1.getName());
        Assertions.assertEquals(itemAddDtoIn.getDescription(), item1.getDescription());
        Assertions.assertEquals(itemAddDtoIn.getAvailable(), item1.getAvailable());
        Assertions.assertEquals(user, item1.getOwner());
        Assertions.assertNull(item1.getRequest());

    }

    @Test
    void fromItemToItemForItemRequestGetDtoOutTest() {
        ItemForItemRequestGetDtoOut itemForItemRequestGetDtoOut = ItemMapper.fromItemToItemForItemRequestGetDtoOut(item);

        Assertions.assertEquals(item.getId(), itemForItemRequestGetDtoOut.getId());
        Assertions.assertEquals(item.getName(), itemForItemRequestGetDtoOut.getName());
        Assertions.assertEquals(item.getOwner().getId(), itemForItemRequestGetDtoOut.getOwnerId());
    }
}
