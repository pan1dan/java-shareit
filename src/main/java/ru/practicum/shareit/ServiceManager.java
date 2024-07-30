package ru.practicum.shareit;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemServiceImpl;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.interfaces.UserService;

@Service
public class ServiceManager {
    @Getter
    private static UserService userService;
    @Getter
    private static ItemService itemService;

    @Autowired
    public ServiceManager(UserServiceImpl userService, ItemServiceImpl itemService) {
        this.itemService = itemService;
        this.userService = userService;
    }
}
