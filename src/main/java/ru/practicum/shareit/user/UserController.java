package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.RepositoryManager;
import ru.practicum.shareit.item.InMemoryItemRepository;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
public class UserController {
    UserService userService;
    RepositoryManager repositoryManager = new RepositoryManager(new InMemoryUserRepository(),
                                                                new InMemoryItemRepository());

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserAddDtoOut addUser(@RequestBody @Valid UserAddDtoIn userAddDtoIn) {
        return userService.addUser(userAddDtoIn);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserUpdateDtoOut updateUser(@PathVariable(name = "id") long userId,
                                       @RequestBody @Valid UserUpdateDtoIn userUpdateDtoIn) {
        userUpdateDtoIn.setId(userId);
        return userService.updateUser(userUpdateDtoIn);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserGetDtoOut getUser(@PathVariable(name = "id") long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserGetDtoOut> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

}
