package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in_entity.UserUpdateDtoIn;
import ru.practicum.shareit.user.dto.out_entity.UserAddDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;
import ru.practicum.shareit.user.dto.out_entity.UserUpdateDtoOut;
import ru.practicum.shareit.user.interfaces.UserService;

import java.util.List;

@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceTest {
    private final UserService userService;

    @Test
    void addUserTest() {
        UserAddDtoIn userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        Assertions.assertEquals(userAddDtoOut.getEmail(), userAddDtoIn.getEmail());
        Assertions.assertEquals(userAddDtoOut.getName(), userAddDtoIn.getName());
    }

    @Test
    void updateUserTest() {
        UserAddDtoIn userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        UserUpdateDtoIn userUpdateDtoIn = new UserUpdateDtoIn(userAddDtoOut.getId(),
                                                              userAddDtoOut.getName() + "Update",
                                                              "userUpdate@mail.ru");
        UserUpdateDtoOut userUpdateDtoOut = userService.updateUser(userUpdateDtoIn);

        Assertions.assertEquals(userUpdateDtoIn.getId(), userUpdateDtoOut.getId());
        Assertions.assertEquals("userUpdate", userUpdateDtoOut.getName());
        Assertions.assertEquals("userUpdate@mail.ru", userUpdateDtoOut.getEmail());
    }

    @Test
    void getUserTest() {
        UserAddDtoIn userAddDtoIn = new UserAddDtoIn("user", "user@mail.ru");
        UserAddDtoOut userAddDtoOut = userService.addUser(userAddDtoIn);
        UserGetDtoOut userGetDtoOut = userService.getUser(userAddDtoOut.getId());

        Assertions.assertEquals(userAddDtoOut.getId(), userGetDtoOut.getId());
        Assertions.assertEquals(userAddDtoOut.getName(), userGetDtoOut.getName());
        Assertions.assertEquals(userAddDtoOut.getEmail(), userGetDtoOut.getEmail());
    }

    @Test
    void getAllUsersTest() {
        UserAddDtoIn userAddDtoIn1 = new UserAddDtoIn("user1", "user1@mail.ru");
        UserAddDtoOut userAddDtoOut1 = userService.addUser(userAddDtoIn1);
        UserAddDtoIn userAddDtoIn2 = new UserAddDtoIn("user2", "user2@mail.ru");
        UserAddDtoOut userAddDtoOut2 = userService.addUser(userAddDtoIn2);
        List<UserGetDtoOut> usersList = userService.getAllUsers();
        UserGetDtoOut userFromList1 = usersList.get(0);
        UserGetDtoOut userFromList2 = usersList.get(1);

        Assertions.assertEquals(userAddDtoOut1.getId(), userFromList1.getId());
        Assertions.assertEquals(userAddDtoOut1.getName(), userFromList1.getName());
        Assertions.assertEquals(userAddDtoOut1.getEmail(), userFromList1.getEmail());

        Assertions.assertEquals(userAddDtoOut2.getId(), userFromList2.getId());
        Assertions.assertEquals(userAddDtoOut2.getName(), userFromList2.getName());
        Assertions.assertEquals(userAddDtoOut2.getEmail(), userFromList2.getEmail());
    }

    @Test
    void deleteUserTest() {
        UserAddDtoIn userAddDtoIn1 = new UserAddDtoIn("user1", "user1@mail.ru");
        UserAddDtoOut userAddDtoOut1 = userService.addUser(userAddDtoIn1);
        UserAddDtoIn userAddDtoIn2 = new UserAddDtoIn("user2", "user2@mail.ru");
        UserAddDtoOut userAddDtoOut2 = userService.addUser(userAddDtoIn2);
        List<UserGetDtoOut> usersListBefore = userService.getAllUsers();
        UserGetDtoOut userFromListBefore1 = usersListBefore.get(0);
        UserGetDtoOut userFromListBefore2 = usersListBefore.get(1);

        Assertions.assertEquals(userAddDtoOut1.getId(), userFromListBefore1.getId());
        Assertions.assertEquals(userAddDtoOut1.getName(), userFromListBefore1.getName());
        Assertions.assertEquals(userAddDtoOut1.getEmail(), userFromListBefore1.getEmail());
        Assertions.assertEquals(userAddDtoOut2.getId(), userFromListBefore2.getId());
        Assertions.assertEquals(userAddDtoOut2.getName(), userFromListBefore2.getName());
        Assertions.assertEquals(userAddDtoOut2.getEmail(), userFromListBefore2.getEmail());

        userService.deleteUser(userAddDtoOut1.getId());
        List<UserGetDtoOut> usersListAfterDelete = userService.getAllUsers();
        Assertions.assertEquals(1, usersListAfterDelete.size());
        UserGetDtoOut userFromListAfter1 = usersListAfterDelete.get(0);
        Assertions.assertEquals(userAddDtoOut2.getId(), userFromListAfter1.getId());
        Assertions.assertEquals(userAddDtoOut2.getName(), userFromListAfter1.getName());
        Assertions.assertEquals(userAddDtoOut2.getEmail(), userFromListAfter1.getEmail());
    }
}
