package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserAddDtoOut addUser(@RequestBody UserAddDtoIn userAddDtoIn) {
        log.info("POST /users: {}", userAddDtoIn);
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        log.info("POST /users возвращает значение: {}", userAddDtoOut);
        return userAddDtoOut;
    }

    @PatchMapping("/{id}")
    public UserUpdateDtoOut updateUser(@PathVariable(name = "id") long userId,
                                       @RequestBody UserUpdateDtoIn userUpdateDtoIn) {
        log.info("PATCH /users/{}: {}", userId, userUpdateDtoIn);
        userUpdateDtoIn.setId(userId);
        UserUpdateDtoOut userUpdateDtoOut = userService.updateUser(userUpdateDtoIn);
        log.info("PATCH /users/{} возвращает значение: {}", userId, userUpdateDtoOut);
        return userUpdateDtoOut;

    }

    @GetMapping("/{id}")
    public UserGetDtoOut getUser(@PathVariable(name = "id") long userId) {
        log.info("GET /users/{}", userId);
        UserGetDtoOut userGetDtoOut = userService.getUser(userId);
        log.info("GET /users/{} возвращает значение: {}", userId, userGetDtoOut);
        return userGetDtoOut;
    }

    @GetMapping
    public List<UserGetDtoOut> getAllUsers() {
        log.info("GET /users");
        List<UserGetDtoOut> userGetDtoOutList = userService.getAllUsers();
        log.info("GET /users возвращает значение: {}", userGetDtoOutList);
        return userGetDtoOutList;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        log.info("DELETE /users/{}", id);
        userService.deleteUser(id);
    }

}
