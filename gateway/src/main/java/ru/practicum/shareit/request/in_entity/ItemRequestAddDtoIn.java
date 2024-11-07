package ru.practicum.shareit.request.in_entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestAddDtoIn {
    @NotBlank
    @NotNull
    String description;

}
