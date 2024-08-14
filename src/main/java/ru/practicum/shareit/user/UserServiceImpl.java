package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.inEntity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.inEntity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.outEntity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.outEntity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.interfaces.UserService;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserAddDtoOut addUser(UserAddDtoIn userAddDtoIn) {
        User newUser = userRepository.save(UserMapper.fromUserAddDtoInToUser(userAddDtoIn));
        return UserMapper.fromUserToUserAddDtoOut(newUser);
        //return RepositoryManager.getUserRepository().addUser(userAddDtoIn);
    }

    @Override
    public UserUpdateDtoOut updateUser(UserUpdateDtoIn userUpdateDtoIn) {
        User user = userRepository
                    .findById(userUpdateDtoIn.getId())
                    .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userUpdateDtoIn.getId()
                                                                                                       + " не найден"));
        if (userUpdateDtoIn.getEmail() != null && !userUpdateDtoIn.getEmail().isBlank()) {
            user.setEmail(userUpdateDtoIn.getEmail());
        }
        if (userUpdateDtoIn.getName() != null && !userUpdateDtoIn.getName().isBlank()) {
            user.setName(userUpdateDtoIn.getName());
        }
        return UserMapper.fromUserToUserUpdateDtoOut(userRepository.save(user));
    }

    @Override
    public UserGetDtoOut getUser(long id) {
        if (id < 1) {
            throw new ValidationException("Ошибка валидации поля id у пользователя");
        }
        return UserMapper.fromUserToUserGetDtoOut(
                userRepository.findById(id).orElseThrow(
                        () -> new NotFoundException("Пользователь с id = " + id + " не найден")));
        //return RepositoryManager.getUserRepository().getUser(id);
    }

    @Override
    public List<UserGetDtoOut> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(UserMapper::fromUserToUserGetDtoOut)
                             .toList();
        //return RepositoryManager.getUserRepository().getAllUsers();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        //RepositoryManager.getUserRepository().deleteUser(id);
    }

}
