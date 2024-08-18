package ru.practicum.shareit.request.dto.inEntity;

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
