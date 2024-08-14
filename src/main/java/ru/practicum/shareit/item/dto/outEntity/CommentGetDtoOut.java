package ru.practicum.shareit.item.dto.outEntity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDtoOut {
    Long id;
    String text;
//    ItemGetDtoOut item;
    String authorName;
    LocalDateTime created;
}
