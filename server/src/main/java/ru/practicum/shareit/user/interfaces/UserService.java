package ru.practicum.shareit.user.interfaces;

import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in_entity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserUpdateDtoOut;

import java.util.List;

public interface UserService {

    UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn);

    UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn);

    UserGetDtoOut getUser(long id);

    List<UserGetDtoOut> getAllUsers();

    void deleteUser(Long id);
}
