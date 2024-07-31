package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserAddDtoOut fromUserToUserAddDtoOut(User user) {
        return UserAddDtoOut.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .build();
    }

    public static UserUpdateDtoOut fromUserToUserUpdateDtoOut(User user) {
        return UserUpdateDtoOut.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .name(user.getName())
                                .build();
    }

    public static UserGetDtoOut fromUserToUserGetDtoOut(User user) {
        return UserGetDtoOut.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
