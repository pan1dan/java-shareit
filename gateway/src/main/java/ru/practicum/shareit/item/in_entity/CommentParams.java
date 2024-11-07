package ru.practicum.shareit.item.in_entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentParams {
    private String text;
    private Long authorId;
    private Long itemId;
}
