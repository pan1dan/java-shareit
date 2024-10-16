package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.base.client.BaseClient;
import ru.practicum.shareit.user.dto.in_entity.UserAddDtoIn;
import ru.practicum.shareit.user.dto.in_entity.UserUpdateDtoIn;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addUser(UserAddDtoIn userAddDtoIn) {
        return post("", userAddDtoIn);
    }

    public ResponseEntity<Object> updateUser(long userId, UserUpdateDtoIn userUpdateDtoIn) {
        return patch("/" + userId, userUpdateDtoIn);
    }

    public ResponseEntity<Object> getUser(long userId) {
        return get("/" + userId, userId);
    }

    public ResponseEntity<Object> getAllUsers() {
        return get("");
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("/" + userId, userId);
    }
}