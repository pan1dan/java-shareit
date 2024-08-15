package ru.practicum.shareit.item.dto.outEntity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDtoOut {
    long id;
    String text;
    String authorName;
    LocalDateTime created;
}
