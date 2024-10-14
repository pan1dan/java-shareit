package ru.practicum.shareit.user.dto.InEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDtoIn {
    @NotBlank(message = "Поле name не может быть пустым")
    String name;
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "email в неверном формате")
    String email;
}
