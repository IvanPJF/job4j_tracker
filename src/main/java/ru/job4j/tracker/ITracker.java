package ru.job4j.tracker;

import ru.job4j.tracker.react.Observe;

import java.util.List;

public interface ITracker {

    Item add(Item item);

    boolean replace(String id, Item item);

    boolean delete(String id);

    default List<Item> findAll() {
        return List.of();
    }

    void findAll(Observe<Item> model);

    List<Item> findByName(String key);

    Item findById(String id);
}
