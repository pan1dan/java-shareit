package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.InEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.InEntity.UserUpdateDtoIn;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserAddDtoIn userAddDtoIn) {
        log.info("POST /users: {}", userAddDtoIn);
        ResponseEntity<Object> newUser = userClient.addUser(userAddDtoIn);
        log.info("POST /users возвращает значение: {}", newUser);
        return newUser;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(name = "id") @Min(0) long userId,
                                       @RequestBody @Valid UserUpdateDtoIn userUpdateDtoIn) {
        log.info("PATCH /users/{}: {}", userId, userUpdateDtoIn);
        userUpdateDtoIn.setId(userId);
        ResponseEntity<Object> updateUser = userClient.updateUser(userId, userUpdateDtoIn);
        log.info("PATCH /users/{} возвращает значение: {}", userId, updateUser);
        return updateUser;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(name = "id") @Min(0) long userId) {
        log.info("GET /users/{}", userId);
        ResponseEntity<Object> user = userClient.getUser(userId);
        log.info("GET /users/{} возвращает значение: {}", userId, user);
        return user;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("GET /users");
        ResponseEntity<Object> users = userClient.getAllUsers();
        log.info("GET /users возвращает значение: {}", users);
        return users;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") @Min(0) long id) {
        log.info("DELETE /users/{}", id);
        ResponseEntity<Object> answer = userClient.deleteUser(id);
        log.info("DELETE /users/{} возвращает значение: {}", id, answer);
        return answer;
    }






}