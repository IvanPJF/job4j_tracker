package ru.job4j.tracker;

/**Абстрактный класс реализующий интерфейс UserAction.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 04.10.2018
 *@version 0.1
 */
public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    protected BaseAction(int key, String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * Ключ меню.
     */
    @Override
    public int key() {
        return this.key;
    }

    /**
     * Название пункта меню.
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
