package ru.practicum.shareit.user.dto.in_entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDtoIn {
    Long id;
    String name;
    String email;
}
