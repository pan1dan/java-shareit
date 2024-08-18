package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.inEntity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.outEntity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.outEntity.ItemRequestGetDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestRepository;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestAddDtoOut addItemRequest(Long userId, ItemRequestAddDtoIn itemRequestAddDtoIn) {
        if (userId < 0) {
            throw new ValidationException("ошибка при проверке id пользователя");
        }
        if (itemRequestAddDtoIn.getDescription() == null || itemRequestAddDtoIn.getDescription().isBlank()) {
            throw new ValidationException("Поле с текстом запросе не может быть пустым");
        }
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId +
                                                                                                        " не найден"));
        ItemRequest newRequest = ItemRequestMapper.fromItemAddDtoInToItemRequest(user, itemRequestAddDtoIn);
        newRequest.setCreated(LocalDateTime.now());
        return ItemRequestMapper.fromItemRequestToItemRequestAddDtoOut(itemRequestRepository.save(newRequest));
    }

    @Override
    public List<ItemRequestGetDtoOut> getUserItemsRequests(Long userId) {
        if (userId < 0) {
            throw new ValidationException("ошибка при проверке id пользователя");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId +
                        " не найден"));

        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId);
        return itemRequestList.stream().map(itemRequest -> {
                                             return ItemRequestMapper.fromItemRequestToItemRequestGetDtoOut(
                                                                itemRequest,
                                                                itemRepository.findAllByRequestId(itemRequest.getId()));
                                             }).toList();
    }

    @Override
    public List<ItemRequestGetDtoOut> getAllItemsRequests() {
        List<ItemRequest> itemRequestList = itemRequestRepository.findAllByOrderByCreatedDesc();
        return itemRequestList.stream().map(itemRequest -> {
                                                return ItemRequestMapper.fromItemRequestToItemRequestGetDtoOut(
                                                                itemRequest,
                                                                itemRepository.findAllByRequestId(itemRequest.getId()));
                                                }).toList();
    }

    @Override
    public ItemRequestGetDtoOut getItemRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                                                       .orElseThrow(() -> new NotFoundException("Реквест с id = " +
                                                                                        requestId + " не был найден"));
        List<Item> items = itemRepository.findAllByRequestId(requestId);
        return ItemRequestMapper.fromItemRequestToItemRequestGetDtoOut(itemRequest, items);
    }

}
