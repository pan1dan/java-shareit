package ru.practicum.shareit.user.dto.out_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDtoOut {
    long id;
    String name;
    String email;
}
