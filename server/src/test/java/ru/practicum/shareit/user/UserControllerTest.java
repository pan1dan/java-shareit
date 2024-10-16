package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in_entity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Test
    void addUserTest() throws Exception {
        UserAddDtoIn userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");
        when(userService.addUser(any(UserAddDtoIn.class))).thenReturn(new UserAddDtoOut(1L,
                                                                                         userAddDtoIn.getName(),
                                                                                         userAddDtoIn.getEmail()));

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.email").value("user@mail.ru"));

        verify(userService).addUser(any(UserAddDtoIn.class));
    }

    @Test
    void updateUserTest() throws Exception {
        UserUpdateDtoIn userUpdateDtoIn = new UserUpdateDtoIn(1L, "userUpdate", "userUpdate@mail.ru");

        when(userService.updateUser(any(UserUpdateDtoIn.class)))
                        .thenReturn(new UserUpdateDtoOut(1L,
                                                         userUpdateDtoIn.getName(),
                                                         userUpdateDtoIn.getEmail()));
        mvc.perform(patch("/users/1")
                        .content(mapper.writeValueAsString(userUpdateDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("userUpdate"))
                .andExpect(jsonPath("$.email").value("userUpdate@mail.ru"));
        verify(userService).updateUser(any(UserUpdateDtoIn.class));
    }

    @Test
    void getUserTest() throws Exception {
        UserGetDtoOut userGetDtoOut = new UserGetDtoOut(1L, "user", "user@mail.ru");

        when(userService.getUser(1L)).thenReturn(new UserGetDtoOut(1L, "user", "user@mail.ru"));

        mvc.perform(get("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("user"))
                .andExpect(jsonPath("$.email").value("user@mail.ru"));
        verify(userService).getUser(any(long.class));
    }

    @Test
    void getAllUsersTest() throws Exception {
        UserGetDtoOut user1 = new UserGetDtoOut(1L, "user1", "user1@mail.ru");
        UserGetDtoOut user2 = new UserGetDtoOut(2L, "user2", "user2@mail.ru");
        List<UserGetDtoOut> usersList = List.of(user1, user2);

        when(userService.getAllUsers()).thenReturn(usersList);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("user1"))
                .andExpect(jsonPath("$[0].email").value("user1@mail.ru"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("user2"))
                .andExpect(jsonPath("$[1].email").value("user2@mail.ru"));
    }

    @Test
    void deleteUserTest() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUser(1L);
    }
}
