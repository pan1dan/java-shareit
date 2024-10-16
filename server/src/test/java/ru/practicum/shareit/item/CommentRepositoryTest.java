package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.interfaces.CommentRepository;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentRepositoryTest {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private LocalDateTime start = LocalDateTime.now().minusDays(4);
    private LocalDateTime end = LocalDateTime.now().minusDays(2);

    @Test
    void findAllByItemIdTest() {
        User user = new User(null, "user", "user@mail.ru");
        user = userRepository.save(user);
        User booker = new User(null, "booker", "booker@mail.ru");
        booker = userRepository.save(booker);
        Item item = new Item(null, "item1", "item1", true, user, null);
        item = itemRepository.save(item);
        Booking booking = new Booking(null, start, end, item, booker, BookingStatus.APPROVED);
        booking = bookingRepository.save(booking);
        Comment comment = new Comment(null, "comment", item,  user, LocalDateTime.now());
        comment = commentRepository.save(comment);
        List<Comment> commentList = commentRepository.findAllByItemId(item.getId());

        Assertions.assertEquals(List.of(comment), commentList);
    }
}
