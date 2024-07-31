package ru.practicum.shareit.user.dto.outEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDtoOut {
    Long id;
    String name;
    String email;
}
