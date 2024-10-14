package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.inEntity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.outEntity.BookingAddDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.item.dto.inEntity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.CommentAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.request.dto.inEntity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.outEntity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {
    private final ItemService itemService;
    private final UserService userService;
    private final ItemRequestService requestService;
    private final BookingService bookingService;

    private final LocalDateTime start = LocalDateTime.now().plusDays(1);
    private final LocalDateTime end = LocalDateTime.now().plusDays(2);

    @Test
    void addItemTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));

        UserAddDtoOut requestor = userService.addUser(new UserAddDtoIn("requestor", "requestor@mail.com"));

        ItemRequestAddDtoOut request = requestService.addItemRequest(requestor.getId(), new ItemRequestAddDtoIn("description"));

        ItemAddDtoOut item = itemService.addItem(user.getId(), new ItemAddDtoIn("item", "description", true, request.getId()));
        ItemAddDtoOut itemMust = new ItemAddDtoOut(
                item.getId(), "item", "description", true, UserMapper.fromUserToUserGetDtoOut(new User(user.getId(), user.getName(), user.getEmail())), request.getId());

        Assertions.assertEquals(item.getName(), itemMust.getName());
        Assertions.assertEquals(item.getDescription(), itemMust.getDescription());
        Assertions.assertEquals(item.getOwner().getName(), itemMust.getOwner().getName());
        Assertions.assertEquals(item.getOwner().getEmail(), itemMust.getOwner().getEmail());
    }

    @Test
    void updateItemTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));

        Long itemId = itemService.addItem(user.getId(),
                                          new ItemAddDtoIn("item",
                                                           "description",
                                                           true,
                                                           null)).getId();

        ItemUpdateDtoOut updateItem = itemService.updateItem(user.getId(),
                                                             new ItemUpdateDtoIn(itemId,
                                                                                 "updateItem",
                                                                                 "updateDescription",
                                                                                 true,
                                                                                 null));

        Assertions.assertEquals(updateItem.getName(), "updateItem");
        Assertions.assertEquals(updateItem.getDescription(), "updateDescription");
        Assertions.assertTrue(updateItem.isAvailable());
        Assertions.assertNull(updateItem.getRequest());
    }

    @Test
    void getItemTest() {
        UserAddDtoOut owner = userService.addUser(new UserAddDtoIn("owner", "user@mail.ru"));
        Long itemId = itemService.addItem(owner.getId(), new ItemAddDtoIn("item", "description", true, null)).getId();
        ItemGetDtoOut item = itemService.getItem(owner.getId(), itemId);

        Assertions.assertEquals(item.getName(), "item");
        Assertions.assertEquals(item.getDescription(), "description");
        Assertions.assertTrue(item.isAvailable());
        Assertions.assertEquals(item.getOwner(), new UserGetDtoOut(owner.getId(), owner.getName(), owner.getEmail()));
        Assertions.assertEquals(item.getComments(), List.of());
        Assertions.assertNull(item.getNextBooking());
        Assertions.assertNull(item.getRequest());
        Assertions.assertNull(item.getLastBooking());
    }

    @Test
    void getItemUserTest() {
        UserAddDtoOut owner = userService.addUser(new UserAddDtoIn("owner", "user@mail.ru"));
        ItemAddDtoOut item1 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item1", "description1", true, null));
        ItemAddDtoOut item2 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item2", "description2", true, null));
        List<ItemGetDtoOut> items = itemService.getUserItems(owner.getId());
        ItemGetDtoOut itemFromList1 = items.get(0);
        ItemGetDtoOut itemFromList2 = items.get(1);
        ItemAddDtoOut item1Get = new ItemAddDtoOut(itemFromList1.getId(),
                                                   itemFromList1.getName(),
                                                   itemFromList1.getDescription(),
                                                   itemFromList1.isAvailable(),
                                                   itemFromList1.getOwner(),
                                                   null);
        ItemAddDtoOut item2Get = new ItemAddDtoOut(itemFromList2.getId(),
                                                   itemFromList2.getName(),
                                                   itemFromList2.getDescription(),
                                                   itemFromList2.isAvailable(),
                                                   itemFromList2.getOwner(),
                                                   null);
        Assertions.assertEquals(List.of(item1, item2), List.of(item1Get, item2Get));
    }

    @Test
    void searchItemsTest() {
        UserAddDtoOut owner = userService.addUser(new UserAddDtoIn("owner", "user@mail.ru"));
        ItemAddDtoOut item1 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item1", "description1", true, null));
        ItemAddDtoOut item2 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item2", "description2", true, null));

        List<ItemGetDtoOut> itemsList = itemService.searchItem("description");
        ItemGetDtoOut itemFromList1 = itemsList.get(0);
        ItemGetDtoOut itemFromList2 = itemsList.get(1);
        ItemAddDtoOut item1Get = new ItemAddDtoOut(itemFromList1.getId(),
                itemFromList1.getName(),
                itemFromList1.getDescription(),
                itemFromList1.isAvailable(),
                itemFromList1.getOwner(),
                null);
        ItemAddDtoOut item2Get = new ItemAddDtoOut(itemFromList2.getId(),
                itemFromList2.getName(),
                itemFromList2.getDescription(),
                itemFromList2.isAvailable(),
                itemFromList2.getOwner(),
                null);
        Assertions.assertEquals(List.of(item1, item2), List.of(item1Get, item2Get));
    }

    @Test
    void addCommentTest() {
        UserAddDtoOut owner = userService.addUser(new UserAddDtoIn("owner", "user@mail.ru"));
        ItemAddDtoOut item1 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item1", "description1", true, null));
        UserAddDtoOut booker = userService.addUser(new UserAddDtoIn("booker", "booker@mail.ru"));
        BookingAddDtoOut booking = bookingService.addBooking(booker.getId(), new BookingAddDtoIn(item1.getId(), start.minusDays(4), end.minusDays(3)));
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        CommentAddDtoOut commentAddDtoOut = itemService.addComment(booker.getId(), item1.getId(), new CommentAddDtoIn("cool"));
        ItemGetDtoOut itemAfterComment = itemService.getItem(owner.getId(), item1.getId());
        Assertions.assertEquals(itemAfterComment.getComments().getFirst().getText(), "cool");
   }
}