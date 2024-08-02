package ru.practicum.shareit.user.interfaces;

import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;

import java.util.List;

public interface UserService {

    UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn);

    UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn);

    UserGetDtoOut getUser(long id);

    List<UserGetDtoOut> getAllUsers();

    void deleteUser(Long id);
}
