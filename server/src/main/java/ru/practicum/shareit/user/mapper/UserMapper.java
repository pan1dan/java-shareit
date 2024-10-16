package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserUpdateDtoOut;
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

    public static User fromUserAddDtoInToUser(UserAddDtoIn userAddDtoIn) {
        return User.builder()
                .name(userAddDtoIn.getName())
                .email(userAddDtoIn.getEmail())
                .build();
    }
}
