package ru.job4j.tracker;

import java.util.Objects;

/**Класс заявки.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 27.09.2018
 *@version 0.1
 */
public class Item {
    private String id;
    private String name;
    private String description;
    private long create;

    public Item() {
    }

    public Item(String name, String description, long create) {
        this.name = name;
        this.description = description;
        this.create = create;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreate() {
        return this.create;
    }

    public void setCreate(long create) {
        this.create = create;
    }

    /**
     * Переопределяет метод toString.
     * @return Содержимое полей Item.
     */
    @Override
    public String toString() {
        return "Name: [" + this.name
                + "] Description: [" + this.description
                + "] Id: [" + this.id + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return create == item.create
                && Objects.equals(id, item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, create);
    }
}
