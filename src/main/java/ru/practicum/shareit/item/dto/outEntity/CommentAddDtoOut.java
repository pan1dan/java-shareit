package ru.practicum.shareit.item.dto.outEntity;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDtoOut {
    Long id;
    String text;
    ItemGetDtoOut item;
    String authorName;
    LocalDateTime created;
}
