package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.in_entity.ItemRequestAddDtoIn;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestAddDtoOut;
import ru.practicum.shareit.request.dto.out_entity.ItemRequestGetDtoOut;
import ru.practicum.shareit.request.interfaces.ItemRequestService;
import ru.practicum.shareit.user.dto.out_entity.UserGetDtoOut;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
public class ItemRequestControllerTest {
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemRequestService itemRequestService;

    @Autowired
    MockMvc mvc;
    private final String sharerUserIdHead = "X-Sharer-User-Id";

    @Test
    void addItemRequestTest() throws Exception {
        ItemRequestAddDtoIn itemRequestIn = new ItemRequestAddDtoIn("itemRequest");
        ItemRequestAddDtoOut itemRequestOut = new ItemRequestAddDtoOut(1L,
                                                                        "itemRequest",
                                                                        new UserGetDtoOut(),
                                                                        LocalDateTime.now());

        when(itemRequestService.addItemRequest(any(Long.class), any(ItemRequestAddDtoIn.class)))
                .thenReturn(itemRequestOut);

        mvc.perform(post("/requests")
                        .header(sharerUserIdHead, 1L)
                        .content(mapper.writeValueAsString(itemRequestIn))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("itemRequest"))
                .andExpect(jsonPath("$.created").isNotEmpty());
        verify(itemRequestService).addItemRequest(any(Long.class), any(ItemRequestAddDtoIn.class));
    }

    @Test
    void getUserItemsRequestsTest() throws Exception {
        ItemRequestGetDtoOut itemRequest1 = new ItemRequestGetDtoOut(1L, "request1", LocalDateTime.now(), List.of());
        ItemRequestGetDtoOut itemRequest2 = new ItemRequestGetDtoOut(2L, "request2", LocalDateTime.now(), List.of());
        List<ItemRequestGetDtoOut> itemRequestsList = List.of(itemRequest1, itemRequest2);

        when(itemRequestService.getUserItemsRequests(any(Long.class))).thenReturn(itemRequestsList);
        mvc.perform(get("/requests")
                        .header(sharerUserIdHead, 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("request1"))
                .andExpect(jsonPath("$[0].created").isNotEmpty())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].description").value("request2"))
                .andExpect(jsonPath("$[1].created").isNotEmpty());
    }

    @Test
    void getAllItemsRequestsTest() throws Exception {
        ItemRequestGetDtoOut itemRequest1 = new ItemRequestGetDtoOut(1L, "request1", LocalDateTime.now(), List.of());
        ItemRequestGetDtoOut itemRequest2 = new ItemRequestGetDtoOut(2L, "request2", LocalDateTime.now(), List.of());
        List<ItemRequestGetDtoOut> itemRequestsList = List.of(itemRequest1, itemRequest2);
        when(itemRequestService.getAllItemsRequests()).thenReturn(itemRequestsList);

        mvc.perform(get("/requests/all")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].description").value("request1"))
                .andExpect(jsonPath("$[0].created").isNotEmpty())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].description").value("request2"))
                .andExpect(jsonPath("$[1].created").isNotEmpty());
    }

    @Test
    void getItemRequestByIdTest() throws Exception {
        ItemRequestGetDtoOut itemRequest1 = new ItemRequestGetDtoOut(1L, "request1", LocalDateTime.now(), List.of());
        when(itemRequestService.getItemRequestById(any(Long.class))).thenReturn(itemRequest1);

        mvc.perform(get("/requests/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("request1"))
                .andExpect(jsonPath("$.created").isNotEmpty());

    }

}
