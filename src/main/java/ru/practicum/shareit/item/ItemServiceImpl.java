package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.RepositoryManager;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.interfaces.ItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
//    ItemRepository itemRepository;
//
//    @Autowired
//    public ItemServiceImpl(InMemoryItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    public ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoIn) {
        RepositoryManager.getUserRepository().getUser(userId);
        //return itemRepository.addItem(userId, itemAddDtoIn);
        return RepositoryManager.getItemRepository().addItem(userId, itemAddDtoIn);
    }

    public ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn) {
        //return itemRepository.updateItem(userId, itemUpdateDtoIn);
        return RepositoryManager.getItemRepository().updateItem(userId, itemUpdateDtoIn);
    }

    public ItemGetDtoOut getItem(Long userId, Long itemId) {
        //return itemRepository.getItem(userId, itemId);
        return RepositoryManager.getItemRepository().getItem(userId, itemId);
    }

    public List<ItemGetDtoOut> getUserItems(Long userId) {
        //return itemRepository.getUserItems(userId);
        return RepositoryManager.getItemRepository().getUserItems(userId);
    }

    public List<ItemGetDtoOut> searchItem(String text) {
        //return itemRepository.searchItem(text);
        return RepositoryManager.getItemRepository().searchItem(text);
    }

}
