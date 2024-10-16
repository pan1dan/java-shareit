package ru.practicum.shareit.user.dto.in_entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDtoIn {
    @Positive
    Long id;
    String name;
    @Email
    String email;
}
