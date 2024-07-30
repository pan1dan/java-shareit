package ru.practicum.shareit.user.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddDtoIn {
    @NotBlank(message = "Поле name не может быть пустым")
    @NotNull(message = "Поле name не может быть null")
    String name;
    @NotBlank(message = "Поле email не может быть пустым")
    @NotNull(message = "Поле email не может быть null")
    @Email(message = "email в неверном формате")
    String email;
}
