package ru.practicum.shareit.user.interfaces;

import ru.practicum.shareit.user.dto.in.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.out.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out.UserUpdateDtoOut;

import java.util.List;

public interface UserService {

    UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn);

    UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn);

    UserGetDtoOut getUser(long id);

    List<UserGetDtoOut> getAllUsers();

    void deleteUser(Long id);
}
