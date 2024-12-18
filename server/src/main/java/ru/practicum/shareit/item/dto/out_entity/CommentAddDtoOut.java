package ru.practicum.shareit.item.dto.out_entity;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDtoOut {
    long id;
    String text;
    ItemGetDtoOut item;
    String authorName;
    LocalDateTime created;
}
