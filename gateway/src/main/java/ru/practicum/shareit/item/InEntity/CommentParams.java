package ru.practicum.shareit.item.InEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentParams {
    private String text;
    private Long authorId;
    private Long itemId;
}
