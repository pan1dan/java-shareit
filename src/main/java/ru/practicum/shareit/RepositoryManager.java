package ru.practicum.shareit;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.InMemoryItemRepository;
import ru.practicum.shareit.user.InMemoryUserRepository;

@Component
public class RepositoryManager {
    @Getter
    private static InMemoryItemRepository itemRepository;
    @Getter
    private static InMemoryUserRepository userRepository;

    public RepositoryManager(InMemoryUserRepository userRepository, InMemoryItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }
}
