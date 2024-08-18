package ru.practicum.shareit.item.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i " +
            "from Item as i " +
            "where i.available = true and " +
            "(lower(i.name) like lower(concat('%', ?1, '%')) or " +
            "lower(i.description) like lower(concat('%', ?1, '%')))")
    List<Item> search(final String text);

    List<Item> findAllByOwnerId(Long ownerId);

    List<Item> findAllByRequestId(Long requestId);
}
