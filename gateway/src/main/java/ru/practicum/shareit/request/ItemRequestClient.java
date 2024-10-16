package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.base.client.BaseClient;
import ru.practicum.shareit.request.in_entity.ItemRequestAddDtoIn;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addItemRequest(long userId, ItemRequestAddDtoIn itemRequestAddDtoIn) {
        return post("", userId, itemRequestAddDtoIn);
    }

    public ResponseEntity<Object> getUserItemsRequests(long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getAllItemsRequests() {
        return get("");
    }

    public ResponseEntity<Object> getItemRequestById(long requestId) {
        return get("/" + requestId);
    }

}
