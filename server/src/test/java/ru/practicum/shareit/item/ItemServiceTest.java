package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.in_entity.BookingAddDtoIn;
import ru.practicum.shareit.booking.dto.out_entity.BookingAddDtoOut;
import ru.practicum.shareit.booking.interfaces.BookingService;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.in_entity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.in_entity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.out_entity.CommentAddDtoOut;
import ru.practicum.shareit.item.dto.out_entity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.out_entity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.out_entity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestRepository;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {
    private final ItemService itemService;
    private final UserService userService;
    private final ItemRequestService itemRequestService;
    private final BookingService bookingService;
    private final ItemRequestRepository itemRequestRepository;

    private final LocalDateTime start = LocalDateTime.now().plusDays(1);
    private final LocalDateTime end = LocalDateTime.now().plusDays(2);
    ItemDto itemDto = new ItemDto();

    @Test
    void addItemTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));

        UserAddDtoOut requester = userService.addUser(new UserAddDtoIn("requestor", "requestor@mail.com"));

        ItemRequestAddDtoOut request = itemRequestService.addItemRequest(requester.getId(), new ItemRequestAddDtoIn("description"));

        ItemAddDtoOut item = itemService.addItem(user.getId(), new ItemAddDtoIn("item", "description", true, request.getId()));
        ItemAddDtoOut itemMust = new ItemAddDtoOut(
                item.getId(), "item", "description", true, UserMapper.fromUserToUserGetDtoOut(new User(user.getId(), user.getName(), user.getEmail())), request.getId());

        Assertions.assertEquals(item.getName(), itemMust.getName());
        Assertions.assertEquals(item.getDescription(), itemMust.getDescription());
        Assertions.assertEquals(item.getOwner().getName(), itemMust.getOwner().getName());
        Assertions.assertEquals(item.getOwner().getEmail(), itemMust.getOwner().getEmail());
    }

    @Test
    void addItemWhenRequestNotFoundTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));
        UserAddDtoOut requester = userService.addUser(new UserAddDtoIn("requestor", "requestor@mail.com"));
        ItemRequestAddDtoOut request = itemRequestService.addItemRequest(requester.getId(), new ItemRequestAddDtoIn("description"));
        itemRequestRepository.deleteAll();
        Assertions.assertThrows(NotFoundException.class, () ->
                itemService.addItem(user.getId(),
                                    new ItemAddDtoIn("item",
                                                     "description",
                                                     true,
                                                     request.getId())));
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
    void notOwnerUpdateItemTest() {
        UserAddDtoOut user1 = userService.addUser(new UserAddDtoIn("user1", "user1@mail.ru"));
        UserAddDtoOut user2 = userService.addUser(new UserAddDtoIn("user2", "user2@mail.ru"));

        Long itemId = itemService.addItem(user1.getId(),
                new ItemAddDtoIn("item",
                        "description",
                        true,
                        null)).getId();

        Assertions.assertThrows(ForbiddenException.class, () -> itemService.updateItem(user2.getId(),
                                                                                       new ItemUpdateDtoIn(itemId,
                                                                                       "updateItem",
                                                                                       "updateDescription",
                                                                                       true,
                                                                                       null)));
    }

    @Test
    void updateItemWithoutItemTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));
        Assertions.assertThrows(NotFoundException.class, () -> itemService.updateItem(user.getId(),
                                                                                      new ItemUpdateDtoIn(1L,
                                                                                      "updateItem",
                                                                                      "updateDescription",
                                                                                      true,
                                                                                      null)));
    }

    @Test
    void updateItemWhenItemRequestNotInBaseTest() {
        UserAddDtoOut user = userService.addUser(new UserAddDtoIn("user", "user@mail.ru"));
        ItemRequestAddDtoIn itemRequestAddDtoIn = new ItemRequestAddDtoIn("itemRequest");
        ItemRequestAddDtoOut itemRequest = itemRequestService.addItemRequest(user.getId(), itemRequestAddDtoIn);
        Long itemId = itemService.addItem(user.getId(),
                new ItemAddDtoIn("item",
                        "description",
                        true,
                        itemRequest.getId())).getId();
        itemRequestRepository.deleteAll();

        Assertions.assertThrows(NotFoundException.class, () -> itemService.updateItem(user.getId(),
                                                                                      new ItemUpdateDtoIn(itemId,
                                                                                      "updateItem",
                                                                                      "updateDescription",
                                                                                      true,
                                                                                      1L)));
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
    void searchItemsWithEmptyTextTest() {
        List<ItemGetDtoOut> itemGetDtoOutList = itemService.searchItem("");
        Assertions.assertEquals(new ArrayList<>(), itemGetDtoOutList);
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

    @Test
    void addCommentWithoutBookingTest() {
        UserAddDtoOut owner = userService.addUser(new UserAddDtoIn("owner", "user@mail.ru"));
        ItemAddDtoOut item1 = itemService.addItem(owner.getId(), new ItemAddDtoIn("item1", "description1", true, null));
        UserAddDtoOut booker = userService.addUser(new UserAddDtoIn("booker", "booker@mail.ru"));
        BookingAddDtoOut booking = bookingService.addBooking(booker.getId(), new BookingAddDtoIn(item1.getId(), start.plusDays(3), end.plusDays(4)));
        bookingService.approveBooking(owner.getId(), booking.getId(), true);
        Assertions.assertThrows(BadRequestException.class, () ->
                                                              itemService.addComment(booker.getId(),
                                                                                     item1.getId(),
                                                                                     new CommentAddDtoIn("cool")));
    }
}