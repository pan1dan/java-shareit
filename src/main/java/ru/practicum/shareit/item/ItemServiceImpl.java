package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.RepositoryManager;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    public ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoIn) {
        RepositoryManager.getUserRepository().getUser(userId);
        return RepositoryManager.getItemRepository().addItem(userId, itemAddDtoIn);
    }

    public ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn) {
        return RepositoryManager.getItemRepository().updateItem(userId, itemUpdateDtoIn);
    }

    public ItemGetDtoOut getItem(Long userId, Long itemId) {
        return RepositoryManager.getItemRepository().getItem(userId, itemId);
    }

    public List<ItemGetDtoOut> getUserItems(Long userId) {
        return RepositoryManager.getItemRepository().getUserItems(userId);
    }

    public List<ItemGetDtoOut> searchItem(String text) {
        return RepositoryManager.getItemRepository().searchItem(text);
    }

}
