package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.request.interfaces.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestRepositoryTest {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Test
    void findAllByRequesterIdOrderByCreatedDescTest() {
        User user = new User(1L, "user2", "user2@mail.ru");
        user = userRepository.save(user);
        ItemRequest itemRequest = new ItemRequest(null, "request", user, LocalDateTime.now());
        itemRequestRepository.save(itemRequest);
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(user.getId());

        Assertions.assertEquals(List.of(itemRequest), itemRequestList);
    }

    @Test
    void findAllByOrderByCreatedDescTest() {
        User user = new User(1L, "user2", "user2@mail.ru");
        user = userRepository.save(user);
        ItemRequest itemRequest = new ItemRequest(null, "request", user, LocalDateTime.now());
        itemRequestRepository.save(itemRequest);
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByOrderByCreatedDesc();

        Assertions.assertEquals(List.of(itemRequest), itemRequestList);
    }
}
