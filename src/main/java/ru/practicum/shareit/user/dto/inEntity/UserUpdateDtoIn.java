package ru.practicum.shareit.user.dto.inEntity;

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
    String email;
}
