package ru.job4j.tracker;

/**Интерфейс.
 *@author IvanPJF (teaching-light@yandex.ru)
 *@since 28.09.2018
 *@version 0.1
 */
public interface Input {

    /**
     * Общий метод для классов реализующих этот интерфейс.
     * @param question Вопрос пользователю.
     */
    String ask(String question);

    int ask(String question, int[] range);
}
