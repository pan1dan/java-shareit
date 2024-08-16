package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.interfaces.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.inEntity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.*;
import ru.practicum.shareit.item.interfaces.CommentRepository;
import ru.practicum.shareit.item.interfaces.ItemRepository;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.interfaces.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemAddDtoOut addItem(Long userId, ItemAddDtoIn itemAddDtoIn) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId +
                        " не найден"));
        Item newItem = itemRepository.save(ItemMapper.fromItemAddDtoInToItem(itemAddDtoIn, user));
        return ItemMapper.fromItemToItemAddDtoOut(newItem);
    }

    @Override
    public ItemUpdateDtoOut updateItem(Long userId, ItemUpdateDtoIn itemUpdateDtoIn) {
        Item item = itemRepository
                .findById(itemUpdateDtoIn.getId())
                .orElseThrow(() -> new NotFoundException(
                                                         "Предмет с id = " + itemUpdateDtoIn.getId() +
                                                         " не был найден"));
        if (!Objects.equals(item.getOwner().getId(), userId)) {
            throw new ForbiddenException("Попытка обновить предмет не владельцем");
        }
        if (itemUpdateDtoIn.getName() != null && !itemUpdateDtoIn.getName().isBlank()) {
            item.setName(itemUpdateDtoIn.getName());
        }
        if (itemUpdateDtoIn.getDescription() != null && !itemUpdateDtoIn.getDescription().isBlank()) {
            item.setDescription(itemUpdateDtoIn.getDescription());
        }
        if (itemUpdateDtoIn.getAvailable() != null) {
            item.setAvailable(itemUpdateDtoIn.getAvailable());
        }
        return ItemMapper.fromItemToItemUpdateDtoOut(itemRepository.save(item));
    }

    @Override
    public ItemGetDtoOut getItem(Long userId, Long itemId) {
        itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с id = " + itemId +
                                                                                                    " не был найден"));
        List<Booking> bookings = bookingRepository.findAllByItemIdAndBookingStatus(itemId, BookingStatus.APPROVED);
        Optional<Booking> lastBooking = bookings.stream()
                .filter(booking -> booking.getEnd().isAfter(LocalDateTime.now()))
                .max(Comparator.comparing(Booking::getEnd));
        Optional<Booking> nextBooking = bookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(Booking::getStart));
        List<CommentGetDtoOut> commentGetDtoOutList = commentRepository.findAllByItemId(itemId)
                                                                      .stream()
                                                                      .map(CommentMapper::fromCommentToCommentGetDtoOut)
                                                                      .toList();
        return ItemMapper.fromItemToItemGetDtoOut(
                lastBooking,
                nextBooking,
                commentGetDtoOutList,
                itemRepository.findById(itemId)
                               .orElseThrow(() -> new NotFoundException(
                                                                        "Предмет с id = " + itemId +
                                                                        " не был найден")));
    }

    @Override
    public List<ItemGetDtoOut> getUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        List<Booking> bookings;
        List<ItemGetDtoOut> itemsList = new ArrayList<>();
        for (Item item : items) {
            bookings = bookingRepository.findAllByItemIdAndBookingStatus(item.getId(), BookingStatus.APPROVED);
            Optional<Booking> lastBooking = bookings.stream()
                    .filter(booking -> booking.getEnd().isAfter(LocalDateTime.now()))
                    .max(Comparator.comparing(Booking::getEnd));
            Optional<Booking> nextBooking = bookings.stream()
                    .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                    .min(Comparator.comparing(Booking::getStart));
            List<CommentGetDtoOut> commentGetDtoOutList = commentRepository.findAllByItemId(item.getId())
                    .stream()
                    .map(CommentMapper::fromCommentToCommentGetDtoOut)
                    .toList();
            itemsList.add(ItemMapper.fromItemToItemGetDtoOut(
                    lastBooking,
                    nextBooking,
                    commentGetDtoOutList,
                    item));

        }
        return itemsList;
    }

    @Override
    public List<ItemGetDtoOut> searchItem(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.search(text).stream().map(ItemMapper::fromItemToItemGetDtoOut).toList();
    }

    @Override
    public CommentAddDtoOut addComment(Long userId, Long itemId, CommentAddDtoIn commentAddDtoIn) {
        Item item = itemRepository.findById(itemId)
                                   .orElseThrow(() -> new NotFoundException("Предмет с id = " + itemId +
                                                                                                    "не найден"));
        User user = userRepository.findById(userId)
                                   .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId +
                                                                                                        " не найден"));
        List<Booking> bookingList = bookingRepository.findAllByBookerIdAndItemIdAndBookingStatusAndEndBefore(
                                                                                                userId,
                                                                                                itemId,
                                                                                                BookingStatus.APPROVED,
                                                                                                LocalDateTime.now());
        if (bookingList.isEmpty()) {
            throw new BadRequestException("Не нашлось бронирований, чтобы оставить комментарий");
        }
        Comment comment = CommentMapper.fromDataToComment(commentAddDtoIn.getText(), user, item);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.fromCommentToCommentAddDtoOut(commentRepository.save(comment));
    }

}
