package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.outEntity.CommentAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.CommentGetDtoOut;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class CommentMapper {
    public static Comment fromDataToComment(String text, User user, Item item) {
        return Comment.builder()
                .text(text)
                .author(user)
                .item(item)
                .build();
    }

    public static CommentAddDtoOut fromCommentToCommentAddDtoOut(Comment comment) {
        return CommentAddDtoOut.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .authorName(comment.getAuthor().getName())
                .item(ItemMapper.fromItemToItemGetDtoOut(comment.getItem()))
                .build();
    }

    public static CommentGetDtoOut fromCommentToCommentGetDtoOut(Comment comment) {
        return CommentGetDtoOut.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .authorName(comment.getAuthor().getName())
                .build();
    }
}
