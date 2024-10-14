package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.outEntity.BookingGetDtoOut;
import ru.practicum.shareit.item.dto.inEntity.CommentAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.dto.inEntity.ItemUpdateDtoIn;
import ru.practicum.shareit.item.dto.outEntity.CommentAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemAddDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemGetDtoOut;
import ru.practicum.shareit.item.dto.outEntity.ItemUpdateDtoOut;
import ru.practicum.shareit.item.interfaces.ItemService;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.outEntity.UserGetDtoOut;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mvc;
    private final String sharerUserIdHead = "X-Sharer-User-Id";

    ItemGetDtoOut itemGetDtoOut = new ItemGetDtoOut(1,
            "item",
            "good",
            true,
            new UserGetDtoOut(),
            new ItemRequest(),
            new BookingGetDtoOut(),
            new BookingGetDtoOut(),
            List.of());

    @Test
    void addItemTest() throws Exception {
        ItemAddDtoIn itemAddDtoIn = new ItemAddDtoIn("item",
                                                     "good",
                                                     true,
                                                     null);
        ItemAddDtoOut itemAddDtoOut = new ItemAddDtoOut(1,
                                                        "item",
                                                        "good",
                                                        true,
                                                        new UserGetDtoOut(),
                                                        1L);
        when(itemService.addItem(any(Long.class), any(ItemAddDtoIn.class))).thenReturn(itemAddDtoOut);

        mvc.perform(post("/items")
                .header(sharerUserIdHead, 1L)
                .content(mapper.writeValueAsString(itemAddDtoIn))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("item"))
                .andExpect(jsonPath("$.description").value("good"))
                .andExpect(jsonPath("$.owner").isNotEmpty())
                .andExpect(jsonPath("$.requestId").value(1));
    }

    @Test
    void updateItemTest() throws Exception {
        ItemUpdateDtoIn itemUpdateDtoIn = new ItemUpdateDtoIn(1L,
                                                              "item",
                                                              "good",
                                                              true,
                                                              1L);
        ItemUpdateDtoOut itemUpdateDtoOut = new ItemUpdateDtoOut(1,
                                                                 "item",
                                                                 "good",
                                                                 true,
                                                                 new UserGetDtoOut(),
                                                                 new ItemRequest());
        when(itemService.updateItem(any(Long.class), any(ItemUpdateDtoIn.class))).thenReturn(itemUpdateDtoOut);

        mvc.perform(patch("/items/1")
                        .header(sharerUserIdHead, 1L)
                        .content(mapper.writeValueAsString(itemUpdateDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("item"))
                .andExpect(jsonPath("$.description").value("good"))
                .andExpect(jsonPath("$.owner").isNotEmpty())
                .andExpect(jsonPath("$.request").isNotEmpty());
    }

    @Test
    void getItemTest() throws Exception {
        when(itemService.getItem(any(Long.class),any(Long.class))).thenReturn(itemGetDtoOut);

        mvc.perform(get("/items/1")
                        .header(sharerUserIdHead, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("item"))
                .andExpect(jsonPath("$.description").value("good"))
                .andExpect(jsonPath("$.owner").isNotEmpty())
                .andExpect(jsonPath("$.request").isNotEmpty())
                .andExpect(jsonPath("$.lastBooking").isNotEmpty())
                .andExpect(jsonPath("$.nextBooking").isNotEmpty());
    }

    @Test
    void getUserItemsTest() throws Exception {
        when(itemService.getUserItems(any(Long.class))).thenReturn(List.of(itemGetDtoOut));

        mvc.perform(get("/items")
                        .header(sharerUserIdHead, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("item"))
                .andExpect(jsonPath("$[0].description").value("good"))
                .andExpect(jsonPath("$[0].owner").isNotEmpty())
                .andExpect(jsonPath("$[0].request").isNotEmpty())
                .andExpect(jsonPath("$[0].lastBooking").isNotEmpty())
                .andExpect(jsonPath("$[0].nextBooking").isNotEmpty());
    }

    @Test
    void searchItemTest() throws Exception {
        when(itemService.searchItem(any(String.class))).thenReturn(List.of(itemGetDtoOut));
        mvc.perform(get("/items/search")
                        .header(sharerUserIdHead, 1L)
                        .param("text", "item")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("item"))
                .andExpect(jsonPath("$[0].description").value("good"))
                .andExpect(jsonPath("$[0].owner").isNotEmpty())
                .andExpect(jsonPath("$[0].request").isNotEmpty())
                .andExpect(jsonPath("$[0].lastBooking").isNotEmpty())
                .andExpect(jsonPath("$[0].nextBooking").isNotEmpty());
    }

    @Test
    void addCommentTest() throws Exception {
        CommentAddDtoIn commentAddDtoIn = new CommentAddDtoIn("comment");
        CommentAddDtoOut commentAddDtoOut = new CommentAddDtoOut(1,
                                                                 "comment",
                                                                 itemGetDtoOut,
                                                                 "user",
                                                                 LocalDateTime.now());
        when(itemService.addComment(any(Long.class),
                                    any(Long.class),
                                    any(CommentAddDtoIn.class)))
                        .thenReturn(commentAddDtoOut);
        mvc.perform(post("/items/1/comment")
                        .header(sharerUserIdHead, 1L)
                        .content(mapper.writeValueAsString(commentAddDtoIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("comment"))
                .andExpect(jsonPath("$.item").isNotEmpty())
                .andExpect(jsonPath("$.authorName").value("user"))
                .andExpect(jsonPath("$.created").isNotEmpty());
    }
}
