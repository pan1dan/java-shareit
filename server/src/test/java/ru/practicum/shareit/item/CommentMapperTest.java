package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.out_entity.CommentAddDtoOut;
import ru.practicum.shareit.item.dto.out_entity.CommentGetDtoOut;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@SpringBootTest
public class CommentMapperTest {
    User user = new User(1L, "user", "user@mail.ru");
    Item item = new Item(1L, "item", "item", true, user, null);
    String text = "123";

    @Test
    void fromDataToCommentTest() {
        Comment comment = CommentMapper.fromDataToComment(text, user, item);

        Assertions.assertEquals(text, comment.getText());
        Assertions.assertEquals(item, comment.getItem());
        Assertions.assertEquals(user, comment.getAuthor());
    }

    @Test
    void fromCommentToCommentAddDtoOutTest() {
        Comment comment = CommentMapper.fromDataToComment(text, user, item);
        comment.setId(1L);
        CommentAddDtoOut commentAddDtoOut = CommentMapper.fromCommentToCommentAddDtoOut(comment);

        Assertions.assertEquals(1L, commentAddDtoOut.getId());
        Assertions.assertEquals(text, commentAddDtoOut.getText());
        Assertions.assertEquals(ItemMapper.fromItemToItemGetDtoOut(item), commentAddDtoOut.getItem());
        Assertions.assertEquals(user.getName(), commentAddDtoOut.getAuthorName());
    }

    @Test
    void fromCommentToCommentGetDtoOutTest() {
        Comment comment = CommentMapper.fromDataToComment(text, user, item);
        comment.setId(1L);
        CommentGetDtoOut commentGetDtoOut = CommentMapper.fromCommentToCommentGetDtoOut(comment);

        Assertions.assertEquals(1L, commentGetDtoOut.getId());
        Assertions.assertEquals(text, commentGetDtoOut.getText());
        Assertions.assertEquals(user.getName(), commentGetDtoOut.getAuthorName());
    }
}
