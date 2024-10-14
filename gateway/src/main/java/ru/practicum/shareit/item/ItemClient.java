package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.base.client.BaseClient;
import ru.practicum.shareit.item.InEntity.CommentParams;
import ru.practicum.shareit.item.InEntity.ItemAddDtoIn;
import ru.practicum.shareit.item.InEntity.ItemUpdateDtoIn;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(long userId, ItemAddDtoIn itemAddDtoIn) {
        return post("", userId, itemAddDtoIn);
    }

    public ResponseEntity<Object> updateItem(long userId, ItemUpdateDtoIn itemUpdateDtoIn, long itemId) {
        return patch("/" + itemId, userId, itemUpdateDtoIn);
    }

    public ResponseEntity<Object> getItem(long userId, long itemId) {
        return get("/" + itemId, userId);
    }

    public ResponseEntity<Object> getUserItems(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> searchItem(String text) {
        Map<String, Object> parameters = Map.of(
                "text", text
        );
        return get("/search?text={text}", 1L, parameters);
    }

    public ResponseEntity<Object> addComment(CommentParams commentParams) {
        return post("/" + commentParams.getItemId() + "/comment", commentParams.getAuthorId(), commentParams);
    }
}
