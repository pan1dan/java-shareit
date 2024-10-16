package ru.practicum.shareit.request;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestGetDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ItemRequestServiceTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private UserAddDtoIn userAddDtoIn;
    private final EntityManager em;
    ItemRequestDto itemRequestDto = new ItemRequestDto();

    @BeforeEach
    public void initialization() {
        userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");
    }

    @Transactional
    @AfterEach
    public void outro() {
        em.createNativeQuery("DELETE FROM items").executeUpdate();
        em.createNativeQuery("DELETE FROM requests").executeUpdate();
        em.createNativeQuery("DELETE FROM users").executeUpdate();
    }

    @Test
    void addItemRequestTest() {
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        UserGetDtoOut userGetDtoOut = new UserGetDtoOut(userAddDtoOut.getId(), userAddDtoOut.getName(), userAddDtoOut.getEmail());
        ItemRequestAddDtoIn itemRequestAddDtoIn = new ItemRequestAddDtoIn("request");
        ItemRequestAddDtoOut itemRequestAddDtoOut = itemRequestService.addItemRequest(userAddDtoOut.getId(), itemRequestAddDtoIn);

        Assertions.assertEquals("request", itemRequestAddDtoOut.getDescription());
        Assertions.assertEquals(userGetDtoOut, itemRequestAddDtoOut.getRequester());
    }

    @Test
    void getUserItemsRequestsTest() {
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        ItemRequestAddDtoIn itemRequestAddDtoIn1 = new ItemRequestAddDtoIn("request1");
        ItemRequestAddDtoOut itemRequestAddDtoOut1 = itemRequestService.addItemRequest(userAddDtoOut.getId(), itemRequestAddDtoIn1);

        List<ItemRequestGetDtoOut> itemRequestGetDtoOutList = itemRequestService.getUserItemsRequests(userAddDtoOut.getId());
        ItemRequestGetDtoOut itemRequestGetDtoOut1 = itemRequestGetDtoOutList.get(0);

        Assertions.assertEquals("request1", itemRequestGetDtoOut1.getDescription());
        Assertions.assertEquals(List.of(), itemRequestGetDtoOut1.getItems());
    }

    @Test
    void getAllItemsRequestsTest() {
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        ItemRequestAddDtoIn itemRequestAddDtoIn1 = new ItemRequestAddDtoIn("request1");
        ItemRequestAddDtoOut itemRequestAddDtoOut1 = itemRequestService.addItemRequest(userAddDtoOut.getId(), itemRequestAddDtoIn1);
        ItemRequestAddDtoIn itemRequestAddDtoIn2 = new ItemRequestAddDtoIn("request2");
        ItemRequestAddDtoOut itemRequestAddDtoOut2 = itemRequestService.addItemRequest(userAddDtoOut.getId(), itemRequestAddDtoIn2);

        List<ItemRequestGetDtoOut> listItemsRequests = itemRequestService.getAllItemsRequests();
        ItemRequestGetDtoOut itemRequest1 = listItemsRequests.get(1);
        ItemRequestGetDtoOut itemRequest2 = listItemsRequests.get(0);

        Assertions.assertEquals(itemRequestAddDtoOut1.getId(), itemRequest1.getId());
        Assertions.assertEquals(itemRequestAddDtoOut1.getDescription(), itemRequest1.getDescription());
        Assertions.assertEquals(itemRequestAddDtoOut1.getCreated(), itemRequest1.getCreated());
        Assertions.assertEquals(List.of(), itemRequest1.getItems());
        Assertions.assertEquals(itemRequestAddDtoOut2.getId(), itemRequest2.getId());
        Assertions.assertEquals(itemRequestAddDtoOut2.getDescription(), itemRequest2.getDescription());
        Assertions.assertEquals(itemRequestAddDtoOut2.getCreated(), itemRequest2.getCreated());
        Assertions.assertEquals(List.of(), itemRequest2.getItems());
    }

    @Test
    void getItemRequestByIdTest() {
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        ItemRequestAddDtoIn itemRequestAddDtoIn1 = new ItemRequestAddDtoIn("request1");
        ItemRequestAddDtoOut itemRequestAddDtoOut1 = itemRequestService.addItemRequest(userAddDtoOut.getId(), itemRequestAddDtoIn1);
        ItemRequestGetDtoOut itemRequestGetDtoOut = itemRequestService.getItemRequestById(itemRequestAddDtoOut1.getId());

        Assertions.assertEquals(itemRequestAddDtoOut1.getId(), itemRequestGetDtoOut.getId());
        Assertions.assertEquals(itemRequestAddDtoOut1.getDescription(), itemRequestGetDtoOut.getDescription());
        Assertions.assertEquals(itemRequestAddDtoOut1.getCreated(), itemRequestGetDtoOut.getCreated());
        Assertions.assertEquals(List.of(), itemRequestGetDtoOut.getItems());
    }
}
