package ru.job4j.tracker;

import java.util.Arrays;
import java.util.Random;

/**Класс Tracker. Предназачен для хранения и работы с заявками.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 27.09.2018
 *@version 0.1
 */
public class Tracker {
    private int sizeItems = 100;
    private Item[] items = new Item[sizeItems];
    private int position = 0;
    private static final Random RND = new Random();

    /**
     * Добавление заявки в tracker.
     * @param item Заявка, которую необходимо добавить.
     * @return Добавляемая заявка.
     */
    public Item add(Item item) {
        if (position != sizeItems) {
            item.setId(this.generateId());
            this.items[position++] = item;
        }
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
     */
    public void replace(String id, Item item) {
        for (int index = 0; index < position; index++) {
            if (this.items[index] != null && this.items[index].getId().equals(id)) {
                item.setId(id);
                this.items[index] = item;
            }
        }
    }

    /**
     * Удаление заявки из tracker.
     * @param id Id заявки, которую необходимо удалить.
     */
    public void delete(String id) {
        for (int index = 0; index < position; index++) {
            if (this.items[index] != null && this.items[index].getId().equals(id) && index != position - 1) {
                System.arraycopy(this.items, index + 1, this.items, index, position - index - 1);
                items[--position] = null;
                break;
            } else if (this.items[index] != null && this.items[index].getId().equals(id)) {
                this.items[--position] = null;
            }
        }
    }

    /**
     * Поиск всех заявок в tracker.
     * @return Массив всех заявок в tracker.
     */
    public Item[] findAll() {
        Item[] result = new Item[position];
        System.arraycopy(this.items, 0, result, 0, position);
        return result;
    }

    /**
     * Поиск заявок по имени.
     * @param key Имя заявки, которую необходимо найти.
     * @return Массив заявок с искомым именем.
     */
    public Item[] findByName(String key) {
        int index = 0;
        int count = 0;
        Item[] result = new Item[position];
        for (int out = 0; out < position; out++) {
            for (int in = index; in < position; in++) {
                if (this.items[in] != null && this.items[in].getName().equals(key)) {
                    result[out] = this.items[in];
                    count++;
                    index = ++in;
                    break;
                }
            }
        }
        return Arrays.copyOf(result, count);
    }

    /**
     * Поиск заявки по id.
     * @param id Id заявки, которую необходимо найти.
     * @return Найденная заявка(если такая заявка существует) или null(если нет такой заявки).
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item: this.items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }
}
