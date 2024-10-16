package ru.practicum.shareit.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.interfaces.UserService;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class ExceptionTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;
    UserAddDtoIn userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");

    @Test
    void handleValidationTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(ValidationException.class);

        mvc.perform(post("/users")
                .content(mapper.writeValueAsString(userAddDtoIn))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Ошибка валидации: "));
    }

    @Test
    void handleNotFoundTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(NotFoundException.class);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Ресурс не найден: "));
    }

    @Test
    void handleConflict() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(ConflictException.class);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Конфликт в программе: "));
    }

    @Test
    void handleForbiddenTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(ForbiddenException.class);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Ошибка доступа: "));
    }

    @Test
    void handleBadRequestTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(BadRequestException.class);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный запрос: "));
    }

    @Test
    void handleExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(RuntimeException.class);

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("500 ERROR: "));
    }

    @Test
    void badRequestExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(new BadRequestException("Неправильный запрос: "));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Неправильный запрос: "))
                .andExpect(jsonPath("$.description").value("Неправильный запрос: "));
    }

    @Test
    void conflictExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(new ConflictException("Конфликт в программе: "));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Конфликт в программе: "))
                .andExpect(jsonPath("$.description").value("Конфликт в программе: "));
    }

    @Test
    void forbiddenExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(new ForbiddenException("Ошибка доступа: "));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Ошибка доступа: "))
                .andExpect(jsonPath("$.description").value("Ошибка доступа: "));
    }

    @Test
    void notFoundExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(new NotFoundException("Ресурс не найден: "));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Ресурс не найден: "))
                .andExpect(jsonPath("$.description").value("Ресурс не найден: "));
    }

    @Test
    void validationExceptionTest() throws Exception {
        when(userService.addUser(any(UserAddDtoIn.class))).thenThrow(new ValidationException("Ошибка валидации: "));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Ошибка валидации: "))
                .andExpect(jsonPath("$.description").value("Ошибка валидации: "));
    }
}
