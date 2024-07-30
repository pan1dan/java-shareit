package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.in.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.out.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn) {
        return userRepository.addUser(userAddDtoIn);
    }

    @Override
    public UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn) {
        return userRepository.updateUser(userUpdateDtoIn);
    }

    @Override
    public UserGetDtoOut getUser(long id) {
        if (id < 1) {
            throw new ValidationException("Ошибка валидации поля id у пользователя");
        }
        return userRepository.getUser(id);
    }

    @Override
    public List<UserGetDtoOut> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
