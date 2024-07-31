package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> userStorage = new HashMap<>();
    private long id = 0;

    @Override
    public UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn) {
        isExistsUserWithThisEmail(userAddDtoIn.getEmail(), id + 1);
        User user = User.builder()
                        .email(userAddDtoIn.getEmail())
                        .name(userAddDtoIn.getName())
                        .id(++id).build();
        userStorage.put(user.getId(), user);
        return UserMapper.fromUserToUserAddDtoOut(user);
    }

    @Override
    public UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn) {
        User user = userStorage.get(userUpdateDtoIn.getId());
        if (userUpdateDtoIn.getEmail() != null && !userUpdateDtoIn.getEmail().isBlank()) {
            isExistsUserWithThisEmail(userUpdateDtoIn.getEmail(), userUpdateDtoIn.getId());
            user.setEmail(userUpdateDtoIn.getEmail());
        }
        if (userUpdateDtoIn.getName() != null && !userUpdateDtoIn.getName().isBlank()) {
            user.setName(userUpdateDtoIn.getName());
        }
        userStorage.put(user.getId(), user);
        return UserMapper.fromUserToUserUpdateDtoOut(user);
    }

    @Override
    public UserGetDtoOut getUser(long id) {
        if (!userStorage.containsKey(id)) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
        return UserMapper.fromUserToUserGetDtoOut(userStorage.get(id));
    }

    @Override
    public List<UserGetDtoOut> getAllUsers() {
        return userStorage.values().stream()
                                   .map(UserMapper::fromUserToUserGetDtoOut)
                                   .toList();
    }

    @Override
    public void deleteUser(Long id) {
        if (userStorage.containsKey(id)) {
            userStorage.remove(id);
        } else {
            throw new NotFoundException("Пользователь с id = " + id + " не найден");
        }
    }

    private void isExistsUserWithThisEmail(String email, long id) {
        for(User user : userStorage.values()) {
            if (user.getEmail().equals(email) && user.getId() != id) {
                throw new ConflictException("Пользователь с таким email уже существует");
            }
        }
    }
}
