package ru.practicum.shareit.user.dto.inEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDtoIn {
    String name;
    String email;
}
