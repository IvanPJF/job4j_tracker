package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**Класс Tracker. Предназачен для хранения и работы с заявками.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 27.09.2018
 *@version 0.1
 */
public class Tracker {
    private List<Item> items = new ArrayList<>();
    private static final Random RND = new Random();

    /**
     * Добавление заявки в tracker.
     * @param item Заявка, которую необходимо добавить.
     * @return Добавляемая заявка.
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Генератор id для добавляемых заявок.
     * @return Уникальный ключ на основании случайного числа и времени.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RND.nextInt());
    }

    /**
     * Редактирование заявки в tracker.
     * @param id Id заявки, которую необходимо изменить.
     * @param item Изменение.
     * @return Результат выполнения действия(true - заявка найдена и отредактирована, false - нет заявки с таким id).
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = 0;
        for (Item value : this.items) {
            if (value != null && id.equals(value.getId())) {
                result = true;
                break;
            }
            index++;
        }
        if (result) {
            item.setId(id);
            this.items.set(index, item);
        }
        return result;
    }

    /**
     * Удаление заявки из tracker.
     * @param id Id заявки, которую необходимо удалить.
     * @return Результат выполнения действия(true - заявка найдена и удалена, false - нет заявки с таким id).
     */
    public boolean delete(String id) {
        boolean result = false;
        int index = 0;
        for (Item value : this.items) {
            if (value != null && id.equals(value.getId())) {
                result = true;
                break;
            }
            index++;
        }
        if (result) {
            this.items.remove(index);
        }
        return result;
    }

    /**
     * Показ всех заявок.
     * @return Список всех заявок.
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Поиск заявок по имени.
     * @param key Имя заявки, которую необходимо найти.
     * @return Массив заявок с искомым именем.
     */
    public List<Item> findByName(String key) {
        return this.items.stream().filter(
                value -> key.equals(value.getName())
        ).collect(Collectors.toList());
    }

    /**
     * Поиск заявки по id.
     * @param id Id заявки, которую необходимо найти.
     * @return Найденная заявка(если такая заявка существует) или null(если нет такой заявки).
     */
    public Item findById(String id) {
        return this.items.stream().filter(
                value -> id.equals(value.getId())
        ).findFirst().orElse(null);
    }
}
