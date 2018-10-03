package ru.job4j.tracker;

/**Класс для создания своей ошибки.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 03.10.2018
 *@version 0.1
 */
public class MenuOutException extends RuntimeException {

    public MenuOutException(String msg) {
        super(msg);
    }
}
