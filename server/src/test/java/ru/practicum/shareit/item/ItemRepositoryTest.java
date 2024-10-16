package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.interfaces.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRepositoryTest {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Test
    void findAllByOwnerIdTest() {
        User user = new User(1L, "user1", "user1@mail.ru");
        user = userRepository.save(user);
        Item item1 = itemRepository.save(new Item(null, "item1", "item1", true, user, null));
        Item item2 = itemRepository.save(new Item(null, "item2", "item2", true, user, null));
        List<Item> itemList = itemRepository.findAllByOwnerId(user.getId());

        Assertions.assertEquals(List.of(item1, item2), itemList);
    }

    @Test
    void findAllByRequestIdTest() {
        User user1 = new User(1L, "user1", "user1@mail.ru");
        user1 = userRepository.save(user1);
        User user2 = new User(2L, "user2", "user2@mail.ru");
        user2 = userRepository.save(user2);
        ItemRequest itemRequest = new ItemRequest(1L, "itemRequest", user2, LocalDateTime.now());
        itemRequest = itemRequestRepository.save(itemRequest);
        Item item1 = itemRepository.save(new Item(null, "item1", "item1", true, user1, itemRequest));
        Item item2 = itemRepository.save(new Item(null, "item2", "item2", true, user1, itemRequest));
        List<Item> itemList = itemRepository.findAllByRequestId(itemRequest.getId());

        Assertions.assertEquals(List.of(item1, item2), itemList);
    }

    @Test
    void searchTest() {
        User user1 = new User(1L, "user1", "user1@mail.ru");
        user1 = userRepository.save(user1);
        Item item1 = itemRepository.save(new Item(null, "item1", "item1", true, user1, null));
        List<Item> itemList = itemRepository.search(item1.getName());

        Assertions.assertEquals(List.of(item1), itemList);
    }
}
