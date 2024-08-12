package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.RepositoryManager;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn) {
        return RepositoryManager.getUserRepository().addUser(userAddDtoIn);
    }

    @Override
    public UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn) {
        return RepositoryManager.getUserRepository().updateUser(userUpdateDtoIn);
    }

    @Override
    public UserGetDtoOut getUser(long id) {
        if (id < 1) {
            throw new ValidationException("Ошибка валидации поля id у пользователя");
        }
        return RepositoryManager.getUserRepository().getUser(id);
    }

    @Override
    public List<UserGetDtoOut> getAllUsers() {
        return RepositoryManager.getUserRepository().getAllUsers();
    }

    @Override
    public void deleteUser(Long id) {
        RepositoryManager.getUserRepository().deleteUser(id);
    }
}
